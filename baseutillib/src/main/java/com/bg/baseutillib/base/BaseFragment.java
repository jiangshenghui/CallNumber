package com.bg.baseutillib.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bg.baseutillib.base.permissions.inter.PermissionsResultListener;
import com.bg.baseutillib.view.dialog.CustomDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * Created by pc on 2017/9/18.
 * 有6.0权限申请的fragment基类
 */

public abstract class BaseFragment<T extends BaseRequestDao> extends RxFragment {

    protected Activity mActivity;
    private PermissionsResultListener mListener;
    private int mRequestCode;
    public T createRequestData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutResID(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createRequestData = onCreateRequestData();
        initData(savedInstanceState);
        initListener();
    }

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    /**
     * 设置布局文件
     *
     * @return 布局文件id, 如R.layout.xxx
     */
    public abstract int setLayoutResID();

    /**
     * 初始化控件
     *
     * @param savedInstanceState
     */
    public abstract void initViews(View view, Bundle savedInstanceState);

    /**
     * 初始化数据等业务逻辑
     *
     * @param savedInstanceState
     */
    public abstract void initData(Bundle savedInstanceState);

    /**
     * 初始化监听
     */
    public abstract void initListener();
    public abstract T onCreateRequestData();

    /**
     * 页面跳转
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(mActivity, clz));
    }

    /**
     * 携带数据的页面跳转
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mActivity, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (createRequestData != null)
            createRequestData.onDisposed();
    }

    /**
     * 其他 fragment 继承 BaseFragment 调用 performRequestPermissions 方法
     *
     * @param desc        首次申请权限被拒绝后再次申请给用户的描述提示
     * @param permissions 要申请的权限数组
     * @param requestCode 申请标记值
     * @param listener    实现的接口
     */
    protected void performRequestPermissions(String desc, String[] permissions, int requestCode, PermissionsResultListener listener) {
        if (permissions == null || permissions.length == 0) return;
        mRequestCode = requestCode;
        mListener = listener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkEachSelfPermission(permissions)) {// 检查是否声明了权限
                requestEachPermissions(desc, permissions, requestCode);
            } else {// 已经申请权限
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        } else {
            if (mListener != null) {
                mListener.onPermissionGranted();
            }
        }
    }

    /**
     * 申请权限前判断是否需要声明
     *
     * @param desc        首次申请权限被拒绝后再次申请给用户的描述提示
     * @param permissions 要申请的权限数组
     * @param requestCode 申请标记值
     */
    private void requestEachPermissions(String desc, String[] permissions, int requestCode) {
        if (shouldShowRequestPermissionRationale(permissions)) {// 需要再次声明
            showRationaleDialog(desc, permissions, requestCode);
        } else {
            requestPermissions(permissions, requestCode);
        }
    }

    /**
     * 弹出声明的 Dialog
     *
     * @param desc        首次申请权限被拒绝后再次申请给用户的描述提示
     * @param permissions 要申请的权限数组
     * @param requestCode 申请标记值
     */
    private void showRationaleDialog(String desc, final String[] permissions, final int requestCode) {
        new CustomDialog.Builder(mActivity)
                .setTitle("提示")
                .setMessage(desc)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        requestPermissions(permissions, requestCode);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    /**
     * 再次申请权限时，是否需要声明
     *
     * @param permissions 要申请的权限数组
     * @return true 需要申请权限,false 已申请权限
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检察每个权限是否申请
     *
     * @param permissions 要申请的权限数组
     * @return true 需要申请权限,false 已申请权限
     */
    private boolean checkEachSelfPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限结果的回调
     *
     * @param requestCode  申请标记值
     * @param permissions  要申请的权限数组
     * @param grantResults 验证申请的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) {
            if (checkEachPermissionsGranted(grantResults)) {
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            } else {// 用户拒绝申请权限
                if (mListener != null) {
                    mListener.onPermissionDenied();
                }
            }
        }
    }

    /**
     * 检查回调结果
     *
     * @param grantResults 验证申请的结果
     * @return true 验证通过
     */
    private boolean checkEachPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}