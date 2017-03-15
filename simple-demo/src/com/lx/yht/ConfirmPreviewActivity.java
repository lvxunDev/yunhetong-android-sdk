package com.lx.yht;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lx.yht.net.TokenRequest;
import com.yunhetong.sdk.YhtSdk;
import com.yunhetong.sdk.base.net.HttpCallBackListener;
import com.yunhetong.sdk.fast.ContractDetailActivity;
import com.yunhetong.sdk.fast.ContractPreviewActivity;
import com.yunhetong.sdk.fast.base.BaseActivity;
import com.yunhetong.sdk.tool.YhtLog;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmPreviewActivity extends BaseActivity {
    private static final String TAG = "ConfirmActivity";

    public static void gotoConfirmPreviewAct(Context context) {
        Intent intent = new Intent(context, ConfirmPreviewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        initBar();
        initUi();
    }


    private void initBar() {
        getBar().setTitle("确认支付细节");
    }

    private void initUi() {
        Button button = (Button) findViewById(R.id.button);
        assert button != null;
        button.setText("合同预览");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpPreViewContract();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void jumpPreViewContract() {
        getProgressDialog().show();
        new TokenRequest().requestTokenWithContract(this, new HttpCallBackListener<String>() {
            @Override
            public void onHttpSucceed(String url, String response, int RequestCode) {
                getProgressDialog().dismiss();
                String token = null;
                long contractId = 0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    contractId = jsonObject.optLong("contractId");
                    token = jsonObject.optString("token");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //初始化 并跳转
                if (!TextUtils.isEmpty(token)) {
                    YhtSdk.getInstance().setToken(token, null);
                    ContractPreviewActivity.gotoContractPreviewAct(ConfirmPreviewActivity.this, contractId + "");
                }
            }

            @Override
            public void onHttpFail(String url, String msg, int RequestCode) {
                getProgressDialog().dismiss();
                Toast.makeText(ConfirmPreviewActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 第三方页面的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        YhtLog.d(TAG, "onActivityResult");
        if (requestCode == ContractDetailActivity.request_code) {
            if (resultCode == ContractDetailActivity.result_code_signfinish) {
                Toast.makeText(this, "签署成功", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == ContractDetailActivity.result_code_invalid) {
                Toast.makeText(this, "作废成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
