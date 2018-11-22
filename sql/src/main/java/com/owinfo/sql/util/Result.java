package com.owinfo.sql.util;

import com.alibaba.fastjson.JSONObject;

/**
 * author: qiyong
 * 2018/11/12 16:20
 */
public class Result {

    public static JSONObject success(){
        JSONObject result = new JSONObject();
        result.put("code", 200);
        result.put("message", "操作成功");
        return result;
    }

    public static JSONObject success(Object data){
        JSONObject result = new JSONObject();
        result.put("code", 200);
        result.put("data", data);
        result.put("message", "操作成功");
        return result;
    }

    public static JSONObject error(String message){
        JSONObject result = new JSONObject();
        result.put("code", 500);
        result.put("message", message);
        return result;
    }
}
