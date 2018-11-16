package com.bg.baseutillib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bg.baseutillib.R;

/**
 * Created by pc on 2018/3/22.
 * 通用RelativeLayout, 适用于个人中心item类型布局
 */
public class BgRelativeLayout extends RelativeLayout {

    private RelativeLayout mRelativeLayout;
    private ImageView mLeftImage;
    private ImageView mRightImage;
    private TextView mRightTextView;
    private TextView mLeftTextView;
    private View mBottomDividerView;
    private View mTopDividerView;
    private EditText mEditText;
    private View mBgEffect;

    private String mRightString;//右边文字
    private int mRightColor = Color.WHITE; // 右边文字颜色
    private float mRightTextSize; // 右边文字大小
    private float mRightTextViewMarginRight;//右边文字_距离右边距离
    private float mRightTextViewMarginLeft;//右边文字_距离左边距离

    private String mLeftString;//左边文字
    private int mLeftColor = Color.WHITE; // 左边文字颜色
    private float mLeftTextSize; // 左边文字大小
    private float mLeftTextViewMarginLeft;//左边文字_距离左边距离

    private boolean isShowTopDivider;//是否显示分界线
    private boolean isShowBottomDivider;//是否显示分界线
    private int mRelativeBackground = Color.GREEN;//标题栏背景色
    private float mRelativeHeight;//标题栏高度

    private Drawable mLeftDrawable;//左边图片
    private float mLeftImageMarginLeft;//左边图片_距离左边距离
    private Drawable mRightDrawable;//右边图片
    private float mRightImageMarginRight;//右边图片_距离右边距离

    private boolean mEditEnabled;//设置Edit是否可以输入
    private String mEditHintString;//设置Edit的hint
    private int mEditHintColor = Color.WHITE;//设置Edit的hint颜色
    private int mEditTextColor = Color.WHITE; // Edit文字颜色
    private String mEditTextString;//设置Edit的String
    private float mEditTextSize; // Edit文字大小
    private float mEditPaddingRight;//设置Edit距离右边的padding
    private int mEditMaxLength;//设置Edit文字最大长度

    private float mBottomDividerMarginLeft;//底部分割线距离左边距离
    private float mBottomDividerMarginRight;//底部分割线距离右边距离

    public BgRelativeLayout(Context context) {
        this(context, null);
    }

