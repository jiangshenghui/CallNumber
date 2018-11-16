package com.bg.baseutillib.net.tools;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 收集所有创建的Activity，方便统一结束
 * 在每个Activity的onCreate()方法中调用addActivity
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();//使用List保存所有的Activity

    /**
     * 添加Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {//添加Activity到List集合
        activities.add(activity);
    }

    /**
     * 移除Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {//从List集合删除Activity
        activities.remove(activity);
    }

    /**
     * 移除所有的Activity
     */
    public static void finishAll() {//结束List集合中所有的Activity
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
