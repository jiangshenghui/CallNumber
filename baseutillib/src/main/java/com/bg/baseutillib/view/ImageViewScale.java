package com.bg.baseutillib.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * 点击先缩小再还原
 */
public class ImageViewScale extends ImageView {
    private boolean isClick;

    private ScaleAnimation animatorNormal, animatorSmaller;

    /**
     * 小于1的时候，先缩小再还原；
     * 大于1的时候，先放大再还原
     */
    private float scaleFactor = 0.85f;

    /**
     * 动画效果执行的时间
     */
    private long durationMillis = 300;

    public ImageViewScale(Context context) {
        super(context);
        init();
    }

    public ImageViewScale(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageViewScale(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        animatorNormal = new ScaleAnimation(scaleFactor, 1f, scaleFactor, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animatorNormal.setDuration(durationMillis);
        animatorNormal.setFillAfter(true);

        animatorSmaller = new ScaleAnimation(1f, scaleFactor, 1f, scaleFactor,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animatorSmaller.setDuration(durationMillis);
        animatorSmaller.setFillAfter(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isClick = true;
                startAnimation(animatorSmaller);
                break;
            case MotionEvent.ACTION_MOVE:
                isOutSide(event);
                break;
            case MotionEvent.ACTION_UP:
                if (isClick) {
                    isClick = false;
                    startAnimation(animatorNormal);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            performClick();
                        }
                    }, 300);
                }
                break;
        }
        return true;
    }

    private void isOutSide(MotionEvent event) {
        float viewX = event.getX();
        float viewY = event.getY();
        if (viewX < 0 || viewY < 0 || viewX > getWidth() || viewY > getHeight()) {
            if (isClick) {
                isClick = false;
                startAnimation(animatorNormal);
            }
        } else if (viewX > 0 || viewY > 0 || viewX < getWidth()
                || viewY < getHeight()) {
            if (!isClick) {
                isClick = true;
                startAnimation(animatorSmaller);
            }
        }
    }
}
