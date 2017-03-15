package com.yunhetong.sdk.base.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yunhetong.sdk.tool.DateJsonDeserializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 签名
 */
public class YhtSign {
    private long id;
    private long sdkUserId;
    private String left;
    private String top;
    //状态 0：唯一的签名
    private String status;
    //签名是否使用过
    private String used;

    private Date gmtCreate;
    private Date gmtModify;
    //签名数据
    private String signData;
    private String signStr;
    private String userName;
    private int userType;
    private String signDate;


    public String getGmtCreate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(new Date(gmtCreate.getTime()));
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = new Date(gmtCreate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSignDate() {
        return signData;
    }

    public void setSignData(String sign) {
        this.signData = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public long getSdkUserId() {
        return sdkUserId;
    }

    public void setSdkUserIdd(long userId) {
        this.sdkUserId = userId;
    }

    /**
     * 签名是否使用过
     *
     * @return
     */
    public boolean isUsed() {
        return "1".equals(used);
    }

    /**
     * 唯一使用的签名
     *
     * @return
     */
    public boolean isDefalult() {
        return "0".equals(status);
    }

    public static YhtSign jsonToBean(String jsonObjStr) {
        if (jsonObjStr == null) return null;
        JSONObject json = null;
        try {
            JSONObject jsonO = new JSONObject(jsonObjStr);
            jsonO = jsonO.optJSONObject("value");
            json = jsonO.optJSONObject("sign");
            if (json == null) return null;
        } catch (JSONException e) {
            return null;
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                new DateJsonDeserializer()).create();
        YhtSign result = gson.fromJson(json.toString(), YhtSign.class);
        return result;
    }
}
