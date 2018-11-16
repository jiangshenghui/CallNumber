package com.call.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final double MAX_NUMBER = 1000000000d;
    public static final double MAX_INPUT_NUMBER = 100000000d;

    public static String formatMoney(double number) {
        if (BigDecimalUtils.compareTo(number, MAX_NUMBER) == 1||BigDecimalUtils.compareTo(number, MAX_NUMBER) == 0) {
            // 大于这个数,要用科学计数法
            return scientificFormatter(number);
        } else {
            // 小于这个数
            return splitFormatter(number);
        }
    }


    public static String doubleToStr(double d) {
        DecimalFormat format = new DecimalFormat("###0.00");
        String str = "";
        str = format.format(d);
        return str;
    }

    public static String splitFormatter(double number) {
        if(number == 0){
            return "0.00";
        }
        return new DecimalFormat("##0.00").format(roundOff(number, 2));
    }

    public static String scientificFormatter(double number) {
		return new DecimalFormat("#.##E0").format(number);
	}
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }
	public static String simpleSplitFormatter(double number) {
		String doubleStr = String.valueOf(number);
		if (doubleStr.contains(".")) {
			String value = new DecimalFormat(",###").format(number)
					+ doubleStr.substring(doubleStr.indexOf("."),
							doubleStr.length());
			return value;
		} else {
			return new DecimalFormat(",###").format(number);
		}
	}
	
    static double roundOff(double x, int position) {
        double a = x;
        double temp = Math.pow(10.0, position);
        a *= temp;
        a = Math.round(a);
        return (a / (double)temp);
    }
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(CharSequence cs) {
		return !StringUtils.isBlank(cs);
	}

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static String nullToEmpty(CharSequence cs) {
        return isEmpty(cs) ? "" : cs.toString();
    }
    
    public static int getWordCount(CharSequence s)  {
        if(s == null) {
            return 0;
        }
        int length = 0;  
        for(int i = 0; i < s.length(); i++) {  
            int ascii = Character.codePointAt(s, i);
            if(ascii >= 0 && ascii <=255)  
                length++;  
            else  
                length += 3;  
                  
        }  
        return length;  
    } 
    
    @SuppressWarnings("unused")
    public static boolean isValidMobile(String mobile){
       /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String num = "[1][123456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (StringUtils.isEmpty(mobile)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return remove(mobile).matches(num);
        }
    }
    
    public static boolean isValidAccount(CharSequence cs) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        Matcher matcher = pattern.matcher(cs);
        
        return matcher.matches();
    }
    
	public static boolean isNotEmpty(CharSequence cs) {
		return !StringUtils.isEmpty(cs);
	}
	
	/**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidMathNumber(String val) {
        Pattern pattern = Pattern.compile("((\\d+)?(\\.\\d+)?)");
        Matcher matcher = pattern.matcher(val);
        return matcher.matches();
    }
    
    /**
     * 判断字符串是否是数字
     */
    public static boolean isDigit(String value) {
        return isInteger(value) || isDouble(value);
    }  
    
    public static String saftyTrimPhoneNum(String phoneNumber) {
        String ret = phoneNumber;
        
        if(StringUtils.isNotEmpty(phoneNumber)) {
            phoneNumber = phoneNumber.replaceAll(" |-", "");
            if(phoneNumber.startsWith("+") && phoneNumber.length() == 14) {
                phoneNumber = phoneNumber.substring(3);
            }
            ret = phoneNumber.replaceAll("\\D", "");
        }
        
        return ret;
    }
    /**
     * 根据类名获取对象实例
     * @author leibing
     * @createTime 2016/08/30
     * @lastModify 2016/08/30
     * @param className 类名
     * @return
     */
    public static Object getObject(String className){
        Object object = null;
        if(StringUtils.isNotEmpty(className)){
            try {
                object = Class.forName(className).newInstance();
            }catch(ClassNotFoundException cnf) {
            }
            catch(InstantiationException ie) {
            }
            catch(IllegalAccessException ia) {
            }
        }
        return object;
    }
    public static String remove(String str){
        if(isNotEmpty(str)){
            str =  str.replaceAll(" ", "");
        }
        return str;
    }
    /**
     * 格式化金额处理,格式:200.00
     *
     * @param amount
     * @return
     */
    public static String formatterAmount(String amount) {
        if(StringUtils.isNotEmpty(amount)){
            BigDecimal decimal = new BigDecimal(amount);
            DecimalFormat formatter = new DecimalFormat("0.000");
            if (amount != null) {
                return formatter.format(decimal.abs().doubleValue());
            }
        }
        return "0.000";
    }
    /**
     * 格式化金额处理,格式:200.00
     *
     * @param amount
     * @return
     */
    public static String formatterAmountOne(String amount) {
        if(StringUtils.isNotEmpty(amount)){
            BigDecimal decimal = new BigDecimal(amount);
            DecimalFormat formatter = new DecimalFormat("0.00");
            if (amount != null) {
                return formatter.format(decimal.abs().doubleValue());
            }
        }
        return "0.00";
    }
    /**
     * 格式化金额处理,格式:200.00
     *
     * @param amount
     * @return
     */
    public static String formatterAmountPoint(String amount) {
        if(StringUtils.isNotEmpty(amount)){
            BigDecimal decimal = new BigDecimal(amount);
            DecimalFormat formatter = new DecimalFormat("0.0");
            if (amount != null) {
                return formatter.format(decimal.abs().doubleValue());
            }
        }
        return "0.0";
    }
    /**
     *
     * @param amount
     * @return
     */
    public static double formatterIntAmount(String amount) {
        if(StringUtils.isNotEmpty(amount)&&isDigit(amount)){
            BigDecimal decimal = new BigDecimal(amount);
            return decimal.abs().doubleValue();
        }
        return 0;
    }
   public static String getPhone(String phone){
       if(StringUtils.isNotEmpty(phone)&&phone.length()>10){
            phone = phone.substring(0,3)+"****"+phone.substring(7,phone.length());
       }
       return phone;
   }

    /**
     * 截取子串
     *
     * @param srcStr
     *            源串
     * @param pattern
     *            匹配模式
     * @param indexOrLast
     * @param frontOrBack
     * @return
     */
    public static String spliteString(String srcStr, String pattern,
                                      String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置
        } else {
            loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc); // 截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
        }
        return result;
    }
    public static String getBillType(int tradeType) {
        String result = "";//0:充值   1：红包   2：打电话  3 ：悬赏  4：视频  5 ：发布项目  6 ：发布咨询  7：加好友
        switch (tradeType){
            case  0:
                result = "充值";
                break;
            case  1:
                result = "红包";
                break;
            case  2:
                result = "打电话";
                break;
            case  3:
                result = "悬赏";
                break;
            case  4:
                result = "视频";
                break;
            case  5:
                result = "发布项目";
                break;
            case  6:
                result = "发布咨询";
                break;
            case  7:
                result = "加好友";
                break;
        }
        return result;
    }

    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            }

        }
        return matches;
    }

}
