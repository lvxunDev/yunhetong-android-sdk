package com.lx.yht;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.yunhetong.sdk.fast.SignDetailActivity;
import com.yunhetong.sdk.fast.SignGeneratorActivity;
import com.yunhetong.sdk.fast.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initBar();
        initUi();
    }

    private void initBar() {
        getBar().setTitle("云合同SDK演示demo");
        getBar().setDisplayShowHomeEnabled(false);
        getBar().setDisplayHomeAsUpEnabled(false);
    }

    private void initUi() {

        //发起方签署入口
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmActivity.gotoConfirmAct(LoginActivity.this);
            }
        });

        //接收方签署入口
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContractListActivity.gotoContractListAct(LoginActivity.this);
            }
        });

        //签名查看入口
        if (false) {
            Button button3 = (Button) findViewById(R.id.button3);
            button3.setVisibility(View.VISIBLE);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SignDetailActivity.gotoSignDetailAct(LoginActivity.this);
                }
            });
        } else {
            findViewById(R.id.button3).setVisibility(View.GONE);
        }


    }


}
