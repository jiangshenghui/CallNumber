package com.call.net.pay;

import com.hx.zzp.net.ApiManager;
import com.hx.zzp.net.pay.response.PreOrderBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PayNetService {
    /**
     * 登录
     */
    @POST(ApiManager.PAY_URL)
    Observable<PreOrderBean> pay(@Body Map<String, String> paramsMap);
}
