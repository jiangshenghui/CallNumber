package com.bg.baseutillib;

import android.app.Application;
import android.content.Context;

import com.bg.baseutillib.tool.SharedPreferencesUtil;
import com.bg.baseutillib.tool.TextViewDrawable;
import com.bg.baseutillib.tool.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * Created by pc on 2018/3/7.
 * BgApplication 初始化相关的数据
 */

public class BgApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        //本地存储
        SharedPreferencesUtil.init(getApplicationContext());
        //Glide图片加载
//        GlideManager.init(getApplicationContext());
        //Toast显示
        ToastUtil.init(getApplicationContext());
        //TextView 的drawable属性
        TextViewDrawable.init(getApplicationContext());
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.backgroundColor, R.color.text_color_666);//全局设置主题颜色
                layout.setHeaderMaxDragRate(40);//最大显示下拉高度/Header标准高度
                //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
                // 指定为经典Header，默认是 贝塞尔雷达Header
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setFooterMaxDragRate(20);//最大显示下拉高度/Footer标准高度
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static Context getContext() {
        return mContext;
    }
}
