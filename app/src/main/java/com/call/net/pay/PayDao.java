package com.call.net.pay;

import android.content.Context;

import com.bg.baseutillib.base.BaseRequestDao;
import com.bg.baseutillib.net.NetworkRequest;
import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.base.BaseObserver;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.net.exception.HttpResponseFunc;
import com.hx.zzp.net.ApiManager;
import com.hx.zzp.net.pay.response.PreOrderBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PayDao  extends BaseRequestDao {

    /**
     * 支付预下单
     */
    public void payPre(final Context context, String goodsId, String paymentType,String money,
                      final RxNetCallback<PreOrderBean> callback) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("goodsName",goodsId);
        paramsMap.put("paymentType",paymentType);
        paramsMap.put("money",money);
        NetworkRequest.getNetService(context, PayNetService.class, ApiManager.HOST)
                .pay(paramsMap)
//                .map(new ServerResponseFunc<PreOrderBean>())//有时我们会需要使用操作符进行变换
                .onErrorResumeNext(new HttpResponseFunc<PreOrderBean>())
                .subscribeOn(Schedulers.io())//指定事件源代码执行的线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//指定订阅者代码执行的线程
                .subscribe(new BaseObserver<PreOrderBean>(context, false) {//参数是我们创建的一个订阅者，在这里与事件流建立订阅关系

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
                    public void onNext(final PreOrderBean value) {
                        callback.onSuccess(value);
                    }
                });
    }
}
