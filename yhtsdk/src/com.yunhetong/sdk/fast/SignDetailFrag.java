package com.yunhetong.sdk.fast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yunhetong.sdk.R;
import com.yunhetong.sdk.SdkRequestManaer;
import com.yunhetong.sdk.YhtContent;
import com.yunhetong.sdk.base.net.HttpCallBackListener;
import com.yunhetong.sdk.base.bean.YhtSign;
import com.yunhetong.sdk.fast.base.BaseActivity;
import com.yunhetong.sdk.fast.base.SdkBaseFragment;
import com.yunhetong.sdk.tool.ImageBase64Util;
import com.yunhetong.sdk.tool.YhtLog;

/**
 * Created by Administrator on 2016/12/8 0008.
 */

public class SignDetailFrag extends SdkBaseFragment implements HttpCallBackListener<String> {

    private static final String TAG = "SignDetailFrag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yht_sign_detail, container, false);
        initView(view);
        signDetail();
        return view;
    }

    private Button mSignDelete;
    private ImageView mSignAdd, mSignView;

    void initView(View view) {
        ((BaseActivity) getActivity()).getBar().setTitle("签名查看");
        mSignDelete = (Button) view.findViewById(R.id.yht_btn_delete);
        mSignAdd = (ImageView) view.findViewById(R.id.yht_iv_add);
        mSignView = (ImageView) view.findViewById(R.id.yht_iv_sign);
        mSignDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signDelete();
            }
        });
        mSignAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignGeneratorActivity.gotoSignGeneratorActForResult(SignDetailFrag.this);
            }
        });
    }

    /**
     * 签名查询请求
     */
    public void signDetail() {
        getProgressDialog().show();
        new SdkRequestManaer().signDetail(YhtContent.REQUESTCODE_SIGNDETAIL,this);
    }

    /**
     * 签名删除请求
     */
    public void signDelete() {
        getProgressDialog().show();
        new SdkRequestManaer().signDelete(YhtContent.REQUESTCODE_SIGNDELETE,this);
    }


    void showAddView() {
        if (mSignDelete == null || mSignView == null || mSignAdd == null) return;
        mSignView.setVisibility(View.GONE);
        mSignDelete.setVisibility(View.GONE);
        mSignAdd.setVisibility(View.VISIBLE);
    }

    void showSignView(String signStr) {
        if (mSignDelete == null || mSignView == null || mSignAdd == null) return;
        mSignAdd.setVisibility(View.GONE);
        mSignDelete.setVisibility(View.VISIBLE);
        mSignView.setVisibility(View.VISIBLE);
        mSignView.setImageBitmap(ImageBase64Util.Base64ToBitmap(signStr, 4));
    }

    void showSignView(YhtSign signData) {
        if (mSignDelete == null || mSignView == null || mSignAdd == null) return;
        mSignAdd.setVisibility(View.GONE);
        if (!signData.isUsed()) mSignDelete.setVisibility(View.VISIBLE);
        mSignView.setVisibility(View.VISIBLE);
        mSignView.setImageBitmap(ImageBase64Util.Base64ToBitmap(signData.getSignDate(), 4));
    }

    @Override
    public void onHttpSucceed(String url, String object, int RequestCode) {
        getProgressDialog().dismiss();
        switch (RequestCode) {
            case YhtContent.REQUESTCODE_SIGNDELETE:
                //签名删除成功
                showAddView();
                break;
            case YhtContent.REQUESTCODE_SIGNDETAIL:
                //签名查看成功
                YhtSign signData = YhtSign.jsonToBean(object);
                if (null != signData) {
                    showSignView(signData);
                } else {
                    showAddView();
                    Toast.makeText(getActivity(), "您还没有创建签名", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }


    @Override
    public void onHttpFail(String url, String msg, int RequestCode) {
        getProgressDialog().dismiss();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        YhtLog.d(TAG, "onActivityResult");
        if (requestCode == SignGeneratorActivity.requestCode && resultCode == Activity.RESULT_OK) {
            String signbt = data.getStringExtra(SignGeneratorActivity.result_generator);
            showSignView(signbt);
        }
    }
}
