package com.yunhetong.sdk.base.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yunhetong.sdk.tool.DateJsonDeserializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 合同
 */
public class YhtContract implements Serializable {

    //合同id
    private long id;
    //合同状态
    private String status;
    //合同标题
    private String title;
    //合同附加信息
    private YhtContractParter partner;
    //签名信息
    private List<YhtSign> singerList;


    public static YhtContract jsonToBean(String jsonObjStr) {
        if (jsonObjStr == null) return null;
        JSONObject json = null;
        try {
            JSONObject jsonO = new JSONObject(jsonObjStr);
            json = jsonO.optJSONObject("value");
            if (json == null) return null;
        } catch (JSONException e) {
            return null;
        }
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                new DateJsonDeserializer()).create();
        YhtContract result = gson.fromJson(json.toString(), YhtContract.class);
        return result;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public YhtContractParter getParter() {
        return partner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
