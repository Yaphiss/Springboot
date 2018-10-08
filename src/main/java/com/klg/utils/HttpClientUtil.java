package com.klg.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.klg.component.LogEvent;
import com.klg.dao.entity.Logs;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by yaphiss on 2018/9/19.
 */
@Component
public class HttpClientUtil {
    private static final Logger logger = Logger.getLogger(HttpClientUtil.class);

    @Autowired private ApplicationContext applicationContext;

    /**
     * 发送post请求
     *
     * @param url:请求地址
     * @param header:请求头参数
     * @param params:表单参数  form提交
     * @param httpEntity   json/xml参数
     * @return
     */
    public JSONObject doPostRequest(String url, Map<String, String> header, Map<String, String> params, HttpEntity httpEntity) {
        JSONObject object = null;
        if (StringUtils.isEmpty(url)) return null;

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;

        String status = new Logs().getDoneStatus();
        String msg = "";
        String method = "";
        Date responseTime = null;

        try {
            httpClient = SSLClientCustom.getHttpClinet();
            HttpPost httpPost = new HttpPost(url);
            method = httpPost.getMethod();
            boolean isJsonRequest = false;
            //请求头header信息
            if (MapUtil.isExist(header)) {
                for (Map.Entry<String, String> stringStringEntry : header.entrySet()) {
                    if (stringStringEntry.getKey() == "Content-Type") isJsonRequest = true;
                    httpPost.setHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
                }
            }
            //请求参数信息
            JSONObject jsonParams = new JSONObject();
            if (MapUtil.isExist(params)) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
                    paramList.add(new BasicNameValuePair(stringStringEntry.getKey(), stringStringEntry.getValue()));
                    jsonParams.put(stringStringEntry.getKey(), stringStringEntry.getValue());
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(paramList, Consts.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);
            }
            //实体设置
            if (httpEntity != null) {
                httpPost.setEntity(httpEntity);
            }

            if (isJsonRequest){
                StringEntity stringEntity = new StringEntity(jsonParams.toString());
                stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(stringEntity);
                httpResponse = httpClient.execute(httpPost);
            }else {
                //发起请求
                httpResponse = httpClient.execute(httpPost);
            }
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            responseTime = new Date();
            if (statusCode == HttpStatus.SC_OK) {
                object = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity(), Consts.UTF_8));
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                HeaderIterator headerIterator = httpResponse.headerIterator();
                while (headerIterator.hasNext()) {
                    stringBuffer.append("\t" + headerIterator.next());
                }
            }

        } catch (Exception e) {
            status = new Logs().getFailStatus();
            msg = e.getMessage();
            logger.error("外部出现异常，原因: " + msg);
        } finally {
            this.publishEvent(status, method, url, params, object, responseTime, msg);
            HttpClientUtil.closeConnection(httpClient, httpResponse);
        }
        return object;
    }

    /**
     * 发送get请求
     * @param url 请求地址
     * @param header 请求头参数
     * @param params 表单参数
     * @return
     * @throws JSONException
     */
    public JSONObject doGetRequest(String url, Map<String, String> header, Map<String, String> params) throws JSONException {
        JSONObject object = null;
        if (StringUtils.isEmpty(url)) return null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = SSLClientCustom.getHttpClinet();
            //请求参数信息
            if (MapUtil.isExist(params)) {
                url = url + buildUrl(params);
            }
            URL urlStr = new URL(url);
            URI uri = new URI(urlStr.getProtocol(), urlStr.getHost(), urlStr.getPath(), urlStr.getQuery(), null);
            HttpGet httpGet = new HttpGet(uri);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)//连接超时
                    .setConnectionRequestTimeout(5000)//请求超时
                    .setSocketTimeout(5000)//套接字连接超时
                    .setRedirectsEnabled(true).build();//允许重定向
            httpGet.setConfig(requestConfig);
            if (MapUtil.isExist(header)) {
                for (Map.Entry<String, String> stringStringEntry : header.entrySet()) {
                    httpGet.setHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
                }
            }
            //发起请求
            httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                object = JSON.parseObject(EntityUtils.toString(httpResponse.getEntity(), Consts.UTF_8));
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                HeaderIterator headerIterator = httpResponse.headerIterator();
                while (headerIterator.hasNext()) {
                    stringBuffer.append("\t" + headerIterator.next());
                }
            }

        } catch (Exception e) {
            logger.error("外部出现异常，原因: " + e.getMessage());
        } finally {
            logger.debug("外部请求结果: "+object);
            HttpClientUtil.closeConnection(httpClient, httpResponse);
        }
        return object;
    }

    private static String buildUrl(Map<String, String> map) {
        if (!MapUtil.isExist(map)) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer("?");
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            stringBuffer.append(stringStringEntry.getKey()).append("=").append(stringStringEntry.getValue()).append("&");
        }
        String result = stringBuffer.toString();
        if (!StringUtils.isEmpty(result)) {
            result = result.substring(0, result.length() - 1);//去掉结尾的&连接符
        }
        return result;
    }

    private static void closeConnection(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发布日志事件
     * @param status
     * @param method
     * @param url
     * @param body
     * @param response
     * @param responseTime
     */
    private void publishEvent(String status, String method, String url, Map<String, String> body, JSONObject response, Date responseTime, String msg) {
        if (null != response) response.put("msg", msg);
        Logs logs = new Logs(status, new Logs().getOutType(), null, null, method, url, body, response, responseTime, null, null);
        applicationContext.publishEvent(new LogEvent(logs));
    }
}
