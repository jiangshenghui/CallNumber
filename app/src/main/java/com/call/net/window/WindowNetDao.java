package com.call.net.window;

import com.call.net.login.response.UserBean;
import com.call.net.window.response.ServiceNetWorkBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WindowNetDao {

    /**
     * 获取服务网点
     */
    @GET("WebApi.ashx")
    Observable<ServiceNetWorkBean> getServiceNetWork(@Query("json") String param);
}
