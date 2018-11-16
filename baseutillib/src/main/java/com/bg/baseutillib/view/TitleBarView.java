package com.bg.baseutillib.view;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bg.baseutillib.R;
import com.bg.baseutillib.tool.ScreenUtils;

/**
 * 自定义标题控件
 */
public class TitleBarView extends RelativeLayout {

    private ImageView mLeftImage;
    private ImageView mRightImage;
    private ImageView mRightImage2;
    private TextView mCenterTextView;
    private TextView mRightTextView;
    private TextView mLeftTextView;
    private RelativeLayout mTitleBar;
    private View mDividerView;

    private String mCenterString; // 标题文字
    private int mCenterColor = Color.WHITE; // 标题颜色
    private float mCenterTextSize; // 标题文字大小

    private String mRightString;//右边文字
    private int mRightColor = Color.WHITE; // 右边文字颜色
    private float mRightTextSize; // 右边文字大小

    private String mLeftString;//左边文字
    private int mLeftColor = Color.WHITE; // 左边文字颜色
    private float mLeftTextSize; // 左边文字大小

    private boolean isShowDivider;//是否显示分界线
    private int mTitleBarBackground = Color.GREEN;//标题栏背景色
    private float mTitleBarHeight;//标题栏高度

    private Drawable mLeftDrawable;//左边图片
    private Drawable mRightDrawable;//右边图片
    private Drawable mRightDrawable2;//右边图片2

