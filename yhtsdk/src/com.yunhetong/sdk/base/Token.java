package com.yunhetong.sdk.base;

import com.yunhetong.sdk.base.net.Action;

/**
 * 凭证
 */
public class Token {
    private String token;
    private long date;

    public Token() {
    }

    public void init(String token, long time) {
        setDate(time);
        setToken(token);
    }

    public void setDate() {
        this.date = System.currentTimeMillis();
    }

    public void setDate(long time) {
        if (time == 0) {
            setDate();
        } else {
            this.date = time;
        }
    }

    public long getDate() {
        return date;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取Token的接口
     */
    public interface TokenListener {
        void onToken(Action action);
    }


}
