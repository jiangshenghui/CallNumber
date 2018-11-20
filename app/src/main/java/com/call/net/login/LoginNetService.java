package com.call.net.login;

import com.bg.baseutillib.net.CommonNetBean;
import com.bg.baseutillib.net.base.BaseResponse;
import com.call.net.ApiManager;
import com.call.net.login.response.CodeBean;
import com.call.net.login.response.SessionBean;
import com.call.net.login.response.UserBean;
import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lijie on 2018/6/29
 * Description:
 * Email: 731098696@qq.com
 * Version：1.0
 */
public interface LoginNetService {
    /**
     * 登录
     */
    @GET("WebApi.ashx")
    Observable<UserBean> login( @Query("json") String param);
}
