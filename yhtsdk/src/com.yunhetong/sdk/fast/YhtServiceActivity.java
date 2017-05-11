package com.yunhetong.sdk.fast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yunhetong.sdk.R;
import com.yunhetong.sdk.YhtContent;
import com.yunhetong.sdk.base.TokenManager;
import com.yunhetong.sdk.base.net.HttpCallBackListener;
import com.yunhetong.sdk.fast.base.BaseActivity;
import com.yunhetong.sdk.fast.view.YhtWebView;
import com.yunhetong.sdk.tool.YhtLog;


/**
 * 总入口
 */
public class YhtServiceActivity extends BaseActivity implements HttpCallBackListener<String> {
    private static final String TAG = YhtServiceActivity.class.getSimpleName();
    private static final String CONTRACT_ID = "yht_contract_id";

    public static void gotoYhtServiceActivity(Activity act, String contractId) {
        Intent intent = new Intent(act, YhtServiceActivity.class);
        intent.putExtra(CONTRACT_ID, contractId);
        act.startActivity(intent);
    }

    YhtWebView mYhtWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yht_service_detail_layout);
        initUI();
        loadViewContractDetail();
    }

    String getIntentData() {
        return null == getIntent() ? null : getIntent().getStringExtra("yht_contract_id");
    }

    void initUI() {
        YhtLog.e(TAG, "initUI");
        getBar().setTitle("签署合同");
        FrameLayout layout = findView(R.id.yht_layout_content);
        mYhtWebView = new YhtWebView(this);
        layout.addView(mYhtWebView);
    }


    /**
     * 加载详情界面
     */
    private void loadViewContractDetail() {
        String contractUrl = getContractUrl(getIntentData());
        mYhtWebView.loadUrl(contractUrl);
    }

    /**
     * 请求合同详情
     *
     * @param contractId 合同Id
     */
    public static String getContractUrl(String contractId) {
        String contractUrl = YhtContent.URL_YHT_SERVICE + "?contractId="
                + contractId + "&token=" + TokenManager.getInstance().getToken();
        YhtLog.e("YhtHttpClient", "ContractDetailUrl: " + contractUrl);
        return contractUrl;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mYhtWebView.getmWebView().canGoBack()) {
            YhtLog.e(TAG, "jump count :" + mYhtWebView.getmWebView().copyBackForwardList().getSize());
            mYhtWebView.getmWebView().goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mYhtWebView != null) {
            mYhtWebView.setVisibility();
            mYhtWebView.removeAllViews();
            mYhtWebView.destroy();
        }
        ViewGroup view = (ViewGroup) this.getWindow().getDecorView();
        view.removeAllViews();
    }


    @Override
    public void onHttpSucceed(String url, String response, int RequestCode) {
        YhtLog.e(TAG, "onHttpSucceed" + response);
    }

    @Override
    public void onHttpFail(String url, String msg, int RequestCode) {
        YhtLog.e(TAG, "onHttpFail" + msg);
    }
}
