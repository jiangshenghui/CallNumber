package com.bg.baseutillib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bg.baseutillib.R;


/**
 * 验证码控件
 */

public class VerificationCodeView extends TextView {
    private String sendVeriCode = "发送验证码";
    private Paint mNormalPaint;//画正常文字画笔
    private Paint mDisablePaint;//画倒计时画笔（不可点击）
    private int normalColor;//正常可点击文字颜色
    private int disableColor;//不可点击文字颜色
    private float textSize = 30;//字体大小
    private int defaultCountdownTime = 60;//默认倒计时，60s
    private int countdownTime = defaultCountdownTime;//倒计时
    private boolean isCountdown = false;//是否正在倒计时

    private final int WHAT_COUNTDOWN = 101;
    private final int updateTimePeriod = 1000;//更新时间周期，1s
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_COUNTDOWN:
                    invalidate();
                    mHandler.removeMessages(WHAT_COUNTDOWN);
                    mHandler.sendEmptyMessageDelayed(WHAT_COUNTDOWN, updateTimePeriod);//1秒更新一次
                    break;
            }
        }
    };

    public VerificationCodeView(Context context) {
        this(context, null);
    }

    public VerificationCodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerificationCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VerificationCodeView, defStyleAttr, 0);
        normalColor = typedArray.getColor(R.styleable.VerificationCodeView_vc_normalColor, Color.WHITE);
        disableColor = typedArray.getColor(R.styleable.VerificationCodeView_vc_disableColor, Color.WHITE);
        textSize = typedArray.getDimension(R.styleable.VerificationCodeView_vc_textSize, 30);
        if (typedArray.hasValue(R.styleable.VerificationCodeView_vc_text))
            sendVeriCode = typedArray.getString(R.styleable.VerificationCodeView_vc_text);


        init();
    }

    private void init() {
        mNormalPaint = new Paint();
        mNormalPaint.setTextSize(textSize);
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setColor(normalColor);

        mDisablePaint = new Paint();
        mDisablePaint.setTextSize(textSize);
        mDisablePaint.setAntiAlias(true);
        mDisablePaint.setColor(disableColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量控件大小
        setMeasuredDimension(getMeasureWH(widthMeasureSpec, 1),
                getMeasureWH(heightMeasureSpec, 2));
    }

    /**
     * 得到控件的尺寸
     *
     * @param widthOrHeight widthMeasureSpec或heightMeasureSpec
     * @param type          表示测量的是宽还是高，1表示宽，2表示高
     * @return
     */
    public int getMeasureWH(int widthOrHeight, int type) {
        int mode = MeasureSpec.getMode(widthOrHeight);
        int size = MeasureSpec.getSize(widthOrHeight);

        //根据模式计算大小
        switch (mode) {
            //精确值，在xml中设置控件大小为固定值，或match_parent
            case MeasureSpec.EXACTLY:
                return size;

            //限定在一个最大值，在xml中设置控件大小为wrap_content
            case MeasureSpec.AT_MOST:
                if (type == 1) {//宽
                    int width = (int) mNormalPaint.measureText(sendVeriCode) + getPaddingLeft() + getPaddingRight();
                    return width;

                } else if (type == 2) {//高
                    int heigth = (int) (mNormalPaint.descent() - mNormalPaint.ascent()) + getPaddingTop() + getPaddingBottom();
                    return heigth;
                }

                //想要多大就有多大，一般为ListView或ScrollView的子控件(很少使用)
            case MeasureSpec.UNSPECIFIED:
                return size;
        }
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isCountdown) {//正在倒计时
            String text = /*"" + countdownTime-- + "s" */ "重新获取(" + countdownTime-- + ")";
            float textWidth = mDisablePaint.measureText(text);
            float textHeight = mDisablePaint.descent() - mDisablePaint.ascent();
            canvas.drawText(text,
                    (getMeasuredWidth() - textWidth) / 2,
                    (getMeasuredHeight() - textHeight) / 2 - mDisablePaint.ascent(),
                    mDisablePaint);

            //倒计时完成
            if (countdownTime < 0) {
                isCountdown = false;
            }
            this.setEnabled(false);

            mHandler.sendEmptyMessageDelayed(WHAT_COUNTDOWN, updateTimePeriod);

        } else {
            float textWidth = mNormalPaint.measureText(sendVeriCode);
            float textHeight = mNormalPaint.descent() - mNormalPaint.ascent();
            canvas.drawText(sendVeriCode,
                    (getMeasuredWidth() - textWidth) / 2,
                    (getMeasuredHeight() - textHeight) / 2 - mNormalPaint.ascent(),
                    mNormalPaint);
            this.setEnabled(true);

            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 开始倒计时
     *
     * @param time 倒计时的时间
     */
    public void startCountdown(int time) {
        isCountdown = true;

        if (time > 0) {
            countdownTime = time;
        } else {
            countdownTime = defaultCountdownTime;
        }

        invalidate();
    }

    /**
     * 停止倒计时
     */
    public void stopCountdown() {
        isCountdown = false;
        invalidate();
        mHandler.removeCallbacksAndMessages(null);
    }
}
