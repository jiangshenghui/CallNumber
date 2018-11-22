package com.call;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.bg.baseutillib.BgApplication;
import com.bg.baseutillib.tool.FileUploadUtil;
import com.call.net.ApiManager;
import java.util.LinkedList;
import java.util.List;


public class MyApplication extends BgApplication {

    private static MyApplication mInstance = null;
    private List<Activity> enterList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FileUploadUtil.init(getApplicationContext(), ApiManager.HOST);
//        UMShareAPI.get(this);
    }
    public static MyApplication getInstance() {
        return mInstance;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //添加Activity到容器中
    public void addEnterActivity(Activity activity)
    {
        enterList.add(activity);
    }
    //遍历所有Activity并finish
    public void exitEnterAllActivity()
    {
        for(Activity activity:enterList)
        {
            activity.finish();
        }
    }

}
