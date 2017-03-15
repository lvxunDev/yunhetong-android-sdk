package com.yunhetong.sdk.base;

/**
 * 在云合同注册平台注册的应用ID
 * 如果为null 报出异常
 */
public class SdkAppIdNullException extends SdkBaseException{
    public SdkAppIdNullException(String detailMessage) {
        super(detailMessage);
    }

    public SdkAppIdNullException() {
    }


}
