package com.bg.baseutillib.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bg.baseutillib.R;


/**
 * 自定义警告对话框
 */

public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context context;//上下文
        private String title;//标题
        private String message;//内容
        private String positiveBtnText;//确定按钮文字
        private int positiveBtnTextColor;//确定按钮文字颜色
        private String negativeBtnText;//取消按钮文字
        private int negativeBtnTextColor;//取消按钮文字颜色
        private View contentView;
        private OnClickListener positiveButtonClickListener;//确定按钮监听
        private OnClickListener negativeButtonClickListener;//取消按钮监听
        private SpannableString contentSpannableString;//文字样式
        private boolean canCancel = true;//是否可以取消对话框
        private Float widthPercent;//对话框宽度占屏幕宽度的百分比

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置对话框的内容
         *
         * @param message 内容字符串
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 设置对话框的内容
         *
         * @param message 内容资源id
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
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
         * 设置自定义对话框使用的布局
         *
         * @param view
         * @return
         */
        public Builder setContentView(View view) {
            this.contentView = view;
            return this;
        }

        /**
         * 设置"确定"点击事件和文字
         *
         * @param positiveButtonText 文字字符串
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveBtnText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveBtnTextColor(int color) {
            this.positiveBtnTextColor = color;
            return this;
        }

        public Builder setNegativeBtnTextColor(int color) {
            this.negativeBtnTextColor = color;
            return this;
        }

        /**
         * 设置"确定"点击事件和文字
         *
         * @param positiveButtonText 文字资源id
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveBtnText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }


        /**
         * 设置"取消"点击事件和文字
         *
         * @param negativeButtonText 文字字符串
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeBtnText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 设置"取消"点击事件和文字
         *
         * @param negativeButtonText 文字资源id
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeBtnText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 设置Content的ForegroundColorSpan
         *
         * @param spannableString
         * @return
         */
        public Builder setContentForegroundColorSpan(SpannableString spannableString) {
            this.contentSpannableString = spannableString;
            return this;
        }

        /**
         * 是否可以取消对话框
         *
         * @param flag
         * @return
         */
        public Builder setCancelable(boolean flag) {
            canCancel = flag;
            return this;
        }

        /**
         * 设置对话框的宽度占屏幕宽度的比例
         *
         * @param widthPercent
         * @return
         */
        public Builder setWidthPercent(float widthPercent) {
            this.widthPercent = widthPercent;
            return this;
        }

        /**
         * 创建对话框（不显示）
         *
         * @return
         */
        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context, R.style.CustomDialog);// 使用自定义主题
            View layout = inflater.inflate(R.layout.custom_dialog_layout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            //对话框宽度占屏幕宽度的比例
            if (widthPercent != null) {
                int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = (int) (widthPixels * widthPercent);
                dialogWindow.setAttributes(lp);
            }

            dialog.setCancelable(canCancel);//是否可以取消对话框

            //设置标题
            if (title != null){
                ((TextView) layout.findViewById(R.id.tvTitle)).setText(title);
            } else {
                ((TextView) layout.findViewById(R.id.tvTitle)).setVisibility(View.GONE);
            }

            // 设置内容 
            if (message != null) {
                TextView tvMessage = (TextView) layout.findViewById(R.id.tvMessage);
                if (contentSpannableString != null) {
                    tvMessage.setText(contentSpannableString);
                } else {
                    tvMessage.setText(message);
                }

            } else if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.llContent)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.llContent)).addView(contentView,
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
            }

            // 设置确认按钮
            if (positiveBtnText != null) {
                TextView textView = ((TextView) layout.findViewById(R.id.tvConfrim));
                textView.setText(positiveBtnText);

                if (positiveButtonClickListener != null) {
                    textView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }

                //确定按钮文字颜色
                if (positiveBtnTextColor != 0) {
                    textView.setTextColor(positiveBtnTextColor);
                }
            } else {
                // 没有设置"确认"按钮文字，隐藏
                layout.findViewById(R.id.tvConfrim).setVisibility(
                        View.GONE);
            }

            // 设置"取消"按钮  
            if (negativeBtnText != null) {
                TextView textView = ((TextView) layout.findViewById(R.id.tvCancel));
                textView.setText(negativeBtnText);
                if (negativeButtonClickListener != null) {
                    textView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }

                //取消按钮文字颜色
                if (negativeBtnTextColor != 0) {
                    textView.setTextColor(negativeBtnTextColor);
                }
            } else {
                // 没有设置"取消"按钮文字，隐藏
                layout.findViewById(R.id.tvCancel).setVisibility(
                        View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }
    }
}


