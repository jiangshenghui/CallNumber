package com.bg.baseutillib.tool;

/** 
 * Created by lijie on 2018/4/18
 * Description:防止快速点击工具类
 * Email: 731098696@qq.com
 * Version：1.0
 */
public class FastClickUtil {

    private static long lastClickTime = 0;//上次点击的时间

    private static int spaceTime = 500;//时间间隔


    /**
     * 是否快速事件
     *
     * @return true 是 false 否
     */
    public static boolean isFastClick() {

        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick;//是否允许点击
        if (currentTime - lastClickTime > spaceTime) {
            isAllowClick = false;
        } else {
            isAllowClick = true;
        }
        lastClickTime = currentTime;
        return isAllowClick;

    }

    /**
     * 是否快速事件
     *
     * @return true 是 false 否
     */
    public static boolean isFastClick(int spaceTime) {

        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick;//是否允许点击
        if (currentTime - lastClickTime > spaceTime) {
            isAllowClick = false;
        } else {
            isAllowClick = true;
        }
        lastClickTime = currentTime;
        return isAllowClick;

    }
}

