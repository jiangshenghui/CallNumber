package com.bg.baseutillib.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bg.baseutillib.R;

/**
 * Created by pc on 2018/4/21.
 * 仿ios弹框
 */
public class BottomDialog extends Dialog {

    public BottomDialog(Context context, int theme) {
        super(context, theme);
        setDialog();
    }

    //重新设置属性
    private void setDialog() {
        Window dialogWindow = this.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialogEnterAndExitAnimation);
    }

    public static class Builder {
        private Context context;//上下文
        private String title;//标题
        private String[] contentList;//内容
        private OnClickListener positiveClickListener;//点击内容按钮监听
        private int cancelColor;//取消字体颜色
        private int[] contentListColor;//内容字体颜色

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置对话框标题
         *
         * @param title 标题字符串
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置对话框标题
         *
         * @param title 标题资源id
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * 设置对话框内容
         *
         * @param contentlist 内容字符串数组
         * @return
         */
        public Builder setContentList(String[] contentlist) {
            this.contentList = contentlist;
            return this;
        }

        /**
         * 设置对话框内容
         *
         * @param contentlist 内容资源id数组
         * @return
         */
        public Builder setContentList(int[] contentlist) {
            for (int i = 0; i < contentlist.length; i++) {
                this.contentList[i] = (String) context.getText(contentlist[i]);
            }
            return this;
        }

        /**
         * 内容资源字体颜色
         *
         * @param contentListColor
         * @return
         */
        public Builder setContentListColor(int[] contentListColor) {
            this.contentListColor = contentListColor;
            return this;
        }

        /**
         * 设置取消字体颜色
         *
         * @param cancelColor
         * @return
         */
        public Builder setCancelColor(int cancelColor) {
            this.cancelColor = cancelColor;
            return this;
        }

        /**
         * 设置"item"点击事件
         *
         * @return
         */
        public Builder setPositivePoitionItem(OnClickListener listener) {
            this.positiveClickListener = listener;
            return this;
        }

        /**
         * 创建对话框（不显示）
         *
         * @return
         */
        public BottomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final BottomDialog dialog = new BottomDialog(context, R.style.CustomDialog);// 使用自定义主题
            View layout = inflater.inflate(R.layout.dialog_bottom, null);

            if (title != null) {
                //设置标题
                TextView botTitle = layout.findViewById(R.id.botTitle);
                View botTitleLine = layout.findViewById(R.id.botTitleLine);
                botTitle.setText(title);
                botTitle.setVisibility(View.VISIBLE);
                botTitleLine.setVisibility(View.VISIBLE);
            }

            //设置内容
            if (contentList != null) {
                LinearLayout botAddItem = layout.findViewById(R.id.botAddItem);
                addItem(dialog, botAddItem);
            }

            //取消
            TextView botCancel = layout.findViewById(R.id.botCancel);
            //设置颜色
            if (cancelColor != 0) {
                botCancel.setTextColor(ContextCompat.getColor(context, cancelColor));
            }
            //关闭dialog
            botCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.setContentView(layout);
            return dialog;
        }

        /**
         * 动态添加item
         *
         * @param dialog
         * @param botAddItem 要添加的布局
         */
        private void addItem(final BottomDialog dialog, LinearLayout botAddItem) {
            for (int i = 0; i < contentList.length; i++) {
                //设置内容与横线的容器
                LinearLayout lay = new LinearLayout(context);
                LinearLayout.LayoutParams layLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lay.setLayoutParams(layLp);
                lay.setOrientation(LinearLayout.VERTICAL);

                //设置内容item，并设置文字
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) context.getResources().getDimension(R.dimen.y120));
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(ContextCompat.getColor(context, R.color.bottom_dialog_text_color));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.x42));
                textView.setText(contentList[i]);
                textView.setId(i);

                //自定义字体颜色
                if (contentListColor != null) {
                    textView.setTextColor(ContextCompat.getColor(context, contentListColor[i]));
                }

                lay.addView(textView);

                //设置横线
                if (i <= contentList.length - 2) {
                    View botLine = new View(context);
                    LinearLayout.LayoutParams lineLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) context.getResources().getDimension(R.dimen.y1));
                    botLine.setLayoutParams(lineLp);
                    botLine.setBackgroundColor(ContextCompat.getColor(context, R.color.dividerColor));
                    lay.addView(botLine);
                }

                botAddItem.addView(lay);

                //设置点击监听事件
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        positiveClickListener.onClick(dialog, view.getId());
                        dialog.dismiss();
                    }
                });
            }
        }
    }
}
