package com.yunhetong.sdk;

import android.app.Application;
import android.text.TextUtils;

import com.yunhetong.sdk.base.net.Action;
import com.yunhetong.sdk.base.Token;
import com.yunhetong.sdk.base.TokenManager;
import com.yunhetong.sdk.base.net.YhtHttpClient;
import com.yunhetong.sdk.tool.YhtLog;


/**
 *
 */
public final class YhtSdk {
    private static YhtSdk instance = new YhtSdk();
    private Application mApplication;


    private YhtSdk() {
    }

    public static YhtSdk getInstance() {
        return instance;
    }

    //---------------------------------------------------------

    /**
     * 打开调试模式
     */
    public void setDebug(boolean debug) {
        YhtLog.DEBUG = debug;
    }

    /**
     * 初始化云合同
     * 初始化回调
     * 在回调接口的方法里，第三方开发者实现获取token的异步请求
     *
     * @param application
     * @param listener    回调接口
     */
    public void initYhtSdk(Application application, Token.TokenListener listener) {
        mApplication = application;
        YhtHttpClient.getInstance().initClient(application);
        TokenManager.getInstance().setTokenListener(listener);
    }

    /**
     * 初始化Token(登录凭证)
     *
     * @param token Token登录凭证
     */
    public void setToken(String token, Action action) {
        if (TextUtils.isEmpty(token)) throw new NullPointerException("Token is null");
        TokenManager.getInstance().initToken(mApplication, token, action);
    }


}
