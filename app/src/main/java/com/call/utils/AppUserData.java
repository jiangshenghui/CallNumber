package com.call.utils;

import com.bg.baseutillib.tool.SharedPreferencesUtil;
import com.call.net.login.response.UserBean;

/**
 * Created by pc on 2018/1/29.
 * 用户个人信息保存的数据
 */

public class AppUserData {

    private static AppUserData userData;
    private static UserBean.BussDataBean userBean;
    private String SESSIONID = "sessionId";
    private String ISLOGIN = "isLogin";
    private String USERID = "userId";
    private String MOBILE = "mobile";
    private String AUTHSTATUS = "authStatus";
    private String AUTHSTATUSTEXT = "authStatusText";
    private String PASSWORD = "passWord";
    private String CITYCODE = "cityCode";
    private String CITYNAME = "cityName";
    private String AVATARURL = "avatarUrl";
    private String LOCATION_NAME = "location_name";
    private String LOCATION_ADDRESS = "location_address";

    private String ORDER_NAME = "order_name";
    private String ORDER_MOBILE = "order_mobile";
    private String LATITUDE = "latitude"; //获取纬度信息
    private String LONGITUDE = "longitude";   //获取经度信息

    private String ISOWNER = "isOwner";//是否是车主
    private String AUDITSTATUS="auditstatus";//车主审核状态
    private String OWNERID="ownerId";//车主id

    private String ISPARTNER = "isPartner";
    private String MONEY = "money";
    private String INVITCOUNT = "invitCount";
    private String INVITECODE = "inviteCode";
    private String INVITEURL = "inviteUrl";

    public static AppUserData getInstance() {
        if (userData == null) {
            userData = new AppUserData();
            userBean = new UserBean.BussDataBean();
        }
        return userData;
    }

    /**
     * 传入UserBean保存相关信息
     * 修改个人信息用
     *
     * @param userData
     */
    public void setUserBean(UserBean userData) {
        SharedPreferencesUtil.writeBoolean(ISPARTNER, userData.isPartner);
        SharedPreferencesUtil.writeString(MONEY, userData.income.money);
        SharedPreferencesUtil.writeString(INVITCOUNT, userData.invitCount);
        SharedPreferencesUtil.writeString(INVITECODE, userData.inviteCode);
        SharedPreferencesUtil.writeString(INVITEURL, userData.inviteUrl);
        SharedPreferencesUtil.writeString(MOBILE, userData.mobile);
//        SharedPreferencesUtil.writeString(AUTHSTATUSTEXT, userData.income.getUserStatusText());
    }

    /**
     * 获取用户的个人信息
     */
    public UserBean.BussDataBean getUserBean() {
        if (userBean != null) {
            userBean.isPartner = SharedPreferencesUtil.readBoolean(ISPARTNER);
            userBean.money = SharedPreferencesUtil.readString(MONEY);
            userBean.invitCount = SharedPreferencesUtil.readString(INVITCOUNT);
            userBean.inviteCode = SharedPreferencesUtil.readString(INVITECODE);
            userBean.inviteUrl = SharedPreferencesUtil.readString(INVITEURL);
            userBean.setMobile(SharedPreferencesUtil.readString(MOBILE));
            return userBean;
        }
        return null;
    }

    /**
     * 获取登录的SessionId
     */
    public void setSessionId(String sessionId) {
        SharedPreferencesUtil.writeString(SESSIONID, sessionId);
    }

    /**
     * 获取登录的SessionId
     */
    public String getSessionId() {
        return SharedPreferencesUtil.readString(SESSIONID);
    }

    /**
     * 保存用户的登录状态
     */
    public void setIsLogin(boolean isLogin){
        SharedPreferencesUtil.writeBoolean(ISLOGIN,isLogin);
    }
    /**
     * 获取用户的登录状态
     */
    public boolean getIsLogin() {
        return SharedPreferencesUtil.readBoolean(ISLOGIN);
    }

    /**
     * 保存用户登录密码
     */
    public void setPassWord(String value) {
        SharedPreferencesUtil.writeString(PASSWORD, value);
    }

    /**
     * 获取用户登录密码
     */
    public String getPassWord() {
        return SharedPreferencesUtil.readString(PASSWORD);
    }

    /**
     * 保存用户登录账号
     */
    public void setMobile(String mobile) {
        SharedPreferencesUtil.writeString(MOBILE, mobile);
    }

