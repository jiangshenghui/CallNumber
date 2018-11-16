package com.call.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.bg.baseutillib.BgApplication;

import java.lang.reflect.Field;

/**
 * Created by aicy on 2016/8/24.
 */
public class KeyBoardUtils {

    private Context context;
    // 软键盘的高度
    private int keyboardHeight;
    // 状态栏的高度
    private int statusBarHeight;
    // 软键盘的显示状态
    private boolean isShowKeyboard;
    //activity的整个布局
    private View viewlayout;
    private OnKeyBoardListener onKeyBoardListener;

    public KeyBoardUtils(){

    }

    public KeyBoardUtils(Context context, View viewlayout) {
        this.context = context;
        this.viewlayout = viewlayout;
        statusBarHeight = getStatusBarHeight(context);
        this.viewlayout.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    /**
     * 输入框点击事件
     * @param edit_tv
     * @param Container
     */
    public void setEditTouch(EditText edit_tv, final FrameLayout Container){
        edit_tv.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            int touch_flag = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touch_flag++;
                if (touch_flag == 2) {
                    //自己业务
                    if (Container.getVisibility() == View.VISIBLE) {
                        Container.setVisibility(View.GONE);
                    }
                    touch_flag = 0;
                }
                return false;
            }
        });
    }

    /**
     * 打开软键盘
     * @param mEditText 输入框
     */
    public static void openKeybord(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) BgApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     * @param mEditText 输入框
     */
    public static void closeKeybord(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager)  BgApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 获取软键盘的状态
     * 使用于dialog
     * @return
     */
    public boolean getKeybordStatus(){
        return isShowKeyboard;
    }

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            // 应用可以显示的区域。此处包括应用占用的区域，
            // 以及ActionBar和状态栏，但不含设备底部的虚拟按键。
            Rect r = new Rect();
            viewlayout.getWindowVisibleDisplayFrame(r);

            // 屏幕高度。这个高度不含虚拟按键的高度
            int screenHeight = viewlayout.getRootView().getHeight();

            int heightDiff = screenHeight - (r.bottom - r.top);

            // 在不显示软键盘时，heightDiff等于状态栏的高度
            // 在显示软键盘时，heightDiff会变大，等于软键盘加状态栏的高度。
            // 所以heightDiff大于状态栏高度时表示软键盘出现了，
            // 这时可算出软键盘的高度，即heightDiff减去状态栏的高度
            if (keyboardHeight == 0 && heightDiff > statusBarHeight) {
                keyboardHeight = heightDiff - statusBarHeight;
            }

            if (isShowKeyboard) {
                // 如果软键盘是弹出的状态，并且heightDiff小于等于状态栏高度，
                // 说明这时软键盘已经收起
                if (heightDiff <= statusBarHeight * 3) {
                    isShowKeyboard = false;
                    if (onKeyBoardListener != null)
                        onKeyBoardListener.OnKeyBoard(false);
                }
            } else {
                // 如果软键盘是收起的状态，并且heightDiff大于状态栏高度，
                // 说明这时软键盘已经弹出
                if (heightDiff > statusBarHeight * 3) {
                    isShowKeyboard = true;
                    if (onKeyBoardListener != null)
                        onKeyBoardListener.OnKeyBoard(true);
                }
            }
        }
    };

    public interface OnKeyBoardListener{
        void OnKeyBoard(boolean isShow);
    }

    public void setOnKeyBoardListener(OnKeyBoardListener onKeyBoardListener){
        this.onKeyBoardListener = onKeyBoardListener;
    }

    // 获取状态栏高度
    private int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return  context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
