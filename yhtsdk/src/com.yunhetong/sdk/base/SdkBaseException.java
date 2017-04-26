package com.yunhetong.sdk.base;
/**
 */
public class SdkBaseException extends RuntimeException {
    public SdkBaseException(String detailMessage) {
        super(detailMessage);
    }

    public SdkBaseException() {
    }

    public SdkBaseException printStackTrace(String ff) {
        return new SdkBaseException(ff);
    }

}
