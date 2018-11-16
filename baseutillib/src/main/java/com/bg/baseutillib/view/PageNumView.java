package com.bg.baseutillib.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bg.baseutillib.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pc on 2017/10/21.
 * 自定义页码，实现网页的页码功能
 * 分3种情况来实现这个功能
 * 1、当按钮小于等于7个没有“...”出现
 * 2、当按钮等于8个有一个“...”出现
 * 3、当按钮大于等于9个有两个“...”出现
 * 最多展示9个按钮，目前是固定死的
 * -----后面需要多加几个按钮，需要改相应的代码
 */

public class PageNumView extends LinearLayout {

    private static final String TAG = "PageNumView";
    private Context mContext;
    /**
     * 默认是第一页
     */
    private int currentPage = 1;
    /**
     * 默认5条数据
     */
    private int pageNum = 5;
    /**
     * 记录当前页码数据
     */
    private List<String> stringList = new ArrayList<>();
    /**
     * 返回当前选中的数字的接口
     */
    private OnPageNumCurrent onPageNumCurrent;

    public PageNumView(Context context) {
        super(context);
        init(context, pageNum);
    }

    public PageNumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, pageNum);
    }

    public PageNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, pageNum);
    }

    public void init(Context context, int size) {
        pageNum = size == 0 ? 1 : size;
        mContext = context;
        setPageBottom(context); //默认显示第一页 ,设置页码布局
    }

    /**
     * 布局数据显示
     */
    private void setPageBottom(Context context) {

        this.removeAllViews();
        stringList.clear();

        int pageSize = pageNum + 2;
        //TODO: 如果数据大于等于 7 条，就会有两个“...”代替页码数字，否则是显示完整的数据
        if (pageSize >= 9) {
            //最多展示9个按钮
            for (int i = 0; i < 9; i++) {
                TextView sBtn = new TextView(context);
                sBtn.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams lp = new LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                //上一页的按钮绘制
                if (i == 0) {
                    sBtn.setTextColor(Color.WHITE);
                    sBtn.setBackgroundResource(R.drawable.page_tv_color);
                    lp.width = 90;
                    sBtn.setText("上一页");
                } else
                    //下一页的按钮绘制
                    if (i == 8) {
                        sBtn.setTextColor(Color.WHITE);
                        sBtn.setBackgroundResource(R.drawable.page_tv_color);
                        lp.width = 90;
                        sBtn.setText("下一页");
                    } else
                        //第一个按钮
                        if (i == 1) {
                            sBtn.setText("1");
                            setTextBg(context, sBtn, i, lp);
                        } else
                            //最后一个按钮
                            if (i == 7) {
                                sBtn.setText(pageNum + "");
                                setTextBg(context, sBtn, i, lp);
                            } else
                            //绘制中间的数据“...”
                            {
                                //当选中最后一个数字的时候，“...”在左边
                                if (currentPage == pageNum) {
                                    if (i == 2) {
                                        sBtn.setText("...");
                                        lp.width = LayoutParams.WRAP_CONTENT;
                                        sBtn.setBackgroundColor(Color.WHITE);
                                    } else {
                                        sBtn.setText(currentPage - 7 + i + "");
                                        setTextBg(context, sBtn, i,lp);
                                    }
                                } else
                                    //选中的数字在中间的时候有两个“...”
                                    if (currentPage > 5 && currentPage < pageNum - 4) {
                                        if (i == 2 || i == 6) {
                                            sBtn.setText("...");
                                            lp.width = LayoutParams.WRAP_CONTENT;
                                            sBtn.setBackgroundColor(Color.WHITE);
                                        } else {
                                            //中间固定写死
                                            if (i == 3) {
                                                sBtn.setText(currentPage - 1 + "");
                                            } else if (i == 4) {
                                                sBtn.setText(currentPage + "");
                                            } else if (i == 5) {
                                                sBtn.setText(currentPage + 1 + "");
                                            }
                                            setTextBg(context, sBtn, i, lp);
                                        }
                                    } else
                                        // 选中的数字在右边，只有左边有一个“...”
                                        if (currentPage > pageNum - 5 && currentPage < pageNum) {
                                            if (currentPage == pageNum - 4) {
                                                if (i == 2 || i == 6) {
                                                    sBtn.setText("...");
                                                    lp.width = LayoutParams.WRAP_CONTENT;
                                                    sBtn.setBackgroundColor(Color.WHITE);
                                                } else {
                                                    //中间固定写死
                                                    if (i == 3) {
                                                        sBtn.setText(currentPage - 1 + "");
                                                    } else if (i == 4) {
                                                        sBtn.setText(currentPage + "");
                                                    } else if (i == 5) {
                                                        sBtn.setText(currentPage + 1 + "");
                                                    }
                                                    setTextBg(context, sBtn, i, lp);
                                                }
                                            } else {
                                                if (i == 2) {
                                                    sBtn.setText("...");
                                                    lp.width = LayoutParams.WRAP_CONTENT;
                                                    sBtn.setBackgroundColor(Color.WHITE);
                                                } else {
                                                    //固定死的按钮
                                                    if (i == 3) {
                                                        sBtn.setText(pageNum - 4 + "");
                                                    } else if (i == 4) {
                                                        sBtn.setText(pageNum - 3 + "");
                                                    } else if (i == 5) {
                                                        sBtn.setText(pageNum - 2 + "");
                                                    } else if (i == 6) {
                                                        sBtn.setText(pageNum - 1 + "");
                                                    }
                                                    setTextBg(context, sBtn, i, lp);
                                                }
                                            }
                                        } else
                                        //选择的数字的id小于6只有右边一个“...”
                                        {
                                            if (i < 6) {
                                                if (currentPage == 5) {
                                                    if (i == 2 || i == 6) {
                                                        sBtn.setText("...");
                                                        lp.width = LayoutParams.WRAP_CONTENT;
                                                        sBtn.setBackgroundColor(Color.WHITE);
                                                    } else {
                                                        //中间固定写死
                                                        if (i == 3) {
                                                            sBtn.setText(currentPage - 1 + "");
                                                        } else if (i == 4) {
                                                            sBtn.setText(currentPage + "");
                                                        } else if (i == 5) {
                                                            sBtn.setText(currentPage + 1 + "");
                                                        }
                                                        setTextBg(context, sBtn, i, lp);
                                                    }
                                                } else {
                                                    sBtn.setText(i + "");
                                                    setTextBg(context, sBtn, i, lp);
                                                }
                                            } else if (i == 6) {
                                                sBtn.setText("...");
                                                lp.width = LayoutParams.WRAP_CONTENT;
                                                sBtn.setBackgroundColor(Color.WHITE);
                                            }
                                        }

                            }
                lp.height = 37;
                lp.leftMargin = 10;
                sBtn.setLayoutParams(lp);
                sBtn.setTextSize(18);
                sBtn.setId(i);
                stringList.add(sBtn.getText().toString());
                sBtn.setOnClickListener(textOnClick);
                this.addView(sBtn);
            }
        } else
            //TODO: 如果数据等于 6 条，就会有 1 个“...”代替页码数字，否则是显示完整的数据
            if (pageSize == 8) {
                //最多展示7个按钮
                for (int i = 0; i < 7; i++) {
                    TextView sBtn = new TextView(context);
                    sBtn.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams lp = new LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    //上一页的按钮绘制
                    if (i == 0) {
                        sBtn.setTextColor(Color.WHITE);
                        sBtn.setBackgroundResource(R.drawable.page_tv_color);
                        lp.width = 90;
                        sBtn.setText("上一页");
                    } else
                        //下一页的按钮绘制
                        if (i == 6) {
                            sBtn.setTextColor(Color.WHITE);
                            sBtn.setBackgroundResource(R.drawable.page_tv_color);
                            lp.width = 90;
                            sBtn.setText("下一页");
                        } else
                            //最后一个数字按钮
                            if (i == 5) {
                                sBtn.setText("6");
                                setTextBg(context, sBtn, 6, lp);
                            } else
                            //绘制中间的数据“...”
                            {
                                //选择的数字小于3只有右边一个“...”
                                if (currentPage < 4) {
                                    if (i == 4) {
                                        sBtn.setText("...");
                                        lp.width = LayoutParams.WRAP_CONTENT;
                                        sBtn.setBackgroundColor(Color.WHITE);
                                    } else {
                                        sBtn.setText(i + "");
                                        setTextBg(context, sBtn, i, lp);
                                    }
                                } else
                                //选择的数字大于3只有左边一个“...”
                                {
                                    if (i == 2) {
                                        sBtn.setText("...");
                                        lp.width = LayoutParams.WRAP_CONTENT;
                                        sBtn.setBackgroundColor(Color.WHITE);
                                    } else if (i == 3 || i == 4) {
                                        sBtn.setText(1 + i + "");
                                        setTextBg(context, sBtn, i, lp);
                                    } else {
                                        sBtn.setText(i + "");
                                        setTextBg(context, sBtn, i, lp);
                                    }
                                }
                            }
                    lp.height = 37;
                    lp.leftMargin = 10;
                    sBtn.setLayoutParams(lp);
                    sBtn.setTextSize(18);
                    sBtn.setId(i);
                    stringList.add(sBtn.getText().toString());
                    sBtn.setOnClickListener(textOnClick);
                    this.addView(sBtn);
                }
            } else
            //TODO: 如果数据小于等于 5 条，展示完整的数据
            {
                for (int i = 0; i < pageSize; i++) {
                    TextView sBtn = new TextView(context);
                    sBtn.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams lp = new LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    //上一页的按钮绘制
                    if (i == 0) {
                        sBtn.setTextColor(Color.WHITE);
                        sBtn.setBackgroundResource(R.drawable.page_tv_color);
                        lp.width = 90;
                        sBtn.setText("上一页");
                    } else
                        //下一页的按钮绘制
                        if (i == pageSize - 1) {
                            sBtn.setTextColor(Color.WHITE);
                            sBtn.setBackgroundResource(R.drawable.page_tv_color);
                            lp.width = 90;
                            sBtn.setText("下一页");
                        } else
                        //中间数据按钮的绘制
                        {
                            sBtn.setText(i + "");
                            setTextBg(context, sBtn, i, lp);
                        }
                    lp.height = 37;
                    lp.leftMargin = 10;
                    sBtn.setLayoutParams(lp);
                    sBtn.setTextSize(18);
                    sBtn.setId(i);
                    stringList.add(sBtn.getText().toString());
                    sBtn.setOnClickListener(textOnClick);
                    this.addView(sBtn);
                }
            }
    }

    /**
     * 公共的设置
     *
     * @param context
     * @param text
     * @param i
     * @param lp
     */
    private void setTextBg(Context context, TextView text, int i, LinearLayout.LayoutParams lp) {
        if (text.getText().toString().equals(String.valueOf(currentPage))) {
            //选择的字体颜色和背景设置
            text.setTextColor(Color.WHITE);
            text.setBackgroundResource(R.drawable.page_tv_color);
        } else {
            //未选择的字体颜色和背景设置
            text.setBackgroundResource(R.drawable.page_tv_color_delautf);
            text.setTextColor(ContextCompat.getColor(context, R.color.text_color_999));
        }
        //大于3位数,只考虑到3位数
        if (String.valueOf(i).length() >= 3) {
            lp.width = 45;
        } else {
            lp.width = 35;
        }
    }

    /**
     * 按钮点击事件
     */
    private OnClickListener textOnClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (stringList.get(view.getId()).equals("上一页")) {
                //上一页
                if (currentPage > 1) {
                    currentPage--;
                    setPageBottom(mContext);
                }
                if (onPageNumCurrent != null){
                    onPageNumCurrent.onCurrent(currentPage);
                }
            } else if (stringList.get(view.getId()).equals("下一页")) {
                //下一页
                if (currentPage < pageNum) {
                    currentPage++;
                    setPageBottom(mContext);
                }
                if (onPageNumCurrent != null){
                    onPageNumCurrent.onCurrent(currentPage);
                }
            } else if (pageNum >= 7) {
                if (stringList.get(view.getId()).equals(String.valueOf(pageNum))) {
                    //选中最后一个数字
                    currentPage = pageNum;
                    setPageBottom(mContext);
                    if (onPageNumCurrent != null){
                        onPageNumCurrent.onCurrent(currentPage);
                    }
                } else if (stringList.get(view.getId()).equals("1")) {
                    //选中第一个数字
                    currentPage = 1;
                    setPageBottom(mContext);
                    if (onPageNumCurrent != null){
                        onPageNumCurrent.onCurrent(currentPage);
                    }
                } else {
                    if (stringList.get(view.getId()).equals("...")) return;
                    //中间
                    if (currentPage != Integer.parseInt(stringList.get(view.getId()))) {
                        currentPage = Integer.parseInt(stringList.get(view.getId()));
                        setPageBottom(mContext);
                    }
                    if (onPageNumCurrent != null){
                        onPageNumCurrent.onCurrent(currentPage);
                    }
                }
            } else {
                if (stringList.get(view.getId()).equals("...")) return;
                //中间
                if (currentPage != Integer.parseInt(stringList.get(view.getId()))) {
                    currentPage = Integer.parseInt(stringList.get(view.getId()));
                    setPageBottom(mContext);
                }
                if (onPageNumCurrent != null){
                    onPageNumCurrent.onCurrent(currentPage);
                }
            }
        }
    };

    /**
     * 返回当前选中的数字的接口
     */
    public interface OnPageNumCurrent{
        void onCurrent(int current);
    }

    /**
     * 监听
     * 返回当前选中的数字的接口
     */
    public void setOnPageNumCurrent(OnPageNumCurrent onPageNumCurrent){
        this.onPageNumCurrent = onPageNumCurrent;
    }
}