package com.yunhetong.sdk.fast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yunhetong.sdk.fast.base.SdkBaseActivity;

public class SignDetailActivity extends SdkBaseActivity{
    private static final String TAG = "SignDetailActivity";

    public static void gotoSignDetailAct(Activity context) {
        Intent intent = new Intent(context, SignDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Fragment createFragment() {
        return new SignDetailFrag();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
