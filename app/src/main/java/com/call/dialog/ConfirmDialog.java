package com.call.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.call.R;

/**
 * Created by xuweihua on 2016/12/8.
 * 确定提示对话框，可以自定义提示信息
 */

public class ConfirmDialog {
    private Context mContext;
    private int mReqCode ;
    private String mMsgStr,mMsgOk,mMsgCancel,mPromptStr;
    private AlertDialog dlg = null;
    private TextView tvcompareName = null;
    private TextView tvName = null;
    private TextView tvIDcard;
    private TextView mPromptTxt;
    private  View contentView;
    private String comparename,name,idCard;

    public ConfirmDialog(Context context, int requestCode, String msg){
        this.mContext = context;
        this.mReqCode = requestCode;
        this.mMsgStr = msg;
    }
    public ConfirmDialog(Context context, int requestCode, String comparename,String name,String idCard){
        this.mContext = context;
        this.mReqCode = requestCode;
        this.comparename = comparename;
        this.name = name;
        this.idCard = idCard;
    }

    public interface DialogButtonClickListener{
        public void onConfirmDialogButtonClick(int requestCode, boolean result);
    }

    public void showDialog(final DialogButtonClickListener listener){
        dlg =  new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        dlg.show();
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_confirm, null);
        dlg.setContentView(contentView);

//        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
//        layoutParams.width = (int) (mContext.getResources().getDisplayMetrics().widthPixels*0.9);
//        contentView.setLayoutParams(layoutParams);

        tvcompareName = contentView.findViewById(R.id.tv_compare_name);
        tvName = contentView.findViewById(R.id.tv_name);
        tvIDcard = contentView.findViewById(R.id.tv_id_card);

        tvcompareName.setText(comparename);
        tvName.setText(name);
        tvIDcard.setText(idCard);

        contentView.findViewById(R.id.btninfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirmDialogButtonClick(mReqCode,true);
                dlg.dismiss();
            }
        });

    }
}
