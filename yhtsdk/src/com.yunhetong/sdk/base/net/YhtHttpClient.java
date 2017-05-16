package com.yunhetong.sdk.base.net;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.yunhetong.sdk.base.RespondObject;
import com.yunhetong.sdk.base.SdkAppIdNullException;
import com.yunhetong.sdk.base.Token;
import com.yunhetong.sdk.base.TokenManager;
import com.yunhetong.sdk.tool.YhtLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * 网络请求
 */
public class YhtHttpClient {
    private static final String TAG = "YhtHttpClient";

    private static YhtHttpClient mHttpClient = null;

    private String appId;
    private Context mContext;

    private YhtHttpClient() {
    }

    public static YhtHttpClient getInstance() {
        if (mHttpClient == null) {
            mHttpClient = new YhtHttpClient();
        }
        return mHttpClient;
    }

    public void initClient(Context context) {
        mContext = context;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(TAG))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

        setAppId();
    }

    private void setAppId() {
        ApplicationInfo appInfo = null;
        try {
            appInfo = mContext.getPackageManager()
                    .getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            String yhtSdk_appId = appInfo.metaData.getString("YhtSdk_AppId");
            appId = yhtSdk_appId.replace("id_", "");
            YhtLog.e(TAG, "appid :" + appId);
        } catch (Exception e) {
            throw new SdkAppIdNullException("appId 没有找到,请在AndroidManifest中配置 <meta-data android:name=\"YhtSdk_AppId\" android:value=\"id_2016050516421\" />");
        }
    }

    private String getAppId() {
        return appId;
    }


    /**
     * 临时保存的请求码
     * 如果请求前判断token失效或者请求后判定token失效,存储失效的请求码
     * 默认为0
     */
    public void yhtNetworkPost(String url, byte requestCode, Map<String, String> params, HttpCallBackListener<String> onCallBack) {
        Map<String, String> map = params;
        if (null == map) {
            map = new HashMap<>();
        }
        map.put("token", TokenManager.getInstance().getToken());
        try {
            Long.valueOf(getAppId());
            map.put("appid", getAppId());
        } catch (NumberFormatException e) {
            Toast.makeText(mContext,"appid格式错误,请检查", Toast.LENGTH_LONG).show();
        }
        if (YhtLog.DEBUG) {
            paramstoString(map);
        }
        doNetworkPost(url, requestCode, map, onCallBack);
    }


    /**
     * 本地凭证有效性检查
     *
     * @return
     */
    private boolean tokenValidationCheck() {
        if (TokenManager.getInstance().isOverdue() || (TokenManager.getInstance().getToken() == null)) {
            YhtLog.e(TAG, "本地token超时，发起回调");
            return true;
        }
        return false;
    }


    /**
     * POST
     *
     * @param url
     * @param requestCode
     * @param params
     * @param onCallBack
     */
    public void doNetworkPost(final String url, final byte requestCode, final Map<String, String> params, final HttpCallBackListener<String> onCallBack) {
        if (tokenValidationCheck()) {
            Action action = new Action(url, requestCode, Request.Method.POST, params, onCallBack);
            TokenManager.getInstance().getTokenListener().onToken(action);
            return;
        }
        //OkHttp
        PostFormBuilder builder = OkHttpUtils.post().url(url);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addParams(entry.getKey(), entry.getValue());
                YhtLog.e(TAG, "key= " + entry.getKey() + " and value= " + entry.getValue());
            }
        }
        builder.build().execute(new MyCallBack(url, requestCode, params, onCallBack));

    }

    public void yhtNetworkGet(String url, byte requestCode, final Map<String, String> params, final HttpCallBackListener<String> onCallBack) {
        Map<String, String> map = params;
        if (null == map) {
            map = new HashMap<>();
        }
        map.put("token", TokenManager.getInstance().getToken());
        try {
            Long.valueOf(getAppId());
            map.put("appid", getAppId());
        } catch (Exception e) {
            Toast.makeText(mContext,"appid格式错误,请检查", Toast.LENGTH_LONG).show();
        }
        doNetworkGet(url, requestCode, map, onCallBack);
    }


    private void doNetworkGet(final String url, final byte requestCode, final Map<String, String> params, final HttpCallBackListener<String> onCallBack) {
        if (tokenValidationCheck()) {
            Action action = new Action(url, requestCode, Request.Method.GET, params, onCallBack);
            TokenManager.getInstance().getTokenListener().onToken(action);
            return;
        }

        String paramsStr = paramstoString(params);
        paramsStr = paramsStr.substring(0, paramsStr.length() - 1);
        final String urlCurr = url + "?" + paramsStr;
        //OkHttp
        OkHttpUtils.get().url(urlCurr).build().execute(new MyCallBack(urlCurr, requestCode, params, onCallBack));

    }

    private class MyCallBack extends StringCallback {
        private String url;
        private byte requestCode;
        private Map<String, String> params;
        private HttpCallBackListener<String> onCallBack;

        private MyCallBack(String url, byte requestCode, Map<String, String> params, HttpCallBackListener<String> onCallBack) {
            this.url = url;
            this.requestCode = requestCode;
            this.params = params;
            this.onCallBack = onCallBack;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            YhtLog.e(TAG, "onErrorResponse { url : " + url + " error:" + e.getMessage() + "}");
            if (onCallBack != null)
                onCallBack.onHttpFail(url, "网络异常，请稍后再试", requestCode);
        }

        @Override
        public void onResponse(String response, int id) {
            RespondObject respondObject = RespondObject.parseJSONToRespond(response);
            if (respondObject.isOk()) {
                TokenManager.getInstance().refreshToken();
                if (onCallBack != null)
                    onCallBack.onHttpSucceed(url, response, requestCode);
            } else if (respondObject.isInvalid()) {
                YhtLog.e(TAG, "服务端返回参数判断--发起回调");
                Action action = new Action(url, requestCode, Request.Method.POST, params, onCallBack);
                TokenManager.getInstance().getTokenListener().onToken(action);
            } else {
                if (onCallBack != null)
                    onCallBack.onHttpFail(url, respondObject.getMessage(), requestCode);
            }
        }

        @Override
        public String parseNetworkResponse(okhttp3.Response response, int id) throws IOException {
            YhtLog.e(TAG, "parseNetworkResponse" + response.message());
            return super.parseNetworkResponse(response, id);
        }
    }

    private String paramstoString(Map<String, String> params) {
        if (params != null && params.size() > 0) {
            String paramsEncoding = "UTF-8";
            StringBuilder encodedParams = new StringBuilder();
            try {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                    encodedParams.append('&');
                    YhtLog.e(TAG, "key= " + entry.getKey() + " and value= " + entry.getValue());
                }
                return encodedParams.toString();
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
            }
        }
        return null;
    }


}
