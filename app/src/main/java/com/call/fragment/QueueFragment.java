package com.call.fragment;

import android.os.Bundle;
import com.call.R;
import com.call.RvBaseFragment;
import com.call.net.login.LoginDao;
/**
 * 队列设置Fragment
 */

public class QueueFragment extends RvBaseFragment {


    public int setLayoutResID() {
        return R.layout.fragment_queue;
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
