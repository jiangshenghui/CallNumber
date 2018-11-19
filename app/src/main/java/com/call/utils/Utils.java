/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.call.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.call.ISaftyDialog;
import com.call.OnCusDialogInterface;
import com.call.R;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Class containing some static utility methods.
 */
public class Utils {
	public static final int IO_BUFFER_SIZE = 8 * 1024;

	public final static int NUM_LENGTH = 5;// 金额长度

	private Utils() {

	};

	public static String calculator(String strValue, EditText tvPayMoney) {
		boolean isAppend = false;
		if (StringUtils.isNotEmpty(strValue)) {
			String subStrValue = "";
			if (strValue.contains(".")) {
				int index = strValue.indexOf(".");
				subStrValue = strValue.substring(0, index);
				if (strValue.substring(index + 1, strValue.length()).length() < 2) {
					isAppend = true;
				} else {
					tvPayMoney
							.setText(strValue.contains(".") ? getSpannableString(strValue)
									: strValue);
					return strValue;
				}
			} else {
				subStrValue = strValue;
			}
			if (subStrValue.length() < NUM_LENGTH || isAppend) {
			} else {
				tvPayMoney.setText(strValue.contains(".") ? getSpannableString(strValue)
								: strValue);
				return strValue;
			}
		}
		tvPayMoney.setText(strValue.contains(".") ? getSpannableString(strValue)
				: strValue);
		return strValue;
	}
	public static Dialog setDialog(Activity activity, int layout, double width,
                                   double height) {
		View view = LayoutInflater.from(activity).inflate(layout, null);
		Dialog dialog = new Dialog(activity, R.style.custom_dialog);
		dialog.setCancelable(true);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view);
		WindowManager m = activity.getWindowManager();
		Window dialogWindow = dialog.getWindow();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		Display display = m.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		if (width > 0) {
			p.width = (int) (d.getWidth() * width); // 宽度设置为屏幕的0.65
		}
		if (height > 0) {
			p.height = (int) (d.getHeight() * height); // 高度设置为屏幕的0.65
		}
		dialogWindow.setAttributes(p);
		return dialog;
	}
	/**
	 * @Description: 像素值转化为DP值
	 * @param context
	 * @param value
	 * @return
	 * @return int
	 */
	public static int getDPValue(Context context, float value) {
		int dpValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context
				.getResources().getDisplayMetrics());
		return dpValue;
	}
	public static String setNumber(String strValue, TextView tvPayMoney, String number) {
		boolean isAppend = false;
		if (StringUtils.isNotEmpty(strValue)) {
			String subStrValue = "";
			if (strValue.contains(".")) {
				int index = strValue.indexOf(".");
				subStrValue = strValue.substring(0, index);
				if (strValue.substring(index + 1, strValue.length()).length() < 2) {
					isAppend = true;
				} else {
					tvPayMoney.setText(strValue.contains(".") ? getSpannableString(strValue)
									: strValue);
					return strValue;
				}
			} else {
				subStrValue = strValue;
			}
			if (subStrValue.length() < NUM_LENGTH || isAppend) {
			} else {
				tvPayMoney.setText(strValue.contains(".") ? getSpannableString(strValue)
								: strValue);
				return strValue;
			}
		}
		if ("0".equals(strValue)) {
			strValue = number;
		} else {
			if ("0.0".equals(strValue) && "0".equals(number)) {
				return strValue;
			}
			strValue += number;
		}
		tvPayMoney.setText(strValue);
		return strValue;
	}

	/***
	 * 设置小数点后字体
	 *
	 * @return
	 */
	public static SpannableString getSpannableString(String strValue) {
		SpannableString spanString = new SpannableString(strValue);
		AbsoluteSizeSpan span = new AbsoluteSizeSpan(66);
		spanString.setSpan(span, strValue.indexOf(".") + 1, strValue.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spanString;
	}

	/***
	 * 添加小数点
	 * 
	 * @param string
	 * @return
	 */
	public static String addPoint(String string) {
		int p = string.length() - 1;
		boolean flag = true;
		Character tmp = string.charAt(p);
		if (0 == p)
			string += ".";
		if (Character.isDigit(tmp) && 0 != p) {
			while (flag) {
				if (!Character.isDigit(tmp)) {
					flag = false;
					if (tmp != '.')
						string += ".";
				}
				if (0 == --p && (tmp != '.')) {
					string += ".";
					break;
				}
				tmp = string.charAt(p);
			}
		}
		return string;
	}

	/***
	 * 添加小数点
	 *
	 * @return
	 */
	public static String deleteNumber(String strNumber) {
		if (!"0".equals(strNumber)) {
			strNumber = strNumber.substring(0, strNumber.length() - 1);
			if (0 == strNumber.length())
				strNumber = "0";
		}
		return strNumber;
	}

	/***
	 * 设置精确到小数点后几位
	 *
	 * @return
	 */
	public static double round(Double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal b = null == v ? new BigDecimal("0.0") : new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String FormatPoint(Double v){
		return String.valueOf(round(v, 1));
	}

	public static String subtract(String fristMoney, String lastMoney) {
		DecimalFormat df = new DecimalFormat("#0.00");
		if (StringUtils.isNotEmpty(fristMoney)
				&& StringUtils.isNotEmpty(lastMoney)) {
			BigDecimal frist = new BigDecimal(fristMoney);
			BigDecimal last = new BigDecimal(lastMoney);
			return df.format(frist.subtract(last)).toString();
		}
		return "0.00";
	}



	private static Integer _screenWidth;
	private static Integer _screenHeight;

	/**
	 * @Description: 得到设备高度
	 * @param context
	 * @return
	 * @return float
	 */
	public static int getScreenHeight(Activity context) {
		if (_screenHeight == null) {
			DisplayMetrics metric = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(metric);
			_screenHeight = metric.heightPixels - getStatusBarHeight(context);
		}
		return _screenHeight;
	}

	/**
	 * @Description: 获取Status Bar高度
	 * @param context
	 * @return
	 * @return int
	 */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 设置光标位置
	 * @param et
	 */
	public static void setEditTextFocus(EditText et) {
		et.setFocusable(true);
		et.setFocusableInTouchMode(true);
		et.requestFocus();
		et.findFocus();
		et.setSelection(et.getText().toString().trim().length());
	}
	/**
	 * @Description: TODO
	 * @param context
	 * @return
	 * @return Dialog
	 */
	public static Dialog showProgressDialog(Context context) {
//        Dialog dialog = new Dialog(context, R.style.dialog);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.ui_progress_dialog);
//        dialog.show();
//
		return showProgressWithDelayHide(context,30000);
	}
	/**
	 * @Description: TODO
	 * @param context
	 * @return
	 * @return Dialog
	 */
	public static Dialog comitProgressDialog(Context context) {
//        Dialog dialog = new Dialog(context, R.style.dialog);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.ui_progress_dialog);
//        dialog.show();
//
		return comitProgressWithDelayHide(context,30000);
	}
	/**
	 * @Description:
	 * @param context
	 * @return
	 * @return Dialog
	 */
	public static Dialog showProgressWithDelayHide(Context context, long delay) {
		final Dialog dialog = new Dialog(context, R.style.dialog);
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.ui_progress_dialog);
		dialog.show();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				if (dialog != null) {
					dialog.dismiss();
				}
			}
		}, delay);
		return dialog;
	}
	/**
	 * @Description:
	 * @param context
	 * @return
	 * @return Dialog
	 */
	public static Dialog comitProgressWithDelayHide(Context context, long delay) {
		final Dialog dialog = new Dialog(context, R.style.BottomDialog);
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.ui_progress_commit_dialog);
		dialog.show();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				if (dialog != null) {
					dialog.dismiss();
				}
			}
		}, delay);
		return dialog;
	}
	/**
	 * @Description: TODO
	 * @param context
	 * @param title
	 * @param msg
	 * @param dialogInterface
	 * @return void
	 */
	public static void showDialog(final Context context, String title, String msg, String confirm,
                                  String cancel, final OnCusDialogInterface dialogInterface) {
		try {
			if(context != null && context instanceof ISaftyDialog) {
				ISaftyDialog activity = (ISaftyDialog)context;
				if(activity.verifyDialogFlag()) {
					return;
				}
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			if (title != null) {
				builder.setTitle(title);
			}
			builder.setCancelable(false);
			builder.setMessage(msg);
			if (confirm != null) {
				builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(context != null && context instanceof ISaftyDialog) {
							ISaftyDialog activity = (ISaftyDialog)context;
							activity.hideDialogFlag();
						}

						dialog.dismiss();
						if (dialogInterface != null) {
							dialogInterface.onConfirmClick();
						}
					}
				});
			}
			if (cancel != null) {
				builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(context != null && context instanceof ISaftyDialog) {
							ISaftyDialog activity = (ISaftyDialog)context;
							activity.hideDialogFlag();
						}

						dialog.dismiss();
						if (dialogInterface != null) {
							dialogInterface.onCancelClick();
						}
					}
				});
			}

			builder.create().show();

			if(context != null && context instanceof ISaftyDialog) {
				ISaftyDialog activity = (ISaftyDialog)context;
				activity.showDialogFlag();
			}
		} catch (Exception e) {
		}
	}
	public static double getRmbAdd(String... rmb) {
		BigDecimal bignRmb = new BigDecimal("0.000");
		if (rmb != null) {
			for (String str : rmb) {
				if (TextUtils.isEmpty(str) && StringUtils.isDigit(str)) {
					BigDecimal bignPointRate = new BigDecimal(str);
					bignRmb = bignRmb.add(bignPointRate);
				}

			}
		}
		return bignRmb.doubleValue();
	}

	public  static String getImageUrl(String shoplogo){
		String avarUrl = "";
		try {
			if (shoplogo != null &&shoplogo.contains("url_befor")) {
				JSONObject json = new JSONObject(shoplogo);
				String url = json.optString("QVGA");
				String baseUrl = json.optString("url_befor");
				avarUrl = baseUrl + url;
			} else {
				avarUrl = shoplogo;
			}
		} catch (JSONException e){
			avarUrl = shoplogo;
		}
		return avarUrl;
	}
	public static boolean isGoodJson(String json) {
		if (StringUtils.isBlank(json)) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	}

	public static String subZeroAndDot(String money){
		return money.replace(".",",").split(",")[0];
	}

	/**
	 * double转String,保留小数点后两位
	 * @param num
	 * @return
	 */
	public static String doubleToString(double num){
		//使用0.00不足位补0，#.##仅保留有效位
		return new DecimalFormat("0.00").format(num);
	}



	/**
	 * 关闭键盘
	 *
	 * @param activity
	 */
	public static void closeKeyboard(Activity activity) {
		if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (activity.getCurrentFocus() != null) {
				InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	/**
	 * 判断是否有虚拟底部按钮
	 *
	 * @return
	 */
	public static boolean checkDeviceHasNavigationBar(Context context) {
		boolean hasNavigationBar = false;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
		if (id > 0) {
			hasNavigationBar = rs.getBoolean(id);
		}
		try {
			Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
			Method m = systemPropertiesClass.getMethod("get", String.class);
			String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
			if ("1".equals(navBarOverride)) {
				hasNavigationBar = false;
			} else if ("0".equals(navBarOverride)) {
				hasNavigationBar = true;
			}
		} catch (Exception e) {

		}
		return hasNavigationBar;
	}

	/**
	 * 获取底部虚拟按键高度
	 *
	 * @return
	 */
	public static int getNavigationBarHeight(Context context) {
		int navigationBarHeight = 0;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
		if (id > 0 && checkDeviceHasNavigationBar(context)) {
			navigationBarHeight = rs.getDimensionPixelSize(id);
		}
		return navigationBarHeight;
	}

	/**
	 * 判断服务是否在运行
	 * @param mContext
	 * @param className　　Service.class.getName();
	 * @return
	 */
	public static boolean isServiceRunning(Context mContext, String className){
		boolean isRunning = false ;
		ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> seviceList = activityManager.getRunningServices(200);  //200:是运行的service的集合大小，当设置过小时，我没有获取到正在运行的Serice
		if (seviceList.size() <= 0){
			return false;
		}
		for (int i=0 ;i < seviceList.size();i++){
			if (seviceList.get(i).service.getClassName().toString().equals(className)){
				isRunning = true;
				break;
			}
		}
		return  isRunning;
	}

	/**
	 * 验证身份证号是否符合规则
	 * @param text 身份证号
	 * @return
	 */
	public static boolean personIdValidation(String text) {
		String regex = "[0-9]{18}";
		return text.matches(regex);
	}

	/**
	 * * 验证输入的名字是否为“中文”或者是否包含“·”
	 * @param name
	 * @return
	 */
	public static boolean isLegalName(String name){
		if (name.contains("·") || name.contains("•")){
			if (name.matches("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$")){
				return true;
			}else {
				return false;
			}
		}else {
			if (name.matches("^[\\u4e00-\\u9fa5]+$")){
				return true;
			}else {
				return false;
			}
		}
	}

	/**
	 * 验证是否是手机号码
	 * @param phone_number
	 */
	public static boolean isMobiolePhoneNumber(String phone_number) {
		/**
		 * 总结起来就是第一位必定为1，第二位必定为[3-9]，其他位置的可以为0-9
		 */
		//"[1]"代表第1位为数字1，"[3-9]"代表第二位，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		String telRegex = "[1][3-9]\\d{9}";
		if (TextUtils.isEmpty(phone_number)) {
			return false;
		}else {
			return phone_number.matches(telRegex);//手机校验
		}
	}


	/**
	 * 校验银行卡卡号
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		if (StringUtils.isEmpty(cardId)) {
			return false;
		}
		char bit = getBankCardCheckCode(cardId
				.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;
	}
	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 *
	 * @param nonCheckCodeCardId
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null
				|| nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	/**
	 * 判断app是否在前后台运行
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					//"后台"
					return true;
				} else {
					//"前台"
					return false;
				}
			}
		}
		return false;
	}


}
