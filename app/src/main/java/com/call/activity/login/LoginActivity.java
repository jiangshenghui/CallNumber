package com.call.activity.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;
import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.tool.SharedPreferencesUtil;
import com.bg.baseutillib.tool.SystemUtils;
import com.bg.baseutillib.tool.ToastUtil;
import com.call.R;
import com.call.RvBaseActivity;
import com.call.activity.service.SetServiceActivity;
import com.call.event.LoginEvent;
import com.call.net.login.LoginDao;
import com.call.net.login.request.CommonBody;
import com.call.net.login.request.ParamsSet;
import com.call.net.login.response.UserBean;
import com.call.utils.AppConfig;
import com.call.utils.AppUserData;
import com.call.utils.Utils;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends RvBaseActivity {
     //co
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tbLook)
    ToggleButton tbLook;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.ivCleanPhone)
    ImageView ivCleanPhone;
    @BindView(R.id.ivRemendPw)
    ImageView ivRemendPw;
    private boolean rememberPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
    }

    @Override
    public int setLayoutResID() {
        return R.layout.login_activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.GRAY);
//        }
        initPermission();
        rememberPass = SharedPreferencesUtil.readBoolean("rememberPass");
        if(rememberPass){
            ivRemendPw.setImageResource(R.mipmap.checkout);
            etPhone.setText( AppUserData.getInstance().getMobile());
            etPassword.setText( AppUserData.getInstance().getPassWord());
        }else {
            etPassword.setText("");
            ivRemendPw.setImageResource(R.mipmap.checkout_no);
        }
    }
    //  下面是android 6.0以上的动态授权

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    @Override
    public void initListener() {
        tbLook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etPassword.setSelection(etPassword.length());
            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = etPhone.getText().toString();
                if (phone.length() > 0) {
                    ivCleanPhone.setVisibility(View.VISIBLE);
                } else {
                    ivCleanPhone.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public LoginDao onCreateRequestData() {
        return new LoginDao();
    }

    @OnClick({R.id.btnLogin,  R.id.tvForgetPassword, R.id.ivCleanPhone,R.id.rePassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin://登录
                login();
                break;
            case R.id.tvForgetPassword://忘记密码
                Bundle bundle = new Bundle();
                bundle.putString("phone",etPhone.getText().toString());
                break;
            case R.id.ivCleanPhone:
                etPhone.setText("");
                break;
            case R.id.rePassword://记住密码
                rememberPass = !rememberPass;
                SharedPreferencesUtil.writeBoolean("rememberPass",rememberPass);
                if(rememberPass){
                    ivRemendPw.setImageResource(R.mipmap.checkout);
                }else {
                    ivRemendPw.setImageResource(R.mipmap.checkout_no);
                }
                break;

        }
    }
    private Dialog mDialog;
    /**
     * 登录
     */
    private void login() {
        final String account = etPhone.getText().toString().trim();
        final String pwd = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showShortToast("请输入用户名");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showShortToast("请输入密码");
            return;
        }
//        mDialog = Utils.showProgressDialog(this);

        CommonBody  commonBody = new CommonBody();
        ParamsSet paramsSet1 = new ParamsSet();
        paramsSet1.name = "loginName";
        paramsSet1.value = "test";

        ParamsSet paramsSet2 = new ParamsSet();
        paramsSet2.name = account;
        paramsSet2.value = pwd;

        List<ParamsSet> paramsSets =new ArrayList<ParamsSet>();
        paramsSets.add(paramsSet1);
        paramsSets.add(paramsSet2);
        commonBody.paramsSet = paramsSets;
        mDialog = Utils.showProgressDialog(this);
        ((LoginDao)createRequestData).login(this, commonBody, pwd, new RxNetCallback<UserBean>() {
            @Override
            public void onSuccess(UserBean userBean) {
                if (mDialog != null) {
                    mDialog.dismiss();
                    mDialog = null;
                }
                if (userBean != null ) {//登录成功
                    ToastUtil.showShortToast("登录成功");
//                    AppUserData.getInstance().setSessionId(userBean.getBussData());
                    AppUserData.getInstance().setMobile(account);
                    AppUserData.getInstance().setPassWord(pwd);
                    AppUserData.getInstance().setIsLogin(true);
                    EventBus.getDefault().post(new LoginEvent(true));
                    startActivity(SetServiceActivity.class);
                    finish();
                }else {
                    ToastUtil.showShortToast("用户名或密码错误");
                }
            }

            @Override
            public void onError(ApiException e) {
                if (mDialog != null) {
                    mDialog.dismiss();
                    mDialog = null;
                }
                if(!TextUtils.isEmpty(e.getMessage())){
                    ToastUtil.showShortToast(e.getMessage());
                }
            }
        });
    }

    public static void startLoginActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (etPhone != null && !TextUtils.isEmpty(AppUserData.getInstance().getUserBean().getMobile())) {
            etPhone.setText(AppUserData.getInstance().getUserBean().getMobile());
            etPhone.setFocusable(true);
            etPhone.setFocusableInTouchMode(true);
            etPhone.requestFocus();
            ivCleanPhone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (requestCode == 125) {
            finish();
        }
    }
}
