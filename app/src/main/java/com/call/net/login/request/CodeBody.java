package com.call.net.login.request;

/**
 * Created by lijie on 2018/8/1
 * Description:
 * Email: 731098696@qq.com
 * Version：1.0
 */
public class CodeBody {
    /**
     * mobile : 13169920185
     * mobileCodeType : user_reigter
     */

    private String mobile;
    private String mobileCodeType;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileCodeType() {
        return mobileCodeType;
    }

    public void setMobileCodeType(String mobileCodeType) {
        this.mobileCodeType = mobileCodeType;
    }
}
