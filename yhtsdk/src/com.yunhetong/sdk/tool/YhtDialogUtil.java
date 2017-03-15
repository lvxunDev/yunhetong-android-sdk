package com.yunhetong.sdk.tool;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunhetong.sdk.R;
import com.yunhetong.sdk.fast.view.ConfirmAlertDialog;


/**
 * 获取Dialog的工具类
 *
 */
public class YhtDialogUtil {

    public static Dialog getWaitDialog(Context context, int resStr) {
        View view = LayoutInflater.from(context).inflate(R.layout.yht_dialog_progress, null);
        ((TextView) view.findViewById(R.id.yht_view_progress_dialog_tip))
                .setText(context.getResources().getString(resStr));
        Dialog dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = YhtScreenUtil.dip2px(context,120); // 宽度
        lp.height = YhtScreenUtil.dip2px(context,120); // 高度
        dialogWindow.setAttributes(lp);
        return dialog;
    }

    public static Dialog getWaitDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.yht_dialog_progress, null);
        Dialog dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = YhtScreenUtil.dip2px(context,120); // 宽度
        lp.height = YhtScreenUtil.dip2px(context,120); // 高度
        dialogWindow.setAttributes(lp);
        return dialog;
    }

    public static ConfirmAlertDialog getComfireAlertDialog2(Context context,
                                                            ConfirmAlertDialog.ConfirmAlertDialogListener ls, byte targetId, Object params,
                                                            String msg) {
        ConfirmAlertDialog d = new ConfirmAlertDialog(context);
        d.setComfireAlertDialog2Click(ls);
        d.setTarget(targetId, params);
        d.confirm_alert_msg.setText(msg);
        return d;
    }

    public static ConfirmAlertDialog getComfireAlertDialog2(Context context,
                                                            ConfirmAlertDialog.ConfirmAlertDialogListener ls, byte targetId, Object params,
                                                            String msg, String no, String yes) {
        ConfirmAlertDialog d = new ConfirmAlertDialog(context);
        d.setComfireAlertDialog2Click(ls);
        d.setTarget(targetId, params);
        d.confirm_alert_msg.setText(msg);
        d.cancelBt.setText(no);
        d.sureBt.setText(yes);
        return d;
    }
}
