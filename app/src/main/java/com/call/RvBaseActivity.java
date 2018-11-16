package com.call;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.bg.baseutillib.base.BaseActivity;
import com.bg.baseutillib.tool.ScreenUtils;
import com.bg.baseutillib.tool.SystemUtils;
import com.bg.baseutillib.view.TitleBarView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RvBaseActivity extends BaseActivity {

    Unbinder bind;

    @Override
    public void initViews(Bundle savedInstanceState) {
        bind = ButterKnife.bind(this);
    }

    public void setPaddingTop(TitleBarView titleBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            if (SystemUtils.getDeviceBrand().equalsIgnoreCase("huawei")) {

                if (Build.VERSION.SDK_INT != Build.VERSION_CODES.LOLLIPOP) {
                    if (titleBar != null)
                        titleBar.setPadding(0, ScreenUtils.getStatusHeight(mContext), 0, 0);
                }
            } else {
                if (titleBar != null)
                    titleBar.setPadding(0, ScreenUtils.getStatusHeight(mContext), 0, 0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null)
            bind.unbind();
    }
}
