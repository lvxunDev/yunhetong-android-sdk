package com.yunhetong.sdk.fast.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yunhetong.sdk.R;


public class ConfirmAlertDialog extends AlertDialog implements
        View.OnClickListener {
    private static final String TAG = "ConfirmAlertDialog";
    private ConfirmAlertDialogListener ls;
    public TextView confirm_alert_msg;
    public Button sureBt;
    public Button cancelBt;

    public ConfirmAlertDialog(Context context) {
        super(context);
        init(context);
    }

    public ConfirmAlertDialog(Context context, boolean cancelable,
                              OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public ConfirmAlertDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }


    private void init(Context context) {
        View ui = LayoutInflater.from(context).inflate(R.layout.yht_dialog_confirm_alert, null);
        this.setView(ui);
        confirm_alert_msg = (TextView) ui.findViewById(R.id.yht_confirm_alert_msg);
        cancelBt = (Button) ui.findViewById(R.id.yht_confirm_alert_cancel);
        sureBt = (Button) ui.findViewById(R.id.yht_confirm_alert_sure);
        cancelBt.setOnClickListener(this);
        sureBt.setOnClickListener(this);
    }


    public void setComfireAlertDialog2Click(ConfirmAlertDialogListener ls) {
        this.ls = ls;
    }


    public interface ConfirmAlertDialogListener {
        /**
         * @param targetId
         * @param dialog
         * @param param
         */
        void ComfireAlertDialog2Sure(byte targetId, AlertDialog dialog,
                                     Object param);

        void ComfireAlertDialog2Cancel(byte targetId, AlertDialog dialog);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.yht_confirm_alert_cancel) {
            if (ls != null) {
                ls.ComfireAlertDialog2Cancel(targetId, this);
            }
        } else if (id == R.id.yht_confirm_alert_sure) {
            if (ls != null) {
                ls.ComfireAlertDialog2Sure(targetId, this, mParamObj);
            }
        }
    }

    public byte targetId = 0;

    public byte getTargetId() {
        return targetId;
    }

    public Object mParamObj;

    public void setTarget(byte targetId, Object params) {
        this.targetId = targetId;
        this.mParamObj = params;
    }

    public String title;
    public String msg;

    public static ConfirmAlertDialog getComfireAlertDialog2(Context context,
                                                            ConfirmAlertDialogListener ls, byte targetId, Object params,
                                                            String msg) {
        ConfirmAlertDialog d = new ConfirmAlertDialog(context);
        d.setComfireAlertDialog2Click(ls);
        d.setTarget(targetId, params);
        d.confirm_alert_msg.setText(msg);
        return d;
    }

    public static ConfirmAlertDialog getComfireAlertDialog2(Context context,
                                                            ConfirmAlertDialogListener ls, byte targetId, Object params,
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
