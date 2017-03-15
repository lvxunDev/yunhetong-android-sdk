package com.yunhetong.sdk.base.net;

/**
 * 请求响应绑定事件
 *
 */
public interface HttpCallBackListener<T> {
    void onHttpSucceed(String url, T response, int RequestCode);

    void onHttpFail(String url, String msg, int RequestCode);
}
