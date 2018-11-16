package com.bg.baseutillib.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import com.bg.baseutillib.tool.ScreenUtils;

/**
 * 滑动时标题变色view
 * Created by pc on 2018/3/19.
 */

public class ScrollChangeScrollView extends NestedScrollView {
    private View mByWhichView;
    private View mTitleView;
    private boolean shouldSlowlyChange = true;
    private Context context;
    //要改变成的颜色
    private int toChangeColor = Color.parseColor("#F36646");

    public OnScrollListener mListener;

    public interface OnScrollListener {
        void onScroll(int scrollX, int scrollY);
    }

    public ScrollChangeScrollView(Context context) {
        this(context, null);
    }

    public ScrollChangeScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollChangeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusableInTouchMode(true);
        this.context = context;
    }

    @Override
    public void scrollTo(int x, int y) {
        //这是为了修复noScrllListView嵌套在srcollview时就自动滑动到noscrolllistview的顶部的bug，不影响使用
        if (x == 0 && y == 0 || y <= 0) {
            super.scrollTo(x, y);
        }
    }

    public void setListener(OnScrollListener listener) {
        this.mListener = listener;
    }

    public void setShouldSlowlyChange(boolean slowlyChange) {
        this.shouldSlowlyChange = slowlyChange;
    }

    /**
     * 设置透明度渐变的标题view
     *
     * @param view
     */
    public void setupTitleView(View view) {
        this.mTitleView = view;
    }

    /**
     * 跟随的view
     *
     * @param view
     */
    public void setupByWhichView(View view) {
        mByWhichView = view;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if (scrollY >= mByWhichView.getTop() + mByWhichView.getMeasuredHeight() - 2 * ScreenUtils.getStatusHeight(context)) {
            mTitleView.setBackgroundColor(toChangeColor);
        } else if (scrollY >= 0) {
            if (!shouldSlowlyChange) {
                mTitleView.setBackgroundColor(Color.TRANSPARENT);
            } else {
                float persent = scrollY * 1f / (mByWhichView.getTop() + mByWhichView.getMeasuredHeight()
                        - 2 * ScreenUtils.getStatusHeight(context));
                int alpha = (int) (255 * persent);
                int color = Color.argb(alpha, 243, 102, 70);
                mTitleView.setBackgroundColor(color);
            }
        }

        if (mListener != null) {
            mListener.onScroll(scrollX, scrollY);
        }
    }
}
