package com.call.net.login.response;

/**
 * Created by lijie on 2018/8/1
 * Description:
 * Email: 731098696@qq.com
 * Version：1.0
 */
public class SessionBean {

    /**
     * bussData : string
     */
    public String token;
    private String bussData;

    public  boolean isPartner;
    public String getBussData() {
        return bussData;
    }

    public void setBussData(String bussData) {
        this.bussData = bussData;
    }
}