    /**
     * 获取用户所在城市code码
     */
    public String getCityCode() {
        return SharedPreferencesUtil.readString(CITYCODE);
    }

    /**
     * 设置用户所在城市code码
     */
    public void setCityCode(String value) {
        SharedPreferencesUtil.writeString(CITYCODE, value);
    }

    /**
     * 获取用户所在城市名
     */
    public String getCityName() {
        return SharedPreferencesUtil.readString(CITYNAME);
    }

    /**
     * 设置用户所在城市名
     */
    public void setCityName(String value) {
        SharedPreferencesUtil.writeString(CITYNAME, value);
    }

    /**
     * 设置用户头像
     */
    public void setAvatarUrl(String value) {
        SharedPreferencesUtil.writeString(AVATARURL, value);
    }

    /**
     * 获取下单时保存的名字
     */
    public String getOrderName() {
        return SharedPreferencesUtil.readString(ORDER_NAME);
    }

    /**
     * 设置下单时保存的名字
     */
    public void setOrderName(String value) {
        SharedPreferencesUtil.writeString(ORDER_NAME, value);
    }

    /**
     * 获取下单时保存的电话
     */
    public String getOrderMobile() {
        return SharedPreferencesUtil.readString(ORDER_MOBILE);
    }

    /**
     * 设置下单时保存的电话
     */
    public void setOrderMobile(String value) {
        SharedPreferencesUtil.writeString(ORDER_MOBILE, value);
    }

    public String getLocation_name() {
        return SharedPreferencesUtil.readString(LOCATION_NAME);
    }

    public void setLocation_name(String location_name) {
        SharedPreferencesUtil.writeString(LOCATION_NAME, location_name);
    }

    public String getLocation_address() {
        return SharedPreferencesUtil.readString(LOCATION_ADDRESS);
    }

    public void setLocation_address(String location_address) {
        SharedPreferencesUtil.writeString(LOCATION_ADDRESS, location_address);
    }


    public String getLatitude() {
        return SharedPreferencesUtil.readString(LATITUDE);
    }

    public void setLatitude(String latitude) {
        SharedPreferencesUtil.writeString(LATITUDE, latitude);
    }

    public String getLongitude() {
        return SharedPreferencesUtil.readString(LONGITUDE);
    }

    public void setLongitude(String longitude) {
        SharedPreferencesUtil.writeString(LONGITUDE, longitude);
    }

    /**
     * 获取当前车主|租客选中状态（本地状态变更使用，不需要跟接口同步）
     */
    public boolean getIsOwner() {
        return SharedPreferencesUtil.readBoolean(ISOWNER);
    }

    /**
     * 修改当前车主|租客选中状态（本地状态变更使用，不需要跟接口同步）
     */
    public void setIsOwner(boolean isOwner) {
        SharedPreferencesUtil.writeBoolean(ISOWNER, isOwner);
    }

    public String getAuditStatus() {
        return SharedPreferencesUtil.readString(AUDITSTATUS);
    }

    public void setAuditStatus(String auditStatus) {
        SharedPreferencesUtil.writeString(AUDITSTATUS, auditStatus);
    }

    public String getOwnerId() {
        return SharedPreferencesUtil.readString(OWNERID);
    }

    public void setOwnerId(String ownerId) {
        SharedPreferencesUtil.writeString(OWNERID, ownerId);
    }

    /**
     * 删除用户个人信息
     */
    public void removeUserData() {
        SharedPreferencesUtil.removeString(SESSIONID);
        SharedPreferencesUtil.removeBoolean(ISLOGIN);
        SharedPreferencesUtil.writeBoolean(ISLOGIN, false);
        SharedPreferencesUtil.writeString(SESSIONID, "");
        SharedPreferencesUtil.writeString(AUTHSTATUS, "");
        SharedPreferencesUtil.writeString(AUTHSTATUSTEXT, "");
//        SharedPreferencesUtil.writeString(CITYCODE, "");
//        SharedPreferencesUtil.writeString(CITYNAME, "");

        SharedPreferencesUtil.removeString(USERID);
//        SharedPreferencesUtil.removeString(MOBILE);
        SharedPreferencesUtil.removeString(AVATARURL);
        SharedPreferencesUtil.removeString(PASSWORD);

    }
}
