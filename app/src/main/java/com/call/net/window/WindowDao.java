package com.call.net.window;

import android.content.Context;
import android.util.Log;

import com.bg.baseutillib.base.BaseRequestDao;
import com.bg.baseutillib.net.NetworkRequest;
import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.base.BaseObserver;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.net.exception.HttpResponseFunc;
import com.call.net.ApiManager;
import com.call.net.login.LoginNetService;
import com.call.net.login.request.CommonBody;
import com.call.net.login.response.UserBean;
import com.call.net.window.response.ServiceNetWorkBean;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WindowDao  extends BaseRequestDao {

    /**
     * 获取服务网点
     */
    public void getServiceNetWork(final Context context, final CommonBody commonBody,
                      final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.GETDEPLIST;
        commonBody.timeStamp = System.currentTimeMillis()+"";
        commonBody.status = "0";
        commonBody.token ="0";
        Gson gson = new Gson();
        NetworkRequest.getNetService(context, WindowNetDao.class, ApiManager.HOST)
                .getServiceNetWork(gson.toJson(commonBody))
//                .map(new ServerResponseFunc<UserBean>())//有时我们会需要使用操作符进行变换
                .onErrorResumeNext(new HttpResponseFunc<ServiceNetWorkBean>())
                .subscribeOn(Schedulers.io())//指定事件源代码执行的线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//指定订阅者代码执行的线程
                .subscribe(new BaseObserver<ServiceNetWorkBean>(context, false) {//参数是我们创建的一个订阅者，在这里与事件流建立订阅关系

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
                    public void onNext(final ServiceNetWorkBean value) {
                        if (callback != null) {
                            callback.onSuccess(value);
                        }
                    }
                });
    }
}
