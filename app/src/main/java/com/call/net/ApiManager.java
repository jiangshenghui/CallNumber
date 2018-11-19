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

    /**
     * a登录
     */
    public static final String LOGIN = "CallUserLogin";

    /**
     * 获取服务网点
     */
    public static final String GETDEPLIST = "GetDepList";
    /**
     * 获取服务窗口
     */
    public static final String GETDEPLIST_WINDOW_LIST = "GetDepWindowList";
    /**
     * 获取服务网点队列列表
     */
    public static final String GETDEPLIST_GROUP = "getDepartGroupList";
    /**
     * 获取队列排队列表
     */
    public static final String GETDEPART_GROUP = "GetGroupQueueList";
    /**
     * 呼叫下一位
     */
    public static final String CLIENT_CALL_NEXT = "ClientCallNext";
    /**
     * 重呼
     */
    public static final String CLIENT_RE_CALL = "ClientReCall";
    /**
     * 办理完成
     */
    public static final String CLIENT_CALL_HANDLE = "ClientHandleCall";
    /**
     * 过号
     */
    public static final String CLIENT_PASS_CALL = "ClientPassCall";
    /**
     * 暂停
     */
    public static final String CLIENT_PAUSE_CALL = "ClientPauseCall";
    /**
     * 解散
     */
    public static final String CLIENT_DISSOLUTION_QUEUE = "ClientDissolutionQueue";
    /**
     * 12、获取所选队列的排队人数
     */
    public static final String CLIENT_GROUP_WAIT_NUMBER = "GetGroupWaitNumber";
    /**
     * 13、窗口办理情况统计
     */
    public static final String CLIENT_WINDOW_STATICTICS = "GetWindowHandleStatictics";
    /**
     * 14、获取用户信息
     */
    public static final String CLIENT_USER_INFO = "GetArchivalUserInfo";
}
