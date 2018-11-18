package com.call.net.pay;



import android.database.Observable;

import com.call.net.ApiManager;
import com.call.net.pay.response.PreOrderBean;

import java.util.Map;


import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PayNetService {
    /**
     * 登录
     */
    @POST(ApiManager.PAY_URL)
    Observable<PreOrderBean> pay(@Body Map<String, String> paramsMap);
}
