package com.call.net.pay.response;

import java.io.Serializable;

public class PreOrderBean implements Serializable {
    public String bussData;
    public String ono;//订单编号
    public String oid;//订单标识
    public String osecbarpic;// 预订单查询URL
    public String ostat;//订单状态
    public AliPay data;//下单附加信息
    public class AliPay{
        public String alipay;
        public String price;
        public String token_id;
        public String appid;
        public String partnerid;
        public String prepayid;
        public String noncestr;
        public String packageA;
        public String timestamp;
        public String sign;
        public String url;
    }
}
