package com.bg.baseutillib.net;

/**
 * 通用网络请求返回实体类
 */

public class CommonNetBean {

    /**
     * status : 返回码，200：成功；500：失败；401：未登录
     * msg : 提示信息
     * data : {"bussData":{}}
     */

    private String status;
    private String msg;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bussData : {}
         */

        private BussDataBean bussData;

        public BussDataBean getBussData() {
            return bussData;
        }

        public void setBussData(BussDataBean bussData) {
            this.bussData = bussData;
        }

        public static class BussDataBean {
        }
    }
}
