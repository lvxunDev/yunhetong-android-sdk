package com.yunhetong.sdk.fast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yunhetong.sdk.R;
import com.yunhetong.sdk.fast.base.SdkBaseActivity;

public class SignGeneratorActivity extends SdkBaseActivity {
    /**
     * 本页返回的签名数据
     */
    public static final String result_generator = "signDataStr";
    /**
     * 跳转本页的请求码
     */
    public static final int requestCode = 2;

    public static void gotoSignGeneratorActForResult(Activity context) {
        Intent intent = new Intent(context, SignGeneratorActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    public static void gotoSignGeneratorActForResult(Fragment frg) {
        Intent intent = new Intent(frg.getActivity(), SignGeneratorActivity.class);
        frg.startActivityForResult(intent, requestCode);
    }

    @Override
    protected Fragment createFragment() {
        return  new SignGeneratorFrag();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