    public TitleBarView(Context context) {
        this(context, null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater.from(context).inflate(R.layout.title_bar_layout, this, true);
        mTitleBar = (RelativeLayout) findViewById(R.id.rl_title_bar_layout);
        mLeftImage = (ImageView) findViewById(R.id.title_bar_left_image);
        mRightImage = (ImageView) findViewById(R.id.title_bar_right_image);
        mRightImage2 = (ImageView) findViewById(R.id.title_bar_right_image2);
        mLeftTextView = (TextView) findViewById(R.id.title_bar_left_text_view);
        mRightTextView = (TextView) findViewById(R.id.title_bar_right_text_view);
        mCenterTextView = (TextView) findViewById(R.id.title_bar_center_text);
        mDividerView = findViewById(R.id.title_bar_divider);

        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBarView, defStyle, 0);

        // 标题文字属性
        mCenterString = typedArray.getString(R.styleable.TitleBarView_tb_centerText);
        mCenterColor = typedArray.getColor(R.styleable.TitleBarView_tb_centerTextColor, mCenterColor);
        mCenterTextSize = typedArray.getDimension(R.styleable.TitleBarView_tb_centerTextSize, 0);

        //右边文字属性
        mRightString = typedArray.getString(R.styleable.TitleBarView_tb_rightText);
        mRightColor = typedArray.getColor(R.styleable.TitleBarView_tb_rightTextColor, mRightColor);
        mRightTextSize = typedArray.getDimension(R.styleable.TitleBarView_tb_rightTextSize, 0);

        //左边文字属性
        mLeftString = typedArray.getString(R.styleable.TitleBarView_tb_leftText);
        mLeftColor = typedArray.getColor(R.styleable.TitleBarView_tb_leftTextColor, mLeftColor);
        mLeftTextSize = typedArray.getDimension(R.styleable.TitleBarView_tb_leftTextSize, 0);

        //分界线
        isShowDivider = typedArray.getBoolean(R.styleable.TitleBarView_tb_divider, false);
        mDividerView.setVisibility(isShowDivider ? VISIBLE : GONE);

        //TitleBar属性
        mTitleBarBackground = typedArray.getColor(R.styleable.TitleBarView_tb_titleBarBackground, mTitleBarBackground);
        mTitleBarHeight = typedArray.getDimension(R.styleable.TitleBarView_tb_titleBarHeight, 0);

        //左边图片是否可见
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_leftImageDrawable)) {
            mLeftDrawable = typedArray.getDrawable(R.styleable.TitleBarView_tb_leftImageDrawable);
            mLeftImage.setImageDrawable(mLeftDrawable);
            mLeftImage.setVisibility(VISIBLE);
            mLeftImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) getContext()).finish();
                }
            });

        } else {
            mLeftImage.setVisibility(GONE);
        }

        //右边图片是否可见
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightImageDrawable)) {
            mRightDrawable = typedArray.getDrawable(R.styleable.TitleBarView_tb_rightImageDrawable);
            mRightImage.setImageDrawable(mRightDrawable);
            mRightImage.setVisibility(VISIBLE);
        } else {
            mRightImage.setVisibility(GONE);
        }

        //右边图片是否可见2
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightImageDrawable2)) {
            mRightDrawable2 = typedArray.getDrawable(R.styleable.TitleBarView_tb_rightImageDrawable2);
            mRightImage2.setImageDrawable(mRightDrawable2);
            mRightImage2.setVisibility(VISIBLE);
        } else {
            mRightImage2.setVisibility(GONE);
        }

        //右边图片距离右边距离
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightImage2_marginRight)){
            setRightImage2MarginRight(typedArray.getDimension(R.styleable.TitleBarView_tb_rightImage2_marginRight, 0));
        }

        //右边文字是否可见
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightText)) {
            mRightTextView.setText(mRightString);
            mRightTextView.setVisibility(VISIBLE);
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightTextColor)) {
                setRightTextColor(mRightColor);
            }
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightTextSize)) {
                setRightTextSize(mRightTextSize);
            }
        } else {
            mRightTextView.setVisibility(GONE);
        }

        //左边文字是否可见
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_leftText)) {
            mLeftTextView.setText(mLeftString);
            mLeftTextView.setVisibility(VISIBLE);
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_leftTextColor)) {
                setLeftTextColor(mLeftColor);
            }
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_leftTextSize)) {
                setLeftTextSize(mLeftTextSize);
            }
        } else {
            mLeftTextView.setVisibility(GONE);
        }

        //设置标题文字
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_centerText)) {
            setCenterText(mCenterString);
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_centerTextColor)) {
                setCenterColor(mCenterColor);
            }
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_centerTextSize)) {
                setCenterTextSize(mCenterTextSize);
            }
        }

        //设置TitleBar
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_titleBarHeight)) {
            setTitleBarHeight(mTitleBarHeight);
        }
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_titleBarBackground)) {
            setTitleBarBackground(mTitleBarBackground);
        }

        typedArray.recycle();
    }

    /**
     * 左边图片点击事件
     *
     * @param listener
     */
    public void setLeftImageClickListener(View.OnClickListener listener) {
        if (mLeftImage != null) {
            mLeftImage.setOnClickListener(listener);
        }
    }

    /**
     * 右边图片点击事件
     *
     * @param listener
     */
    public void setRightImageClickListener(View.OnClickListener listener) {
        if (mRightImage != null) {
            mRightImage.setOnClickListener(listener);
        }
    }

    /**
     * 右边图片点击事件2
     *
     * @param listener
     */
    public void setRightImage2ClickListener(View.OnClickListener listener) {
        if (mRightImage2 != null) {
            mRightImage2.setOnClickListener(listener);
        }
    }

    /**
     * 右边图片2_距离右边距离
     *
     * @param right
     */
    public void setRightImage2MarginRight(float right) {
        if (mRightImage2 != null) {
            ((LayoutParams) mRightImage2.getLayoutParams()).rightMargin = (int) right;
        }
    }

    /**
     * 右边文件点击事件
     *
     * @param listener
     */
    public void setRightTextClickListener(View.OnClickListener listener) {
        if (mRightTextView != null) {
            mRightTextView.setOnClickListener(listener);
        }
    }

    /**
     * 左边文字点击事件
     *
     * @param listener
     */
    public void setLeftTextClickListener(View.OnClickListener listener) {
        if (mLeftTextView != null) {
            mLeftTextView.setOnClickListener(listener);
        }
    }

    /**
     * 设置左边文字是否可见
     *
     * @param visibility
     */
    public void setLeftTextVisibility(int visibility) {
        if (mLeftTextView != null) {
            mLeftTextView.setVisibility(visibility);
        }
    }

    /**
     * 设置左边文字
     *
     * @param text
     */
    public void setLeftText(String text) {
        if (mLeftTextView != null) {
            mLeftTextView.setText(text);
        }
    }

    /**
     * 设置左边文字颜色
     *
     * @param color
     */
    public void setLeftTextColor(int color) {
        if (mLeftTextView != null) {
            mLeftTextView.setTextColor(color);
        }
    }

    /**
     * 设置左边文字大小
     *
     * @param size
     */
    public void setLeftTextSize(float size) {
        if (mLeftTextView != null) {
            mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    /**
     * 设置右边文字
     *
     * @param text
     */
    public void setRightText(String text) {
        if (mRightTextView != null) {
            mRightTextView.setText(text);
        }
    }

    /**
     * 设置右边文字颜色
     *
     * @param color
     */
    public void setRightTextColor(int color) {
        if (mRightTextView != null) {
            mRightTextView.setTextColor(color);
        }
    }

    /**
     * 设置右边文字大小
     *
     * @param size
     */
    public void setRightTextSize(float size) {
        if (mRightTextView != null) {
            mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    /**
     * 设置右边文字是否可见
     *
     * @param visibility
     */
    public void setRightTextVisibility(int visibility) {
        if (mRightTextView != null) {
            mRightTextView.setVisibility(visibility);
        }
    }

    /**
     * 设置右边的图片是否可见
     *
     * @param visibility
     */
    public void setRightImageVisibility(int visibility) {
        if (mRightImage != null) {
            mRightImage.setVisibility(visibility);
        }
    }

    /**
     * 设置右边的图片是否可见2
     *
     * @param visibility
     */
    public void setRightImage2Visibility(int visibility) {
        if (mRightImage2 != null) {
            mRightImage2.setVisibility(visibility);
        }
    }

    /**
     * 设置右边图片
     *
     * @param resource
     */
    public void setRightImageResource(int resource) {
        if (mRightImage != null) {
            mRightImage.setImageResource(resource);
        }
    }

    /**
     * 设置右边图片2
     *
     * @param resource
     */
    public void setRightImage2Resource(int resource) {
        if (mRightImage2 != null) {
            mRightImage2.setImageResource(resource);
        }
    }

    /**
     * 设置左边的图片是否可见
     *
     * @param visibility
     */
    public void setLeftImageVisibility(int visibility) {
        if (mLeftImage != null) {
            mLeftImage.setVisibility(visibility);
        }
    }

    /**
     * 设置左边图片
     *
     * @param resource
     */
    public void setLeftImageResource(int resource) {
        if (mLeftImage != null) {
            mLeftImage.setImageResource(resource);
        }
    }

    public ImageView getmRightImage() {
        return mRightImage;
    }

    /**
     * 设置中间的标题文字
     *
     * @param text
     */
    public void setCenterText(String text) {
        if (mCenterTextView != null) {
            mCenterTextView.setText(text);
        }
    }

    /**
     * 设置中间的标题文字颜色
     *
     * @param color
     */
    public void setCenterColor(int color) {
        if (mCenterTextView != null) {
            mCenterTextView.setTextColor(color);
        }
    }

    /**
     * 设置中间的标题文字大小
     *
     * @param size
     */
    public void setCenterTextSize(float size) {
        if (mCenterTextView != null) {
            mCenterTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    /**
     * 设置中间的标题文字是否可见
     *
     * @param visibility
     */
    public void setCenterTextVisibility(int visibility) {
        if (mCenterTextView != null) {
            mCenterTextView.setVisibility(visibility);
        }
    }

    /**
     * 设置分割线是否可见
     *
     * @param visibility
     */
    public void setDividerVisibility(int visibility) {
        if (mDividerView != null) {
            mDividerView.setVisibility(visibility);
        }
    }

    /**
     * 设置TitleBar背景色
     *
     * @param color
     */
    public void setTitleBarBackground(int color) {
        if (mTitleBar != null) {
            mTitleBar.setBackgroundColor(color);
        }
    }

    /**
     * 设置TitleBar高度
     *
     * @param height
     */
    public void setTitleBarHeight(float height) {
        if (mTitleBar != null) {
            (mTitleBar.getLayoutParams()).height = (int) height;
        }
    }
}
