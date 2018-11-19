package com.call.fragment;

import android.os.Bundle;
import com.call.R;
import com.call.RvBaseFragment;
import com.call.net.login.LoginDao;
/**
 *
 * 语言设置
 */
public class LanguageFragment extends RvBaseFragment {


    public int setLayoutResID() {
        return R.layout.fragment_language;
    }

    @Override
    public void initData(Bundle savedInstanceState) {


    }

    @Override
    public void initListener() {

    }

    public LoginDao onCreateRequestData() {
        return new LoginDao();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
