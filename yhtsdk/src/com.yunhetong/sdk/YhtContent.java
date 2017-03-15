package com.yunhetong.sdk;

/**
 * 常量类
 * 通知TAG
 * 接口的url
 * 接口的请求码
 */
public final class YhtContent {

    //测试
    public static final String YHT_HOST = "http://testsdk.yunhetong.com/sdk";
    //正式
//    public static final String YHT_HOST = "http://sdk.yunhetong.com/sdk";


    /**
     * 合同详情
     * 1.通知TAG
     * 2.url
     * 3.请求码
     */
    public static final String URL_CONTRACTDETAIL = YHT_HOST + "/contract/view";
    public static final byte REQUESTCODE_CONTRACTDETAIL = 4;
    public static final String URL_CONTRACTDETAILVIEW = YHT_HOST + "/contract/mobile/view";

    /**
     * 合同预览详情
     * 1.通知TAG
     * 2.url
     * 3.请求码
     */
    public static final String URL_CONTRACTPREVIEW = YHT_HOST + "/contract/preview";
    public static final byte REQUESTCODE_CONTRACTPREVIEW = 5;
    public static final String URL_CONTRACTPREVIEWVIEW = YHT_HOST + "/contract/mobile/preview";

    /**
     * 合同签署
     */
    public static final String URL_CONTRACTSIGN = YHT_HOST + "/contract/sign";
    public static final byte REQUESTCODE_CONTRACTSIGN = 6;

    /**
     * 合同双方一起签署
     */
    public static final String URL_CONTRACTSIGN_ALL = YHT_HOST + "/contract/allSign";
    public static final byte REQUESTCODE_CONTRACTSIGN_ALL = 3;
    /**
     * 合同作废
     */
    public static final String URL_CONTRACTINVALID = YHT_HOST + "/contract/invalid";
    public static final byte REQUESTCODE_CONTRACTINVALID = 7;

    /**
     * 创建签名
     */
    public static final String URL_SIGNGENERATE = YHT_HOST + "/sign/createSign";
    public static final byte REQUESTCODE_SIGNGENERATE = 8;

    /**
     * 查看签名
     */
    public static final String URL_SIGNDETAIL = YHT_HOST + "/sign/getSign";
    public static final byte REQUESTCODE_SIGNDETAIL = 9;

    /**
     * 删除签名
     */
    public static final String URL_SIGNDELETE = YHT_HOST + "/sign/delSign";
    public static final byte REQUESTCODE_SIGNDELETE = 10;

}