    public BgRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BgRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater.from(context).inflate(R.layout.layout_bg_relative, this, true);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        mLeftImage = (ImageView) findViewById(R.id.relative_left_image);
        mRightImage = (ImageView) findViewById(R.id.relative_right_image);
        mRightTextView = (TextView) findViewById(R.id.relative_right_text);
        mLeftTextView = (TextView) findViewById(R.id.relative_left_text_view);
        mEditText = (EditText) findViewById(R.id.relative_right_edit_text);
        mBottomDividerView = findViewById(R.id.relative_bottom_divider);
        mTopDividerView = findViewById(R.id.relative_top_divider);
        mBgEffect = findViewById(R.id.relative_bg_effect);

        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BgRelativeLayout, defStyle, 0);

        //右边文字属性
        mRightString = typedArray.getString(R.styleable.BgRelativeLayout_rl_rightText);
        mRightColor = typedArray.getColor(R.styleable.BgRelativeLayout_rl_rightTextColor, mRightColor);
        mRightTextSize = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_rightTextSize, 0);
        mRightTextViewMarginRight = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_rightText_marginRight, 0);
        mRightTextViewMarginLeft = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_rightText_marginLeft, 0);

        //左边文字属性
        mLeftString = typedArray.getString(R.styleable.BgRelativeLayout_rl_leftText);
        mLeftColor = typedArray.getColor(R.styleable.BgRelativeLayout_rl_leftTextColor, mLeftColor);
        mLeftTextSize = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_leftTextSize, 0);
        mLeftTextViewMarginLeft = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_leftText_marginLeft, 0);

        //分界线
        isShowTopDivider = typedArray.getBoolean(R.styleable.BgRelativeLayout_rl_top_divider, false);
        mTopDividerView.setVisibility(isShowTopDivider ? VISIBLE : GONE);
        isShowBottomDivider = typedArray.getBoolean(R.styleable.BgRelativeLayout_rl_bottom_divider, false);
        mBottomDividerView.setVisibility(isShowBottomDivider ? VISIBLE : GONE);
        mBottomDividerMarginLeft = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_bottom_divider_marginLeft, 0);
        mBottomDividerMarginRight = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_bottom_divider_marginRight, 0);

        //RelativeLayout属性
        mRelativeBackground = typedArray.getColor(R.styleable.BgRelativeLayout_rl_background, mRelativeBackground);
        mRelativeHeight = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_height, 0);

        //EditText属性
        mEditHintString = typedArray.getString(R.styleable.BgRelativeLayout_rl_editHint);
        mEditHintColor = typedArray.getColor(R.styleable.BgRelativeLayout_rl_editHintColor, 0);
        mEditTextColor = typedArray.getColor(R.styleable.BgRelativeLayout_rl_editTextColor, 0);
        mEditTextString = typedArray.getString(R.styleable.BgRelativeLayout_rl_editText);
        mEditTextSize = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_editTextSize, 0);
        mEditPaddingRight = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_editPaddingRight, 0);
        mEditMaxLength = typedArray.getInteger(R.styleable.BgRelativeLayout_rl_editMaxLength, 100);
        mEditEnabled = typedArray.getBoolean(R.styleable.BgRelativeLayout_rl_isEditEnabled, false);

        //左边图片是否可见
        if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_leftImageDrawable)) {
            mLeftDrawable = typedArray.getDrawable(R.styleable.BgRelativeLayout_rl_leftImageDrawable);
            mLeftImage.setImageDrawable(mLeftDrawable);
            mLeftImage.setVisibility(VISIBLE);
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_leftImageDrawable_marginLeft)) {
                mLeftImageMarginLeft = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_leftImageDrawable_marginLeft, 0);
                setLeftImageMarginLeft(mLeftImageMarginLeft);
            }
        } else {
            mLeftImage.setVisibility(GONE);
        }

        //右边图片是否可见
        if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_rightImageDrawable)) {
            mRightDrawable = typedArray.getDrawable(R.styleable.BgRelativeLayout_rl_rightImageDrawable);
            mRightImage.setImageDrawable(mRightDrawable);
            mRightImage.setVisibility(VISIBLE);
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_rightImageDrawable_marginRight)) {
                mRightImageMarginRight = typedArray.getDimension(R.styleable.BgRelativeLayout_rl_rightImageDrawable_marginRight, 0);
                setRightImageMarginRight(mRightImageMarginRight);
            }
        } else {
            mRightImage.setVisibility(GONE);
        }

        //右边文字是否可见
        if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_rightText)) {
            mRightTextView.setText(mRightString);
            mRightTextView.setVisibility(VISIBLE);
            if (!typedArray.hasValue(R.styleable.BgRelativeLayout_rl_rightImageDrawable)) {
                MarginLayoutParams mp = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LayoutParams lp = new LayoutParams(mp);
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                mRightTextView.setLayoutParams(lp);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_rightTextColor)) {
                setRightTextColor(mRightColor);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_rightTextSize)) {
                setRightTextSize(mRightTextSize);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_rightText_marginRight)) {
                setRightTextViewMarginRight(mRightTextViewMarginRight);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_rightText_marginLeft)) {
                setmRightTextViewMarginLeft(mRightTextViewMarginLeft);
            }
        } else {
            mRightTextView.setVisibility(GONE);
        }

        //左边文字是否可见
        if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_leftText)) {
            mLeftTextView.setText(mLeftString);
            mLeftTextView.setVisibility(VISIBLE);
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_leftTextColor)) {
                setLeftTextColor(mLeftColor);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_leftTextSize)) {
                setLeftTextSize(mLeftTextSize);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_leftText_marginLeft)) {
                setLeftTextViewMarginLeft(mLeftTextViewMarginLeft);
            }
        } else {
            mLeftTextView.setVisibility(GONE);
        }

        //设置RelativeLayout
        if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_height)) {
            setRelativeLayoutHeight(mRelativeHeight);
        }
        if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_background)) {
            setRelativeLayoutBackground(mRelativeBackground);
        }

        //设置Edit的hint
        if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_editHint)
                || typedArray.hasValue(R.styleable.BgRelativeLayout_rl_editText)) {
            setEditTextHint(mEditHintString);
            mEditText.setVisibility(VISIBLE);
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_editPaddingRight)) {
                setEditTextPaddingRight(mEditPaddingRight);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_editTextColor)) {
                mEditText.setTextColor(mEditTextColor);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_editTextSize)) {
                mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mEditTextSize);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_editMaxLength)) {
                InputFilter[] filters = {new InputFilter.LengthFilter(mEditMaxLength)};
                mEditText.setFilters(filters);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_editHintColor)) {
                mEditText.setHintTextColor(mEditHintColor);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_editText)) {
                mEditText.setText(mEditTextString);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_isEditEnabled)){
                mEditText.setEnabled(mEditEnabled);
            }
        } else {
            mEditText.setVisibility(GONE);
        }

        //设置底部分割线距离左右两边的距离
        if (isShowBottomDivider) {
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_bottom_divider_marginLeft)) {
                setBottomDividerMarginLeft(mBottomDividerMarginLeft);
            }
            if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_bottom_divider_marginRight)) {
                setBottomDividerMarginRight(mBottomDividerMarginRight);
            }
        }

        //是否显示背景效果
        if (typedArray.hasValue(R.styleable.BgRelativeLayout_rl_isShowBgEffect)) {
            mBgEffect.setVisibility(typedArray.getBoolean(R.styleable.BgRelativeLayout_rl_isShowBgEffect, false)
                    ? VISIBLE : GONE);
        }

        typedArray.recycle();
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
     * 获取左边TextView
     */
    public TextView getLeftText() {
        if (mLeftTextView != null) {
            return mLeftTextView;
        }
        return null;
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
     * 左边文字_距离左边距离
     *
     * @param left
     */
    public void setLeftTextViewMarginLeft(float left) {
        if (mLeftTextView != null) {
            ((LayoutParams) mLeftTextView.getLayoutParams()).leftMargin = (int) left;
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
     * 获取右边TextView
     */
    public TextView getRightText() {
        if (mRightTextView != null) {
            return mRightTextView;
        }
        return null;
    }

    public ImageView getRightImg() {
        if (mRightImage != null) {
            return mRightImage;
        }
        return null;
    }

    public ImageView getLeftImg() {
        if (mLeftImage != null) {
            return mLeftImage;
        }
        return null;
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
     * 右边文字_距离右边距离
     *
     * @param right
     */
    public void setRightTextViewMarginRight(float right) {
        if (mRightTextView != null) {
            ((LayoutParams) mRightTextView.getLayoutParams()).rightMargin = (int) right;
        }
    }

    /**
     * 右边文字_距离左边距离
     *
     * @param left
     */
    public void setmRightTextViewMarginLeft(float left) {
        if (mRightTextView != null) {
            ((LayoutParams) mRightTextView.getLayoutParams()).leftMargin = (int) left;
        }
    }

    /**
     * 设置RelativeLayout背景色
     *
     * @param color
     */
    public void setRelativeLayoutBackground(int color) {
        if (mRelativeLayout != null) {
            mRelativeLayout.setBackgroundColor(color);
        }
    }

    /**
     * 设置RelativeLayout高度
     *
     * @param height
     */
    public void setRelativeLayoutHeight(float height) {
        if (mRelativeLayout != null) {
            (mRelativeLayout.getLayoutParams()).height = (int) height;
        }
    }

    /**
     * 设置监听事件
     *
     * @param listener
     */
    public void setRelativeLayoutClickListener(View.OnClickListener listener) {
        if (mRelativeLayout != null) {
            mRelativeLayout.setOnClickListener(listener);
        }
    }

    /**
     * 获取Edit内容
     *
     * @return
     */
    public String getEditTextContent() {
        if (mEditText != null) {
            return mEditText.getText().toString().trim();
        }
        return "";
    }

    /**
     * 获取Edit
     *
     * @return
     */
    public EditText getEditText() {
        if (mEditText != null) {
            return mEditText;
        }
        return null;
    }

    /**
     * 设置Edit的hint
     *
     * @param text
     */
    public void setEditTextHint(String text) {
        if (mEditText != null) {
            mEditText.setHint(text);
        }
    }

    /**
     * 设置Edit距离右边的padding
     *
     * @param right
     */
    public void setEditTextPaddingRight(float right) {
        if (mEditText != null) {
            mEditText.setPadding(0, 0, (int) right, 0);
        }
    }

    /**
     * 设置Edit的style属性
     * <p>
     * InputType.
     *
     * @param type
     */
    public void setEditTextStyle(int type) {
        if (mEditText != null) {
            mEditText.setInputType(type);
        }
    }

    /**
     * 设置底部分界线距离左边的距离
     *
     * @param left
     */
    public void setBottomDividerMarginLeft(float left) {
        if (mBottomDividerView != null) {
            ((LayoutParams) mBottomDividerView.getLayoutParams()).leftMargin = (int) left;
        }
    }

    /**
     * 设置底部分界线距离右边的距离
     *
     * @param right
     */
    public void setBottomDividerMarginRight(float right) {
        if (mBottomDividerView != null) {
            ((LayoutParams) mBottomDividerView.getLayoutParams()).rightMargin = (int) right;
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
     * 右边图片_距离右边距离
     *
     * @param right
     */
    public void setRightImageMarginRight(float right) {
        if (mRightImage != null) {
            ((LayoutParams) mRightImage.getLayoutParams()).rightMargin = (int) right;
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

    /**
     * 左边图片_距离左边距离
     *
     * @param left
     */
    public void setLeftImageMarginLeft(float left) {
        if (mLeftImage != null) {
            ((LayoutParams) mLeftImage.getLayoutParams()).leftMargin = (int) left;
        }
    }
}
