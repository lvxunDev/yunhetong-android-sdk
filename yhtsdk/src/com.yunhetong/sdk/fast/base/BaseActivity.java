package com.yunhetong.sdk.fast.base;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.yunhetong.sdk.tool.YhtDialogUtil;

import java.util.Stack;

public class BaseActivity extends AppCompatActivity {
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }


    /**
     * 进度条
     */
    private Dialog mProgressDialog;

    public Dialog getProgressDialog() {
        if (null == mProgressDialog) {
            mProgressDialog = YhtDialogUtil.getWaitDialog(this);
        }
        return mProgressDialog;
    }

    private static volatile Stack<Activity> activityStack;

    public static Stack<Activity> getStack() {
        if (activityStack == null) {
            synchronized (BaseActivity.class) {
                if (activityStack == null) {
                    activityStack = new Stack<>();
                }
            }
        }
        return activityStack;
    }

    /**
     * 添加Activity到栈
     *
     * @param activity
     */
    public static synchronized void addActivity(Activity activity) {
        BaseActivity.getStack().add(activity);
    }

    /**
     * finish and remove activity
     *
     * @param activity
     */
    public static synchronized void removeAndfinishActivity(Activity activity) {
        if (activity == null)
            return;
        if (activityStack != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing())
                activity.finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * finish and remove activity
     *
     * @param activity
     */
    public synchronized static void removeActivity(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * finish and remove activity
     *
     * @param activity
     */
    public synchronized static void removeAndfinishAllActivityEx(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (activity != null && activity != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
        addActivity(activity);
    }

    /**
     * 结束所有Activity
     */
    public synchronized static void finishAllActivity() {
        if (activityStack == null) return;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    public void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

    }

    public ActionBar getBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        return actionBar;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
