package com.yunhetong.sdk.base.net;

import android.support.annotation.NonNull;
import com.android.volley.Request;

import java.util.Map;

//请求操作
public class Action {
    public String url;
    public byte requestCode;
    public int requestMode;
    public Map<String, String> map;
    public HttpCallBackListener<String> listener;

    public Action(String url, byte requestCode, int requestMode, Map<String, String> map, HttpCallBackListener listener) {
        this.url = url;
        this.requestCode = requestCode;
        this.requestMode = requestMode;
        this.map = map;
        this.listener = listener;
    }

    public static void doAction(@NonNull Action action) {
        if (action.requestMode == Request.Method.GET) {
            YhtHttpClient.getInstance().yhtNetworkGet(action.url, action.requestCode, action.map, action.listener);
        } else if (action.requestMode == Request.Method.POST) {
            YhtHttpClient.getInstance().yhtNetworkPost(action.url, action.requestCode, action.map, action.listener);
        }
    }
}
