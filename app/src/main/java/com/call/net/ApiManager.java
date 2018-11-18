package com.call.net;

public class ApiManager {

//      ### 测试环境地址 http://192.168.31.241:7001
//            ### 生产环境地址 http://pai.foxxgame.com
    /**
     * app的域名
     */
    //public static final String HOST = "http://www.lvdaotaxi.com/";//正式环境
//    public static final String HOST = "http://47.107.72.255/";//正式环境
//    public static final String HOST = "https://daorv-rent.icebartech.com/";//测试环境
//    public static final String HOST = "http://192.168.31.241:7001/";//测试环境
  //  public static final String HOST =  "https://pai.foxxgame.com/";//正式环境
    public static final String HOST =  " http://ghapi.paihao123.com/";//正式环境

//    public static final String HOST = "http://yk2w5t.natappfree.cc/";//测试环境




    public final static String APP_ID= "wxf2243a9b03aea7d4" ;
    public final static String WX_APP_SECRET= "bf5e5123f3d280bb9f9bc6d8f554eb7b";
    public final static String QQ_APP_ID= "1107893107" ;
    public final static String QQ_WX_APP_KEY= "CzwYTuutMYNPw2BI";
    public final static String WX_TYPE = "02" ;

    /**
     * app登录
     */
    public static final String LOGIN = "CallUserLogin";


    /**
     * app用户详情信息
     */
    public static final String FINDDETAIL = "api/user";

    /**
     * 扫码确认登陆
     */
    public static final String AGREELOGIN = "api/app/usercore/agreeLogin";
    /**
     * app注册
     */
    public static final String REGISTER = "api/user/reg";
    /**
     * 校验是否注册
     */
    public static final String CHECKREGISTER = "api/app/usercore/checkRegister";
    /**
     * 获取验证码
     */
    public static final String FIND_MOBILE_CODE = "api/captcha";
    /**
     * app重置密码
     */
    public static final String RESETPASSWORD = "api/user/forgotPassword";
    /**
     * 支付订单
     */
    public static final String PAY_URL = "api/pay/createOrder";

    /**
     * 成为合伙人
     */
    public static final String PARTNER_URL = "api/user/bePartner";

    /**
     * 合伙人
     */
    public static final String UPGRADE_PARTNER_URL = "api/user/partnerLevelUp";


}
