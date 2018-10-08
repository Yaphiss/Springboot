package com.klg.dao.entity;

import com.klg.utils.DateUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by yaphiss on 2018/9/19.
 */
@Document(collection = "logs")
public class Logs implements Serializable {

    private static final String INIT_STATUS = "init";

    @Id
    private String id;
    private String status;
    private String type;
    private Date requestTime;
    private String requestDate;
    private String method;
    private String url;
    private Map<String, String> body;
    private Object response;
    private Date responseTime;
    private Date createdAt;
    private Date updatedAt;

    private String outType;
    private String inType;
    private String doneStatus;
    private String failStatus;

    public Logs() {
    }


    public Logs(String status, String type, Date requestTime, String requestDate, String method, String url, Map<String, String> body, Object response, Date responseTime, Date createdAt, Date updatedAt) {
        Date now = new Date();

        this.status = status;
        this.type = type;
        this.requestTime = null == requestTime ? now : requestTime;
        this.requestDate = null == requestDate ? DateUtil.format(now, "yyyy-MM-dd") : requestDate;
        this.method = method;
        this.url = url;
        this.body = body;
        this.response = response;
        this.responseTime = responseTime;
        this.createdAt = null == createdAt ? now : createdAt;
        this.updatedAt = null == updatedAt ? now : updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOutType() {
        return "out";
    }

    public String getInType() {
        return "in";
    }

    public String getDoneStatus() {
        return "done";
    }

    public String getFailStatus() {
        return "fail";
    }

    @Override
    public String toString() {
        return "Logs{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", requestTime=" + requestTime +
                ", requestDate='" + requestDate + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", body=" + body +
                ", response=" + response +
                ", responseTime=" + responseTime +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", outType='" + outType + '\'' +
                ", inType='" + inType + '\'' +
                ", doneStatus='" + doneStatus + '\'' +
                ", failStatus='" + failStatus + '\'' +
                '}';
    }
}
