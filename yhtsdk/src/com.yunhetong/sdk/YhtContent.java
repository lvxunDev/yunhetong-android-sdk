package com.yunhetong.sdk;

/**
 * 常量类
 * 通知TAG
 * 接口的url
 * 接口的请求码
 */
public final class YhtContent {

    //测试
    public static final String YHT_HOST = "http://testsdk.yunhetong.com";
    //正式
//    public static final String YHT_HOST = "http://sdk.yunhetong.com";


    public static final String URL_YHT_SERVICE = YHT_HOST + "/sdk/contract/hView";

    /**
     * 合同详情
     */
    public static final String URL_CONTRACTDETAIL = YHT_HOST + "/sdk/contract/view";
    public static final byte REQUESTCODE_CONTRACTDETAIL = 4;
    public static final String URL_CONTRACTDETAILVIEW = YHT_HOST + "/sdk/contract/mobile/view";


    /**
     * 合同签署
     */
    public static final String URL_CONTRACTSIGN = YHT_HOST + "/sdk/contract/sign";
    public static final byte REQUESTCODE_CONTRACTSIGN = 6;

    /**
     * 合同作废
     */
    public static final String URL_CONTRACTINVALID = YHT_HOST + "/sdk/contract/invalid";
    public static final byte REQUESTCODE_CONTRACTINVALID = 7;

    /**
     * 创建签名
     */
    public static final String URL_SIGNGENERATE = YHT_HOST + "/sdk/sign/createSign";
    public static final byte REQUESTCODE_SIGNGENERATE = 8;

    /**
     * 查看签名
     */
    public static final String URL_SIGNDETAIL = YHT_HOST + "/sdk/sign/getSign";
    public static final byte REQUESTCODE_SIGNDETAIL = 9;

    /**
     * 删除签名
     */
    public static final String URL_SIGNDELETE = YHT_HOST + "/sdk/sign/delSign";
    public static final byte REQUESTCODE_SIGNDELETE = 10;

}
