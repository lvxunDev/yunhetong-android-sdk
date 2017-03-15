package com.yunhetong.sdk.fast;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yunhetong.sdk.R;
import com.yunhetong.sdk.SdkRequestManaer;
import com.yunhetong.sdk.YhtContent;
import com.yunhetong.sdk.base.net.HttpCallBackListener;
import com.yunhetong.sdk.base.bean.YhtContract;
import com.yunhetong.sdk.fast.base.BaseActivity;
import com.yunhetong.sdk.fast.base.SdkBaseFragment;
import com.yunhetong.sdk.fast.view.ConfirmAlertDialog;
import com.yunhetong.sdk.fast.view.YhtWebView;
import com.yunhetong.sdk.tool.YhtDialogUtil;
import com.yunhetong.sdk.tool.YhtLog;

public class ContractPreviewFrag extends SdkBaseFragment implements HttpCallBackListener<String> {
    private static final String TAG = "ContractDetailFrag";
    private boolean MENUE_STATE = true;

    public ContractPreviewFrag(String contractId) {
        this.contractId = contractId;
    }

    private YhtWebView mYhtWebView;
    private String contractId;

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yht_contract_detail_layout, container, false);
        initView(view);
        contractPreview();
        return view;
    }

    void initView(View v) {
        ((BaseActivity) getActivity()).getBar().setTitle(" ");
        FrameLayout layout = (FrameLayout) v.findViewById(R.id.yht_layout_content);
        mYhtWebView = new YhtWebView(getActivity());
        layout.addView(mYhtWebView);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (MENUE_STATE) {
            SubMenu sub = menu.addSubMenu(0, Menu.FIRST, 1, "菜单");
            sub.setIcon(R.drawable.yht_ic_more_opt);
            sub.add(0, Menu.FIRST + 1, 1, "确认签署").setIcon(R.drawable.yht_ic_contract_opt_sign);//
            sub.add(0, Menu.FIRST + 2, 1, "取消").setIcon(R.drawable.yht_ic_contract_opt_del);//
            sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST + 1:
                showSignDialog();
                break;
            case Menu.FIRST + 2:
                getActivity().finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSignDialog() {
        YhtDialogUtil.getComfireAlertDialog2(getActivity(), new ConfirmAlertDialog.ConfirmAlertDialogListener() {
            @Override
            public void ComfireAlertDialog2Sure(byte targetId, AlertDialog dialog, Object param) {
                dialog.dismiss();
                contractSignAll();
            }

            @Override
            public void ComfireAlertDialog2Cancel(byte targetId, AlertDialog dialog) {
                dialog.dismiss();
            }
        }, (byte) 1, "", "确认签署？").show();
    }

    //预览
    public void contractPreview() {
        getProgressDialog().show();
        new SdkRequestManaer().contractPreview( contractId,YhtContent.REQUESTCODE_CONTRACTPREVIEW,this);
    }

    //发起签署请求
    public void contractSignAll() {
        getProgressDialog().show();
        new SdkRequestManaer().contractSignAll( contractId,YhtContent.REQUESTCODE_CONTRACTSIGN_ALL,this);
    }

    @Override
    public void onHttpSucceed(String url, String object, int RequestCode) {
        getProgressDialog().dismiss();
        switch (RequestCode) {
            case YhtContent.REQUESTCODE_CONTRACTPREVIEW:
                YhtContract contract = YhtContract.jsonToBean(object);
                ((BaseActivity) getActivity()).getBar().setTitle(contract.getTitle());
                YhtLog.d(TAG, "返回-合同详情 :" + contract.toString());
                loadViewContractDetail();
                break;
            case YhtContent.REQUESTCODE_CONTRACTSIGN_ALL:
                Toast.makeText(getActivity(), "签署成功", Toast.LENGTH_SHORT).show();
                updateMenuState();
                break;
            default:
                break;
        }
    }

    private void updateMenuState() {
        MENUE_STATE = false;
        getActivity().invalidateOptionsMenu();
    }

    /**
     * 加载详情界面
     */
    private void loadViewContractDetail() {
        if (null != mYhtWebView) {
            String contractUrl = SdkRequestManaer.getPreviewContractUrl(contractId);
            mYhtWebView.loadUrl(contractUrl);
        }
    }

    @Override
    public void onHttpFail(String url, String msg, int RequestCode) {
        getProgressDialog().dismiss();
        YhtLog.d(TAG, "失败原因 :" + msg);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 资源销毁
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mYhtWebView != null) {
            mYhtWebView.setVisibility();
            mYhtWebView.removeAllViews();
            mYhtWebView.destroy();
            mYhtWebView = null;
            ViewGroup view = (ViewGroup) getActivity().getWindow().getDecorView();
            view.removeAllViews();
        }

    }
}
