package com.yunhetong.sdk.fast.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.yunhetong.sdk.R;


public abstract class SdkBaseActivity extends BaseActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yht_fragment_content_layout);
        BaseActivity.addActivity(this);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.yht_fragment_content);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.yht_fragment_content, fragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseActivity.removeActivity(this);
    }

}
