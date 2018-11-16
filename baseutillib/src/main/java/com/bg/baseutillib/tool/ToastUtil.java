package com.bg.baseutillib.tool;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * 吐司工具类
 * <p>
 * 使用案例：
 * ToastUtil.showShortToast(MainActivity.this, "这是测试内容");
 *
 * @author Damon
 * @version 1.000 2017年9月7日16:42:20
 */
public class ToastUtil {

    private static Toast mToast;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 短时间吐司
     *
     * @param content 显示的内容
     */
    public static void showShortToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(content);
        }
        mToast.show();
    }

    /**
     * 长时间吐司
     *
     * @param content 显示的内容
     */
    public static void showLongToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, content, Toast.LENGTH_LONG);
        } else {
            mToast.setDuration(Toast.LENGTH_LONG);
            mToast.setText(content);
        }
        mToast.show();
    }

    /**
     * 关闭Toast显示
     */
    public static void closeToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
