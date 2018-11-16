package com.bg.baseutillib.tool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

/**
 * 设置TextView 的drawable属性
 */
public class TextViewDrawable {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * DrawableLeft属性
     *
     * @param textView   控件
     * @param drawableId 资源
     */
    public static void setDrawableLeft(TextView textView, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * DrawableTop属性
     *
     * @param textView   控件
     * @param drawableId 资源
     */
    public static void setDrawableTop(TextView textView, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * DrawableRight属性
     *
     * @param textView   控件
     * @param drawableId 资源
     */
    public static void setDrawableRight(TextView textView, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * DrawableBottom属性
     *
     * @param textView   控件
     * @param drawableId 资源
     */
    public static void setDrawableBottom(TextView textView, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, null, drawable);
    }
}
