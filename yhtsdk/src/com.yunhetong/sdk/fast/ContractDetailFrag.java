package com.yunhetong.sdk.fast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
import com.yunhetong.sdk.base.bean.YhtContractParter;
import com.yunhetong.sdk.fast.base.BaseActivity;
import com.yunhetong.sdk.fast.base.SdkBaseFragment;
import com.yunhetong.sdk.fast.view.ConfirmAlertDialog;
import com.yunhetong.sdk.fast.view.YhtWebView;
import com.yunhetong.sdk.tool.YhtDialogUtil;
import com.yunhetong.sdk.tool.YhtLog;

public class ContractDetailFrag extends SdkBaseFragment implements ConfirmAlertDialog.ConfirmAlertDialogListener, HttpCallBackListener<String> {
    private static final String TAG = "ContractDetailFrag";
    private static ContractDetailFrag.MenuStatus menuStatus = ContractDetailFrag.MenuStatus.none;

    public ContractDetailFrag(String contractId) {
        this.contractId = contractId;
    }

    private YhtWebView mYhtWebView;
    private YhtContract mRequestContractData;
    public enum MenuStatus {
        sign, invalid, signAndInvalid, none
    }

    private String contractId;
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yht_contract_detail_layout, container, false);
        initView(view);
        contractDetail();
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
        SubMenu sub = menu.addSubMenu(0, Menu.FIRST, 1, "菜单");
        sub.setIcon(R.drawable.yht_ic_more_opt);
        switch (menuStatus) {
            case sign:
                sub.add(0, Menu.FIRST + 1, 1, "确认签署").setIcon(R.drawable.yht_ic_contract_opt_sign);//
                sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                break;
            case signAndInvalid:
                sub.add(0, Menu.FIRST + 2, 1, "确认签署").setIcon(R.drawable.yht_ic_contract_opt_sign);//
                sub.add(0, Menu.FIRST + 3, 1, "作废").setIcon(R.drawable.yht_ic_contract_opt_del);//
                sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                break;
            case invalid:
                sub.add(0, Menu.FIRST + 4, 1, "作废").setIcon(R.drawable.yht_ic_contract_opt_del);//
                sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                break;
            case none:
                menu.clear();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST + 1:
                showSignDialog();
                break;
            case Menu.FIRST + 4:
                showInvalidDialog();
                break;
            case Menu.FIRST + 2:
                showSignDialog();
                break;
            case Menu.FIRST + 3:
                showInvalidDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static final byte contractsign = 1;
    private static final byte contractInvalit = 2;

    private void showSignDialog() {
        YhtDialogUtil.getComfireAlertDialog2(getActivity(), this, contractsign, "", "确认签署？").show();
    }

    private void showInvalidDialog() {
        YhtDialogUtil.getComfireAlertDialog2(getActivity(), this, contractInvalit, "", "确认作废？").show();
    }

    @Override
    public void ComfireAlertDialog2Sure(byte targetId, AlertDialog dialog, Object param) {
        dialog.dismiss();
        switch (targetId) {
            case contractsign:
                if (null == mRequestContractData) return;
                if (mRequestContractData.getParter().isHasSign()) {//判断是否有签名
                    contractSign();
                } else {
                    SignGeneratorActivity.gotoSignGeneratorActForResult(this);//没有签名跳转绘制签名页 有返回结果
                }
                break;
            case contractInvalit:
                contractInvalid();
                break;
            default:
                break;
        }
    }

    @Override
    public void ComfireAlertDialog2Cancel(byte targetId, AlertDialog dialog) {
        dialog.dismiss();
    }

    /**
     * 合同详情请求
     */
    public void contractDetail() {
        getProgressDialog().show();
        new SdkRequestManaer().contractDetail(contractId,null,YhtContent.REQUESTCODE_CONTRACTDETAIL, this);
    }

    /**
     * 加载详情界面
     */
    private void loadViewContractDetail() {
        String contractId = String.valueOf(mRequestContractData.getParter().getContractId());
        String contractUrl = SdkRequestManaer.getContractUrl(contractId);
        mYhtWebView.loadUrl(contractUrl);
    }

    /**
     * 合同签署请求
     */
    public void contractSign() {
        getProgressDialog().show();
        new SdkRequestManaer().contractSign( contractId,YhtContent.REQUESTCODE_CONTRACTSIGN,this);
    }

    /**
     * 合同作废请求
     */
    public void contractInvalid() {
        getProgressDialog().show();
        new SdkRequestManaer().contractInvalid(contractId,YhtContent.REQUESTCODE_CONTRACTINVALID,this );
    }


    @Override
    public void onHttpSucceed(String url, String object, int RequestCode) {
        getProgressDialog().dismiss();
        switch (RequestCode) {
            case YhtContent.REQUESTCODE_CONTRACTDETAIL:
                YhtContract contract = YhtContract.jsonToBean(object);
                YhtLog.e(TAG, "返回-合同详情 :" + contract.toString());
                mRequestContractData = contract;
                if (null != mYhtWebView ) {
                    setMenuState(mRequestContractData);
                    loadViewContractDetail();
                }
                break;
            case YhtContent.REQUESTCODE_CONTRACTINVALID:
                YhtLog.e(TAG, "合同作废成功");
//                contractDetail();
                Intent intent = new Intent();
                getActivity().setResult(ContractDetailActivity.result_code_invalid, intent);
                break;
            case YhtContent.REQUESTCODE_CONTRACTSIGN:
                contractDetail();
                YhtLog.e(TAG, "合同签署成功");
                Intent intent2 = new Intent();
                getActivity().setResult(ContractDetailActivity.result_code_signfinish, intent2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onHttpFail(String url, String msg, int RequestCode) {
        getProgressDialog().dismiss();
        YhtLog.e(TAG, "失败原因 :" + msg);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void setMenuState(YhtContract contract) {
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle(contract.getTitle());
        YhtContractParter parter = contract.getParter();
        if (parter.isSign() && parter.isInvalid()) {
            menuStatus = ContractDetailFrag.MenuStatus.signAndInvalid;
        }
        if (parter.isSign() && !parter.isInvalid()) {
            menuStatus = ContractDetailFrag.MenuStatus.sign;
        }
        if (!parter.isSign() && parter.isInvalid()) {
            menuStatus = ContractDetailFrag.MenuStatus.invalid;
        }
        if (!parter.isSign() && !parter.isInvalid()) {
            menuStatus = ContractDetailFrag.MenuStatus.none;
        }
        getActivity().invalidateOptionsMenu();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        YhtLog.e(TAG, "onActivityResult");
        if (requestCode == SignGeneratorActivity.requestCode && resultCode == Activity.RESULT_OK) {
            contractSign();
        }
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
