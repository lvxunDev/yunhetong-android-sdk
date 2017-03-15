package com.yunhetong.sdk.fast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.yunhetong.sdk.R;
import com.yunhetong.sdk.SdkRequestManaer;
import com.yunhetong.sdk.YhtContent;
import com.yunhetong.sdk.base.net.HttpCallBackListener;
import com.yunhetong.sdk.fast.base.BaseActivity;
import com.yunhetong.sdk.fast.base.SdkBaseFragment;
import com.yunhetong.sdk.fast.view.YhtSignDrawView;


public class SignGeneratorFrag extends SdkBaseFragment implements HttpCallBackListener<String> {
    private YhtSignDrawView mDrawView;
    private Button mSignCommit, mSignClear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.yht_sign_generator, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        ((BaseActivity )getActivity()).getBar().setTitle("签名绘制");
        mSignCommit = (Button) v.findViewById(R.id.yht_btn_sign_apply);
        mSignClear = (Button) v.findViewById(R.id.yht_btn_sign_clear);
        mDrawView = (YhtSignDrawView) v.findViewById(R.id.yht_draw_panel);
        mDrawView.setFragment(this);
        mSignCommit.setEnabled(true);
        mSignClear.setEnabled(true);
        mSignCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signGerenater();
            }
        });
        mSignClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClearAction();
            }
        });
    }

    /**
     * 新增签名请求
     */
    public void signGerenater() {
        if (mDrawView != null && mDrawView.isSign) {
            getProgressDialog().show();
            new SdkRequestManaer().signGenerate(getStringSign(), YhtContent.REQUESTCODE_SIGNGENERATE, this);
        } else {
            Toast.makeText(getActivity(), "未签名", Toast.LENGTH_SHORT).show();
        }
    }

    private String getStringSign() {
        if (mDrawView == null) return null;
        return mDrawView.getStringSign();
    }

    private void setClearAction() {
        setBtEnable(false);
        if (mDrawView != null) {
            mDrawView.clearSign();
        }
    }

    //对清除按钮和创建按钮设置状态
    public void setBtEnable(boolean enable) {
        if (mSignCommit != null && mSignCommit.isEnabled() != enable) {
            mSignCommit.setEnabled(enable);
            mSignClear.setEnabled(enable);
        }
    }


    @Override
    public void onHttpSucceed(String url, String object, int RequestCode) {
        getProgressDialog().dismiss();
        if (RequestCode == YhtContent.REQUESTCODE_SIGNGENERATE) {
            String signviewStr = getStringSign();
            Intent intent = new Intent();
            intent.putExtra(SignGeneratorActivity.result_generator, signviewStr);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }

    @Override
    public void onHttpFail(String url, String msg, int RequestCode) {
        getProgressDialog().dismiss();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDrawView != null)
            mDrawView.destroy();
    }

}
