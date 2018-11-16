package com.bg.baseutillib.net.base;


import android.content.Context;
import android.util.Log;

import com.bg.baseutillib.R;
import com.bg.baseutillib.net.tools.NetUtils;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.net.exception.ERROR;
import com.bg.baseutillib.tool.ToastUtil;
import com.bg.baseutillib.view.LoadingProgressDialog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 自定义Subscriber
 */
public abstract class BaseObserver<T> implements Observer<T> {
    private Context context;
    private boolean isNeedCahe;
    private boolean isShowLoadingDialog = true;//是否显示加载进度对话框
    private LoadingProgressDialog mLoadingProgressDialog;

    public BaseObserver(Context context) {
        this.context = context;

        if (isShowLoadingDialog && mLoadingProgressDialog == null) {
            mLoadingProgressDialog = new LoadingProgressDialog(context, R.style.LoadingDialog);
        }
    }

    public BaseObserver(Context context, boolean isShowLoadingDialog) {
        this.context = context;
        this.isShowLoadingDialog = isShowLoadingDialog;

        if (isShowLoadingDialog && mLoadingProgressDialog == null) {
            mLoadingProgressDialog = new LoadingProgressDialog(context, R.style.LoadingDialog);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d("ThomasDebug", "BaseObserver : Http is start");

        if (!NetUtils.isNetworkAvailable(context)) {
            ToastUtil.showLongToast( "网络异常");
            onComplete();//一定要手动调用
        }

        // 显示进度条
        showProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        Log.d("BaseObserver", "onError : " + e.getMessage());

        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ERROR.UNKNOWN));
        }

        //关闭等待进度条
        hideProgressDialog();
    }

    @Override
    public void onComplete() {
        Log.d("BaseObserver", "onCompleted : Http is complete");

        //关闭等待进度条
        hideProgressDialog();
    }

    public abstract void onError(ApiException e);

    /**
     * 隐藏联网进度对话框
     */
    private void hideProgressDialog() {
        if (isShowLoadingDialog && mLoadingProgressDialog != null && mLoadingProgressDialog.isShowing()) {
            mLoadingProgressDialog.dismiss();
        }
    }

    /**
     * 显示联网进度对话框
     */
    private void showProgressDialog() {
        if (isShowLoadingDialog && mLoadingProgressDialog != null) {
            if (mLoadingProgressDialog.isShowing()) {
                mLoadingProgressDialog.dismiss();
            }

//            mLoadingProgressDialog.setTransparentBackground(setProgressDialogTransparent());
            mLoadingProgressDialog.show();
        }
    }

//    /**
//     * 设置进度对话框背景是否透明
//     *
//     * @return true透明，false半透明
//     */
//    protected boolean setProgressDialogTransparent() {
//        return false;
//    }
}

