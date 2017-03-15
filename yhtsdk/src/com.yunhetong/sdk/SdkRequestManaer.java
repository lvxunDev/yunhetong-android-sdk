package com.yunhetong.sdk;

import android.text.TextUtils;

import com.yunhetong.sdk.base.net.HttpCallBackListener;
import com.yunhetong.sdk.base.net.IYhtSdkRequest;
import com.yunhetong.sdk.base.TokenManager;
import com.yunhetong.sdk.base.net.YhtHttpClient;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public final class SdkRequestManaer implements IYhtSdkRequest {

    @Override
    public void contractDetail(String contractId, String notificaParams, byte requestCode, HttpCallBackListener<String> onCallBackListener) {
        if (null == onCallBackListener && TextUtils.isEmpty(contractId)) return;
        String url = YhtContent.URL_CONTRACTDETAIL;
        final WeakReference<HttpCallBackListener> wrOnCallBackListener = new WeakReference<HttpCallBackListener>(onCallBackListener);
        Map<String, String> map = new HashMap<>();
        map.put("contractId", contractId);
        if (notificaParams != null)
            map.put("notificaParams", notificaParams);
        YhtHttpClient.getInstance().yhtNetworkPost(url, requestCode, map, wrOnCallBackListener.get());
    }

    @Override
    public void contractPreview(String contractId, byte requestCode, HttpCallBackListener<String> onCallBackListener) {
        String url = YhtContent.URL_CONTRACTPREVIEW;
        final WeakReference<HttpCallBackListener> wrOnCallBackListener = new WeakReference<HttpCallBackListener>(onCallBackListener);
        Map<String, String> map = new HashMap<>();
        map.put("contractId", contractId);
        YhtHttpClient.getInstance().yhtNetworkPost(url, requestCode, map, wrOnCallBackListener.get());
    }

    @Override
    public void contractSignAll(String contractId, byte requestCode, HttpCallBackListener<String> onCallBackListener) {
        String url = YhtContent.URL_CONTRACTSIGN_ALL;
        final WeakReference<HttpCallBackListener> wrOnCallBackListener = new WeakReference<HttpCallBackListener>(onCallBackListener);
        Map<String, String> map = new HashMap<>();
        map.put("contractId", contractId);
        YhtHttpClient.getInstance().yhtNetworkPost(url, requestCode, map, wrOnCallBackListener.get());
    }

    /**
     * 请求合同详情
     *
     * @param contractId 合同Id
     */
    public static String getContractUrl(String contractId) {
        String contractUrl = YhtContent.URL_CONTRACTDETAILVIEW + "?contractId=" + contractId + "&token=" + TokenManager.getInstance().getToken();
        return contractUrl;
    }

    /**
     * 请求合同预览
     *
     * @param contractId 合同Id
     */
    public static String getPreviewContractUrl(String contractId) {
        String contractUrl = YhtContent.URL_CONTRACTPREVIEWVIEW + "?contractId=" + contractId + "&token=" + TokenManager.getInstance().getToken();
        return contractUrl;
    }


    @Override
    public void contractInvalid(String contractId, byte requestCode, HttpCallBackListener<String> onCallBackListener) {
        if (null == onCallBackListener && TextUtils.isEmpty(contractId)) return;
        String url = YhtContent.URL_CONTRACTINVALID;
        WeakReference<HttpCallBackListener> wrOnCallBackListener = new WeakReference<HttpCallBackListener>(onCallBackListener);
        Map<String, String> map = new HashMap<>();
        map.put("contractId", contractId);
        YhtHttpClient.getInstance().yhtNetworkPost(url, requestCode, map, wrOnCallBackListener.get());
    }

    @Override
    public void contractSign(String contractId, byte requestCode, HttpCallBackListener<String> onCallBackListener) {
        if (null == onCallBackListener && TextUtils.isEmpty(contractId)) return;
        String url = YhtContent.URL_CONTRACTSIGN;
        WeakReference<HttpCallBackListener> wrOnCallBackListener = new WeakReference<HttpCallBackListener>(onCallBackListener);
        Map<String, String> map = new HashMap<>();
        map.put("contractId", contractId);
        YhtHttpClient.getInstance().yhtNetworkPost(url, requestCode, map, wrOnCallBackListener.get());
    }

    @Override
    public void signDetail(byte requestCode, HttpCallBackListener<String> onCallBackListener) {
        String url = YhtContent.URL_SIGNDETAIL;
        WeakReference<HttpCallBackListener> wrOnCallBackListener = new WeakReference<HttpCallBackListener>(onCallBackListener);
        YhtHttpClient.getInstance().yhtNetworkGet(url, requestCode, null, wrOnCallBackListener.get());
    }

    @Override
    public void signDelete(byte requestCode, HttpCallBackListener<String> onCallBackListener) {
        String url = YhtContent.URL_SIGNDELETE;
        WeakReference<HttpCallBackListener> wrOnCallBackListener = new WeakReference<HttpCallBackListener>(onCallBackListener);
        YhtHttpClient.getInstance().yhtNetworkPost(url, requestCode, null, wrOnCallBackListener.get());
    }

    @Override
    public void signGenerate(String signData, byte requestCode, HttpCallBackListener<String> onCallBackListener) {
        if (null == onCallBackListener && TextUtils.isEmpty(signData)) return;
        String url = YhtContent.URL_SIGNGENERATE;
        WeakReference<HttpCallBackListener> wrOnCallBackListener = new WeakReference<HttpCallBackListener>(onCallBackListener);
        Map<String, String> map = new HashMap<>();
        map.put("sign", signData);
        YhtHttpClient.getInstance().yhtNetworkPost(url, requestCode, map, wrOnCallBackListener.get());
    }

}
