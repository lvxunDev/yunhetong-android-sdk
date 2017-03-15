package com.yunhetong.sdk.base.net;


/**
 * SDK的请求接口
 */
public interface IYhtSdkRequest {

    /*************************合同的请求************************/
    /**
     * 合同详情
     * @param  notificaParams 通知地址
     * @param onCallBackListener 网络请求的回调
     * @param contractId         合同ID
     */
    void contractDetail(String contractId, String notificaParams,byte requestCode,HttpCallBackListener<String> onCallBackListener);

    /**
     * 合同预览
     * @param onCallBackListener
     * @param contractId
     */
    void contractPreview(String contractId,byte requestCode,HttpCallBackListener<String> onCallBackListener);

    /**
     * 合同作废
     *
     * @param onCallBackListener
     * @param contractId
     */
    void contractInvalid(String contractId,byte requestCode,HttpCallBackListener<String> onCallBackListener);

    /**
     * 合同签署
     * 单方签署
     * @param onCallBackListener
     * @param contractId
     */
    void contractSign(String contractId,byte requestCode,HttpCallBackListener<String> onCallBackListener);

    /**
     * 合同签署
     * 双方签署
     * @param onCallBackListener
     * @param contractId
     */
    void contractSignAll(String contractId,byte requestCode,HttpCallBackListener<String> onCallBackListener);

    /**************************签名的请求****************************/
    /**
     * 签名查看
     *
     * @param onCallBackListener 网络请求的回调
     */
    void signDetail(byte requestCode,HttpCallBackListener<String> onCallBackListener);

    /**
     * 签名删除
     *
     * @param onCallBackListener
     */
    void signDelete(byte requestCode,HttpCallBackListener<String> onCallBackListener);

    /**
     * 签名新增/绘制
     *
     * @param onCallBackListener
     * @param signData
     */
    void signGenerate(String signData,byte requestCode,HttpCallBackListener<String> onCallBackListener);
}
