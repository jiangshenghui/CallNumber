package com.bg.baseutillib.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bg.baseutillib.R;

/**
 * 自定义加载中对话框
 */
public class LoadingProgressDialog extends ProgressDialog {

    private View mRootView;
    private TextView tvLoadDialog;//提示文字控件

    private String mTextPrompt;//提示文字

    public LoadingProgressDialog(Context context) {
        super(context);
    }

    public LoadingProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public LoadingProgressDialog(Context context, int theme, String textPrompt) {
        super(context, theme);
        this.mTextPrompt = textPrompt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外部设置
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        setContentView(R.layout.loading_dialog);

        mRootView = findViewById(R.id.llLoadingDialog);
        tvLoadDialog = (TextView) findViewById(R.id.tv_load_dialog);

        if (!TextUtils.isEmpty(mTextPrompt)) {
            tvLoadDialog.setVisibility(View.VISIBLE);
            tvLoadDialog.setText(mTextPrompt);
        } else {
            tvLoadDialog.setVisibility(View.GONE);
        }

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

//    /**
//     * 是否设置透明背景
//     *
//     * @param isTransparent true设置透明，false设置半透明
//     */
//    public void setTransparentBackground(boolean isTransparent) {
//        if (mRootView == null) {
//            return;
//        }
//        
//        if (isTransparent) {//透明
//            mRootView.setBackgroundResource(R.drawable.loading_dialog_transparent_bg_shape);
//        } else {//半透明
//            mRootView.setBackgroundResource(R.drawable.loading_dialog_translucent_bg_shape);
//        }
//    }
}