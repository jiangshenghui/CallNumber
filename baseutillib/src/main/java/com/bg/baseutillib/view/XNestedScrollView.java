package com.bg.baseutillib.view;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

public class XNestedScrollView extends NestedScrollView {

    public XNestedScrollView(@NonNull Context context) {
        this(context, null);
    }

    public XNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOverScrollMode(OVER_SCROLL_NEVER);
        setFillViewport(true);
        setFocusableInTouchMode(true);
    }
}
