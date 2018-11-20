package com.call.net.update;

import android.content.Context;

import com.bg.baseutillib.base.BaseRequestDao;
import com.bg.baseutillib.net.NetworkRequest;

import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.base.BaseObserver;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.net.exception.HttpResponseFunc;
import com.call.net.ApiManager;
import com.call.net.update.response.UpdateBean;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UpdateDao extends BaseRequestDao {

//    /**
//     * 升级app
//     */
//    public void  getUpdateInfo(Context context,String versionId,String type,boolean isShow,
//                       final RxNetCallback<UpdateBean> callback) {
//        Map<String, String> paramsMap = new HashMap<String, String>();
//        paramsMap.put("versionId",versionId);
//        paramsMap.put("type",type);
//        NetworkRequest.getNetService(context,UpdateNetService.class, ApiManager.HOST)
//                .update(paramsMap)
////                .map(new ServerResponseFunc<PreOrderBean>())//有时我们会需要使用操作符进行变换
//                .onErrorResumeNext(new HttpResponseFunc<UpdateBean>())
//                .subscribeOn(Schedulers.io())//指定事件源代码执行的线程
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())//指定订阅者代码执行的线程
//                .subscribe(new BaseObserver<UpdateBean>(context, false) {//参数是我们创建的一个订阅者，在这里与事件流建立订阅关系
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        super.onSubscribe(d);
//                        disposables.add(d);
//                    }
//
//                    @Override
//                    public void onError(ApiException e) {
//                        if (callback != null) {
//                            callback.onError(e);
//                        }
//                    }
//
//                    @Override
//                    public void onNext(final UpdateBean value) {
//                        callback.onSuccess(value);
//                    }
//                });
//    }
}
