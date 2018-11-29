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
import com.call.net.login.request.CommonBody;
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
    /**
     * 获取服务窗口
     */
    public void getServiceWindows(final Context context, final CommonBody commonBody,
                                  final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.GETDEPLIST_WINDOW_LIST;
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
    /**
     * 获取服务网点队列列表
     */
    public void getDepartGroupList(final Context context, final CommonBody commonBody,
                                  final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.GETDEPLIST_GROUP;
        commonBody.timeStamp = System.currentTimeMillis()+"";
        commonBody.status = "0";
        commonBody.token ="0";
        Gson gson = new Gson();
        Log.d("jsh","params:"+gson.toJson(commonBody));
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
    /**
     * 获取队列排队列表
     */
    public void getGroupQueueList(final Context context, final CommonBody commonBody,
                                   final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.GETDEPART_GROUP;
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
    /**
     * 呼叫下一位
     */
    public void clientCallNext(final Context context, final CommonBody commonBody,
                                  final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.CLIENT_CALL_NEXT;
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
    /**
     * 重呼
     */
    public void clientReCall(final Context context, final CommonBody commonBody,
                               final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.CLIENT_RE_CALL;
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
    /**
     * 办理完成
     */
    public void clientHandleCall(final Context context, final CommonBody commonBody,
                             final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.CLIENT_CALL_HANDLE;
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
    /**
     * 过号
     */
    public void clientPassCall(final Context context, final CommonBody commonBody,
                                 final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.CLIENT_PASS_CALL;
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
    /**
     * 暂停
     */
    public void clientPauseCall(final Context context, final CommonBody commonBody,
                               final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.CLIENT_PAUSE_CALL;
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
    /**
     * 解散
     */
    public void clientDissolutionQueue(final Context context, final CommonBody commonBody,
                                final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.CLIENT_DISSOLUTION_QUEUE;
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
    /**
     * 获取所选队列的排队人数
     */
    public void getGroupWaitNumber(final Context context, final CommonBody commonBody,
                                       final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.CLIENT_GROUP_WAIT_NUMBER;
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
    /**
     * 窗口办理情况统计
     */
    public void getWindowHandleStatictics(final Context context, final CommonBody commonBody,
                                   final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.CLIENT_WINDOW_STATICTICS;
        commonBody.timeStamp = System.currentTimeMillis()+"";
        commonBody.status = "0";
        commonBody.token ="0";
        Gson gson = new Gson();
        Log.d("jsh","getGroupQueueList:"+gson.toJson(commonBody));
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
    /**
     * 获取用户信息
     */
    public void getArchivalUserInfo(final Context context, final CommonBody commonBody,
                                          final RxNetCallback<ServiceNetWorkBean> callback) {
        commonBody.actionName = ApiManager.CLIENT_USER_INFO;
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
