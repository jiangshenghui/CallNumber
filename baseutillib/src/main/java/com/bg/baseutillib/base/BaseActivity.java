package com.bg.baseutillib.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bg.baseutillib.R;
import com.bg.baseutillib.base.permissions.inter.PermissionsResultListener;
import com.bg.baseutillib.net.tools.ActivityCollector;
import com.bg.baseutillib.tool.SharedPreferencesUtil;
import com.bg.baseutillib.tool.SystemUtils;
import com.bg.baseutillib.tool.ToastUtil;
import com.bg.baseutillib.view.dialog.CustomDialog;
import com.githang.statusbar.StatusBarCompat;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by pc on 2017/9/18.
 * 有6.0权限申请的activity抽象类
 */

public abstract class BaseActivity<T extends BaseRequestDao> extends RxAppCompatActivity {

    private PermissionsResultListener mListener;
    private int mRequestCode;
    protected Context mContext;
    public T createRequestData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResID());
        initStatus();
        mContext = this;
        createRequestData = onCreateRequestData();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        ActivityCollector.addActivity(this);
        initViews(savedInstanceState);//初始化控件
        initData(savedInstanceState);//初始化数据等业务逻辑
        initListener();//初始化监听

        ToastUtil.init(this);
        SharedPreferencesUtil.init(this);
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
    public abstract void initViews(Bundle savedInstanceState);

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
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * 页面跳转
     */
    public void startActivityForResult(Class<?> clz, int type) {
        startActivityForResult(new Intent(BaseActivity.this, clz), type);
    }

    /**
     * 携带数据的页面跳转
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 携带数据的页面跳转
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle, int type) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, type);
    }

    /**
     * 点击edittext以外区域隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        try {
            if (getWindow().superDispatchTouchEvent(ev)) {
                return true;
            }
        } catch (Exception e) {
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回键返回事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (createRequestData != null)
            createRequestData.onDisposed();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 其他 activity 继承 BaseActivity 调用 performRequestPermissions 方法
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
    @RequiresApi(api = Build.VERSION_CODES.M)
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
    @TargetApi(Build.VERSION_CODES.M)
    private void showRationaleDialog(String desc, final String[] permissions, final int requestCode) {
        if (!TextUtils.isEmpty(desc)) {
            new CustomDialog.Builder(this)
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
        } else {
            requestPermissions(permissions, requestCode);
        }
    }

    /**
     * 再次申请权限时，是否需要声明
     *
     * @param permissions 要申请的权限数组
     * @return true 需要申请权限,false 已申请权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查每个权限是否申请
     *
     * @param permissions 要申请的权限数组
     * @return true 需要申请权限,false 已申请权限
     */
    private boolean checkEachSelfPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
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
     * @param grantResults
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

    /**
     * 加载手机沉侵式
     */
    private void initStatus() {
        if (SystemUtils.getDeviceBrand().equalsIgnoreCase("vivo") && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.text_color_999));
        } else {
            StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.title_bg_color));
        }
    }
}
