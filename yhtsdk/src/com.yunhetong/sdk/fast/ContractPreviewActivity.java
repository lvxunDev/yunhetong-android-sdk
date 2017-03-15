package com.yunhetong.sdk.fast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.yunhetong.sdk.fast.base.SdkBaseActivity;


/**
 * 合同预览页
 * 调用方法启动：ContractPreviewActivity.gotoContractPreviewAct();
 */
public class ContractPreviewActivity extends SdkBaseActivity {
    private static final String TAG = ContractPreviewActivity.class.getSimpleName();

    private static final String CONTRACT_ID = "yht_contract_id";
    public static final int Request_code = 5;

    public static void gotoContractPreviewAct(Activity act, String contractId) {
        Intent intent = new Intent(act, ContractPreviewActivity.class);
        intent.putExtra(CONTRACT_ID, contractId);
        act.startActivity(intent);
    }

    @Override
    protected Fragment createFragment() {
        return new ContractPreviewFrag(getContractId());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String getContractId() {
        Intent intent = getIntent();
        if (null == intent) return null;
        return intent.getStringExtra(CONTRACT_ID);
    }

}
