package com.lx.yht.net;

import android.content.Context;
import android.text.TextUtils;

import com.yunhetong.sdk.base.net.HttpCallBackListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Token请求
 */
public class TokenRequest {

    private static final String TAG = "TokenRequest";

    /**
     * 受邀方获取Token 并查看合同列表
     *
     * @param context
     */
    public void requestTokenWithUser(final Context context, final HttpCallBackListener<String> onCallBack) {
//        final String url = "替换成您的应用服务器地址";
        final String url = "http://testsdk.yunhetong.com/sdkdemo/token";
        doNetworkPost(context, url, onCallBack);
    }

    /**
     * 发起方:生成合同时获取Token
     *
     * @param context
     */
    public void requestTokenWithContract(final Context context, final HttpCallBackListener<String> onCallBack) {
//        final String url = "替换成您的应用服务器地址";
        final String url = "http://testsdk.yunhetong.com/sdkdemo/token_contract";
        doNetworkPost(context, url, onCallBack);
    }

    public void doNetworkPost(Context context, final String url, final HttpCallBackListener<String> onCallBack) {
        OkHttpUtils.post().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                onCallBack.onHttpFail(url, "网络错误", 2);
            }
            @Override
            public void onResponse(String response, int id) {
                //初始化 并跳转
                if (!TextUtils.isEmpty(response) && null != onCallBack) {
                    onCallBack.onHttpSucceed(url, response, 3);
                }
            }
        });
    }
}
