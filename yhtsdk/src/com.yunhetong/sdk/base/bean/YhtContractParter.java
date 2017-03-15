package com.yunhetong.sdk.base.bean;

/**
 */
public class YhtContractParter {
    private int sdkUserId;
    private long contractId;

    /**
     * 能否签名的权限
     */
    private boolean sign; //
    /**
     * 是否能作废权限
     */
    private boolean invalid;
    /**
     * 用户是否含有签名
     */
    private boolean hasSign;

    public boolean isHasSign() {
        return hasSign;
    }

    public void setHasSign(boolean hasSign) {
        this.hasSign = hasSign;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public int getSdkUserId() {
        return sdkUserId;
    }

    public void setSdkUserId(int sdkUserId) {
        this.sdkUserId = sdkUserId;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

}
