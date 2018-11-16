package com.call.net.login.request;

/**
 * Created by lijie on 2018/8/1
 * Description:
 * Email: 731098696@qq.com
 * Version：1.0
 */
public class RegisterBody {
    /**
     * mobile : 13169920185
     * password : 123456
     * validCode : 209324
     */

    private String mobile;
    private String password;
    private String validCode;
    public String inviteCode;
    public String idCard;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }
}
