package com.lx.yht;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lx.yht.adapter.ContractAdapter;
import com.lx.yht.net.TokenRequest;
import com.yunhetong.sdk.YhtSdk;
import com.yunhetong.sdk.base.net.HttpCallBackListener;
import com.yunhetong.sdk.base.bean.YhtContract;
import com.yunhetong.sdk.fast.ContractPreviewActivity;
import com.yunhetong.sdk.tool.YhtLog;
import com.yunhetong.sdk.fast.base.BaseActivity;
import com.yunhetong.sdk.fast.ContractDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContractListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "ContractListActivity";

    public static void gotoContractListAct(Context context) {
        Intent intent = new Intent(context, ContractListActivity.class);
        context.startActivity(intent);
    }

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contracts);
        initBar();
        requestToken();
        initUi();
    }

    private void initUi() {
        mListView = (ListView) findViewById(R.id.lv_contracts);
        assert mListView != null;
        mListView.setOnItemClickListener(this);
    }

    private void initBar() {
        getBar().setTitle("合同列表");
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        YhtContract colContract = (YhtContract) mListView.getAdapter().getItem(position);
        gotoContractDetail(colContract);
    }

    //跳转合同详情
    private void gotoContractDetail(YhtContract contract) {
        //详情
        ContractDetailActivity.gotoContractDetailActForResult(this, String.valueOf(contract.getId()));
    }

    private void requestToken() {
        getProgressDialog().show();
        new TokenRequest().requestTokenWithUser(this, new HttpCallBackListener<String>() {
            @Override
            public void onHttpSucceed(String url, String object, int RequestCode) {
                getProgressDialog().dismiss();
                YhtLog.d(TAG, "onHttpSucceed  data:" + object);
                String tokenStr = null;
                List<YhtContract> contracts = null;
                try {
                    JSONObject jsonObject = new JSONObject(object);
                    JSONObject jsonObject2 = jsonObject.optJSONObject("value");
                    tokenStr = jsonObject2.optString("token");
                    JSONArray jsonArr = jsonObject2.optJSONArray("contractList");
                    contracts = new ArrayList<>();
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonO = jsonArr.getJSONObject(i);
                        YhtContract contract = new YhtContract();
                        contract.setId(jsonO.optLong("id"));
                        contract.setTitle(jsonO.optString("title"));
                        contract.setStatus(jsonO.optString("status"));
                        contracts.add(contract);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //初始化 并跳转
                if (!TextUtils.isEmpty(tokenStr) && null != contracts && contracts.size() > 0) {
                    YhtSdk.getInstance().setToken(tokenStr, null);
                    mListView.setAdapter(new ContractAdapter(ContractListActivity.this, contracts));
                }
            }

            @Override
            public void onHttpFail(String url, String msg, int RequestCode) {
                getProgressDialog().dismiss();
                Toast.makeText(ContractListActivity.this, msg, Toast.LENGTH_SHORT).show();
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
        YhtLog.d(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ContractDetailActivity.request_code) {
            if (resultCode == ContractDetailActivity.result_code_invalid) {
                Toast.makeText(this, "作废成功", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == ContractDetailActivity.result_code_signfinish) {
                Toast.makeText(this, "签署完成", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == ContractPreviewActivity.Request_code && resultCode == ContractDetailActivity.request_code) {
            if (null != mListView) {
                requestToken();
            }
        }
    }
}
