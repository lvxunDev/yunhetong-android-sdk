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

        Button button4 = (Button) findViewById(R.id.button);
        button4.setVisibility(View.VISIBLE);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmPreviewActivity.gotoConfirmPreviewAct(LoginActivity.this);
            }
        });

        Button button = (Button) findViewById(R.id.button1);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmActivity.gotoConfirmAct(LoginActivity.this);
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        assert button2 != null;
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContractListActivity.gotoContractListAct(LoginActivity.this);
            }
        });
        if (false) {
            Button button3 = (Button) findViewById(R.id.button3);
            assert button3 != null;
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
