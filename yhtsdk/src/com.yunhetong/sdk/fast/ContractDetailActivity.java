package com.yunhetong.sdk.fast;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import com.yunhetong.sdk.fast.base.SdkBaseActivity;


/**
 * 合同详情页
 * 调用方法启动：ContractDetailActivity.gotoContractDetailAct();
 */
public class ContractDetailActivity extends SdkBaseActivity {
    private static final String TAG = ContractDetailActivity.class.getSimpleName();
    public static final int request_code = 3;
    public static final int result_code_invalid = 4;
    public static final int result_code_signfinish = 5;

    /**
     * 如果第三方是从Activity跳转过来的 调用本方法
     * 请求码是 ContractDetailActivity.REQUEST_CODE
     *
     * @param act
     * @param contractId 用户合同Id
     */
    public static void gotoContractDetailActForResult(Activity act, String contractId) {
        Intent intent = new Intent(act, ContractDetailActivity.class);
        intent.putExtra(CONTRACT_ID, contractId);
        act.startActivityForResult(intent, request_code);
    }

    /**
     * 如果第三方是从Fragment跳转过来的 调用本方法
     * 请求码是 ContractDetailActivity.REQUEST_CODE
     *
     * @param fg
     * @param contractId 用户合同Id
     */
    public static void gotoContractDetailActForResult(Fragment fg, String contractId) {
        Intent intent = new Intent(fg.getActivity(), ContractDetailActivity.class);
        intent.putExtra(CONTRACT_ID, contractId);
        fg.startActivityForResult(intent, request_code);
    }

    private static final String CONTRACT_ID = "yht_contract_id";

    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return new ContractDetailFrag(getContractId());
    }


    private String getContractId() {
        Intent intent = getIntent();
        if (null == intent) return null;
        return intent.getStringExtra(CONTRACT_ID);
    }

}
