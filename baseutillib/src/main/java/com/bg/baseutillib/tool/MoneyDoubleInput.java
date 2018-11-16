package com.bg.baseutillib.tool;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by pc on 2018/2/8.
 * EditText输入框
 * 必须设置 android:inputType="numberDecimal"
 * 金钱保留两位数，并不超过设置的最大金额
 */

public class MoneyDoubleInput {

    /**
     * @param editText 当前输入框
     * @param money    最大金额
     * @param isSetMax 是否设置最大限制金额
     */
    public static void MoneyDoubleInputToEditText(final EditText editText, final double money, final boolean isSetMax) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() <= 0) return;

                //第一位是小数点将不能输入
                if (editable.toString().trim().substring(0).equals(".")) {
                    editText.setText("");
                }

                //第一位是0将自动补加小数点
                if (editable.toString().startsWith("0")
                        && editable.toString().trim().length() > 1) {
                    if (!editable.toString().trim().substring(1, 2).equals(".")) {
                        editText.setText("0.");
                    }
                }

                //限制只能输入两位小数
                String temp = editable.toString();
                if (temp.indexOf(".") != -1) {
                    int posDot = temp.indexOf(".");
                    if (posDot <= 0) return;
                    if (temp.length() - posDot - 1 > 2) {
                        editable.delete(posDot + 3, posDot + 4);
                    }
                }

                if (isSetMax) {
                    //不能超过设置的总金额
                    if (Double.parseDouble(editable.toString()) > money) {
                        editText.setText(money + "");
                    }
                }

                editText.setSelection(editText.length());
            }
        });
    }
}
