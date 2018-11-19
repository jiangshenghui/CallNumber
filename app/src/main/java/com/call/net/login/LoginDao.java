package com.call.net.login;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.bg.baseutillib.base.BaseRequestDao;
import com.bg.baseutillib.net.CommonNetBean;
import com.bg.baseutillib.net.NetworkRequest;
import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.base.BaseObserver;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.net.exception.HttpResponseFunc;
import com.bg.baseutillib.net.exception.ServerResponseFunc;
import com.bg.baseutillib.tool.SharedPreferencesUtil;
import com.call.net.ApiManager;
import com.call.net.login.request.CommonBody;
import com.call.net.login.request.ParamsSet;
import com.call.net.login.request.RegisterBody;
import com.call.net.login.response.CodeBean;
import com.call.net.login.response.SessionBean;
import com.call.net.login.response.UserBean;
import com.call.utils.AppUserData;
import com.call.utils.Sha1;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginDao extends BaseRequestDao {

    /**
     * 登录
     */
    public void login(final Context context, final CommonBody commonBody, String password,
                      final RxNetCallback<UserBean> callback) {
        commonBody.actionName = ApiManager.LOGIN;
        commonBody.timeStamp = System.currentTimeMillis()+"";
        commonBody.status = "0";
        commonBody.token ="0";
        Gson gson = new Gson();
        NetworkRequest.getNetService(context, LoginNetService.class, ApiManager.HOST)
                .login(gson.toJson(commonBody))
//                .map(new ServerResponseFunc<UserBean>())//有时我们会需要使用操作符进行变换
                .onErrorResumeNext(new HttpResponseFunc<UserBean>())
                .subscribeOn(Schedulers.io())//指定事件源代码执行的线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//指定订阅者代码执行的线程
                .subscribe(new BaseObserver<UserBean>(context, false) {//参数是我们创建的一个订阅者，在这里与事件流建立订阅关系

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        disposables.add(d);
                    }

                    @Override
                    public void onError(ApiException e) {

                        if (callback != null) {
                            callback.onError(e);
                        }
                    }

                    @Override
                    public void onNext(final UserBean value) {
//                        AppUserData.getInstance().setSessionId(value.token);
//                        SharedPreferencesUtil.writeCookie("authorization",value.token);
                        Log.d("jsh","login"+value.paramsSet.get(0).value);
                        if (callback != null) {
                            callback.onSuccess(value);
                        }

                    }
                });
    }

}
