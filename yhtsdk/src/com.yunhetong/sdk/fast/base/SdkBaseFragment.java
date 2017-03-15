package com.yunhetong.sdk.fast.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yunhetong.sdk.tool.YhtDialogUtil;

public class SdkBaseFragment extends Fragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 进度条
     */
    private Dialog mProgressDialog;
    public Dialog getProgressDialog() {
        if (null == mProgressDialog) {
            mProgressDialog = YhtDialogUtil.getWaitDialog(getActivity());
        }
        return mProgressDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
