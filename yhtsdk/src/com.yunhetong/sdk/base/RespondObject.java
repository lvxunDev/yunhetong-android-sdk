package com.yunhetong.sdk.base;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yunhetong.sdk.tool.DateJsonDeserializer;

import java.util.Date;

/**
 * 响应体的基本信息
 */
public class RespondObject {
    public String code;   //错误代码
    public String subCode;  //次级错误代码
    private String message;  //提示信息
//    private Map value;  //返回内容


    public String getMessage() {
        return message;
    }

    public boolean isOk() {
        if (!TextUtils.isEmpty(code) && code.equals("200")) return true;
        return false;
    }

    /**
     * token错误
     */
    public boolean isInvalid() {
        if (!TextUtils.isEmpty(code) && code.equals("400")) return true;
        return false;
    }

    /**
     * 签名不存在
     */
    public boolean signNotExist() {
        if (subCode.equals("10303")) return true;
        return false;
    }

    public static RespondObject parseJSONToRespond(String jsonObjStr) {
        if (null != jsonObjStr) {
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                    new DateJsonDeserializer()).create();
            RespondObject result = gson.fromJson(jsonObjStr, RespondObject.class);
            return result;
        }
        return null;
    }
}
