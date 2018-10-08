package com.klg.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaphiss on 2018/9/19.
 * API请求响应体
 */
public class ResponseResult {
    private static String CODE_KEY = "code";
    private static String DATA_KEY = "data";
    private static String MSG_KEY = "msg";

    /**
     * 返回请求成功体
     *
     * @param data 响应数据
     * @return
     */
    public static Map<String, Object> SuccessResponse(Map<String, Object> data){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(CODE_KEY, 0);
        map.put(DATA_KEY, data);

        return map;
    }

    /**
     * 返回请求失败体
     *
     * @return
     */
    public static Map<String, Object> FailResponse(String msg){
        Map<String, Object> map = new HashMap<String, Object>();

        map.put(CODE_KEY, -1);
        map.put(DATA_KEY, null);
        map.put(MSG_KEY, msg);

        return map;
    }
}
