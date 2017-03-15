package com.lx.yht;

import android.app.Application;
import android.widget.Toast;

import com.lx.yht.net.TokenRequest;
import com.yunhetong.sdk.YhtSdk;
import com.yunhetong.sdk.base.net.Action;
import com.yunhetong.sdk.base.net.HttpCallBackListener;
import com.yunhetong.sdk.base.Token;

import org.json.JSONException;
import org.json.JSONObject;

public class MyApplication extends Application implements Token.TokenListener {

    private static final String TAG = "Application";

    @Override
    public void onCreate() {
        super.onCreate();
        // 云合同SDK初始化
        YhtSdk.getInstance().initYhtSdk(this, this);
    }

    @Override
    public void onToken(Action action) {
        //　获取Token
        getToken(action);
    }

    /**
     * 请求Token
     */
    private void getToken(final Action action) {
        new TokenRequest().requestTokenWithUser(this, new HttpCallBackListener<String>() {
            @Override
            public void onHttpSucceed(String url, String object, int RequestCode) {
                String tokenStr = null;
                try {
                    JSONObject jsonObject = new JSONObject(object);
                    JSONObject jsonObject2 = jsonObject.optJSONObject("value");
                    tokenStr = jsonObject2.optString("token");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //TODO 初始化Token
                YhtSdk.getInstance().setToken(tokenStr, action);
            }

            @Override
            public void onHttpFail(String url, String msg, int RequestCode) {
                Toast.makeText(MyApplication.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
