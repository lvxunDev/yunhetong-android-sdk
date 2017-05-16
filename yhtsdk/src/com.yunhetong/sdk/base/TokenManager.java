package com.yunhetong.sdk.base;


import android.app.Application;
import android.content.Context;

import com.yunhetong.sdk.base.net.Action;
import com.yunhetong.sdk.tool.YhtLog;
import com.yunhetong.sdk.tool.YhtSPUtils;

/**
 * Token登录凭证的管理类
 * 单例 在app生命周期内存活
 */
public final class TokenManager {

    private static final String TOKEN = "TOKEN_DATA";
    private static final String TOKEN_TIME = "TOKEN_DATA_TIME";
    private static final String TAG = TokenManager.class.getSimpleName();
    private static TokenManager instance = new TokenManager();

    private TokenManager() {
    }

    public static TokenManager getInstance() {
        return instance;
    }


    private Token mToken;
    private Context mContext;
    private Token.TokenListener mListener;

    public void initToken(Context context, String token, Action action) {
        if (null == mToken) {
            mToken = new Token();
        }
        this.mContext = context;
        mToken.init(token, 0);
        YhtSPUtils.put(mContext, TOKEN, mToken.getToken());
        YhtSPUtils.put(mContext, TOKEN_TIME, mToken.getDate());
        YhtLog.e(TAG, " YhtSPUtils: " + token + "   token time: " + String.valueOf(mToken.getDate()));


        if (null != action) {
            Action.doAction(action);
        }
    }


    private Token getTokenBean() {
        if (null == mToken) {
            mToken = new Token();
            String tkStr = YhtSPUtils.getString(mContext, TOKEN, "");
            Long tkTime = YhtSPUtils.getLong(mContext, TOKEN_TIME, 0);
            mToken.init(tkStr, tkTime);
        }
        return mToken;
    }

    /**
     * 刷新凭证有效期
     */
    public void refreshToken() {
        getTokenBean().setDate();
    }

    /**
     * 获取登录凭证
     *
     * @return string
     */
    public String getToken() {
        return getTokenBean().getToken();
    }


    /**
     * 判断token 是否过期
     *
     * @return boolean
     */
    public boolean isOverdue() {
        if ((System.currentTimeMillis() - this.getTokenBean().getDate()) > 30 * 60 * 1000)
            return true;
//        if ((System.currentTimeMillis() - this.getTokenBean().getDate()) > 10000) return true;//超过十秒算超时
        return false;
    }

    /**
     * 设置回调
     *
     * @param listener
     */
    public void setTokenListener(Token.TokenListener listener) {
        this.mListener = listener;
    }

    public Token.TokenListener getTokenListener() {
        return mListener;
    }


}
