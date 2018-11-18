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
    /**
     * 成为合伙人
     */
    public void bePartner(final Context context, String name, String idCard,String level,
                      final RxNetCallback<SessionBean> callback) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("name",name);
//        paramsMap.put("idCard",idCard);
        paramsMap.put("level",level);
        NetworkRequest.getNetService(context, LoginNetService.class, ApiManager.HOST)
                .bePartner(paramsMap)
//                .map(new ServerResponseFunc<SessionBean>())//有时我们会需要使用操作符进行变换
                .onErrorResumeNext(new HttpResponseFunc<SessionBean>())
                .subscribeOn(Schedulers.io())//指定事件源代码执行的线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//指定订阅者代码执行的线程
                .subscribe(new BaseObserver<SessionBean>(context, false) {//参数是我们创建的一个订阅者，在这里与事件流建立订阅关系

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
                    public void onNext(final SessionBean value) {
                        if (callback != null) {
                            callback.onSuccess(value);
                        }
                    }
                });
    }

    /**
     * 成为合伙人
     */
    public void partnerLevelUp(final Context context,String level,
                          final RxNetCallback<SessionBean> callback) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("level",level);
        NetworkRequest.getNetService(context, LoginNetService.class, ApiManager.HOST)
                .partnerLevelUp(paramsMap)
//                .map(new ServerResponseFunc<SessionBean>())//有时我们会需要使用操作符进行变换
                .onErrorResumeNext(new HttpResponseFunc<SessionBean>())
                .subscribeOn(Schedulers.io())//指定事件源代码执行的线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//指定订阅者代码执行的线程
                .subscribe(new BaseObserver<SessionBean>(context, false) {//参数是我们创建的一个订阅者，在这里与事件流建立订阅关系

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
                    public void onNext(final SessionBean value) {
                        if (callback != null) {
                            callback.onSuccess(value);
                        }
                    }
                });
    }

    /**
     * 注册
     */
    public void register(final Context context, final RegisterBody body,
                         final RxNetCallback<SessionBean> callback) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("phone",body.getMobile());
        paramsMap.put("password",Sha1.getSha1(body.getPassword()));
        paramsMap.put("captcha",body.getValidCode());
        paramsMap.put("inviteCode",body.inviteCode);
        paramsMap.put("idCard",body.idCard);
        NetworkRequest.getNetService(context, LoginNetService.class, ApiManager.HOST)
                .register(paramsMap)
                .map(new ServerResponseFunc<SessionBean>())
                .onErrorResumeNext(new HttpResponseFunc<SessionBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SessionBean>(context, true) {

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
                    public void onNext(final SessionBean value) {
                        AppUserData.getInstance().setSessionId(value.token);
                        SharedPreferencesUtil.writeCookie("authorization",value.token);
                        findDetail(context, new RxNetCallback<UserBean>() {
                            @Override
                            public void onSuccess(UserBean userBean) {
                                if (userBean != null) {
                                    userBean.mobile = body.getMobile();
                                    AppUserData.getInstance().setUserBean(userBean);
                                    AppUserData.getInstance().setIsLogin(true);
                                    if (callback != null) {
                                        callback.onSuccess(value);
                                    }
                                }
                            }

                            @Override
                            public void onError(ApiException e) {
                                if (callback != null) {
                                    callback.onError(e);
                                }
                            }
                        });
                    }
                });
    }

    /**
     * 获取验证码
     */
    public void findMobileCode(Context context, String phone,
                               final RxNetCallback<CodeBean> callback) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("phone",phone);
        NetworkRequest.getNetService(context, LoginNetService.class, ApiManager.HOST)
                .findMobileCode(paramsMap)
//                .map(new ServerResponseFunc<CodeBean>())
                .onErrorResumeNext(new HttpResponseFunc<CodeBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<CodeBean>(context, true) {
                    @Override
                    public void onError(ApiException e) {
                        if (callback != null) {
                            callback.onError(e);
                        }
                    }

                    @Override
                    public void onNext(CodeBean value) {
                        if (callback != null) {
                            callback.onSuccess(value);
                        }
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        disposables.add(d);
                    }
                });
    }

    /**
     * app重置密码
     */
    public void resetPassword(final  Context context, final  RegisterBody body,
                              final RxNetCallback<SessionBean> callback) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("phone",body.getMobile());
        paramsMap.put("password",Sha1.getSha1(body.getPassword()));
        paramsMap.put("captcha",body.getValidCode());
        NetworkRequest.getNetService(context, LoginNetService.class, ApiManager.HOST)
                .resetPassword(paramsMap)
                .map(new ServerResponseFunc<SessionBean>())//有时我们会需要使用操作符进行变换
                .onErrorResumeNext(new HttpResponseFunc<SessionBean>())
                .subscribeOn(Schedulers.io())//指定事件源代码执行的线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//指定订阅者代码执行的线程
                .subscribe(new BaseObserver<SessionBean>(context, false) {//参数是我们创建的一个订阅者，在这里与事件流建立订阅关系
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
                    public void onNext(final SessionBean value) {
                        AppUserData.getInstance().setSessionId(value.token);
                        SharedPreferencesUtil.writeCookie("authorization",value.token);

                        findDetail(context, new RxNetCallback<UserBean>() {
                            @Override
                            public void onSuccess(UserBean userBean) {
                                if (userBean != null) {
                                    userBean.mobile = body.getMobile();
                                    AppUserData.getInstance().setUserBean(userBean);
                                    AppUserData.getInstance().setIsLogin(true);
                                    if (callback != null) {
                                        callback.onSuccess(value);
                                    }
                                }
                            }

                            @Override
                            public void onError(ApiException e) {
                                if (callback != null) {
                                    callback.onError(e);
                                }
                            }
                        });
                    }
                });
    }

    /**
     * app用户详情信息
     */
    public void findDetail(Context context, final RxNetCallback<UserBean> callback) {
        NetworkRequest.getNetService(context, LoginNetService.class, ApiManager.HOST)
                .findDetail()
                .map(new ServerResponseFunc<UserBean>())
                .onErrorResumeNext(new HttpResponseFunc<UserBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserBean>(context, true) {
                    @Override
                    public void onError(ApiException e) {
                        if (callback != null) {
                            callback.onError(e);
                        }
                    }

                    @Override
                    public void onNext(UserBean value) {
                        if (callback != null) {
                            callback.onSuccess(value);
                        }
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        disposables.add(d);
                    }
                });
    }

    /**
     * 校验是否注册
     */
    public void checkRegister(Context context, String mobile,
                              final RxNetCallback<CommonNetBean> callback) {
        NetworkRequest.getNetService(context, LoginNetService.class, ApiManager.HOST)
                .checkRegister(mobile)
                .map(new ServerResponseFunc<CommonNetBean>())
                .onErrorResumeNext(new HttpResponseFunc<CommonNetBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<CommonNetBean>(context) {
                    @Override
                    public void onError(ApiException e) {
                        if (callback != null) {
                            callback.onError(e);
                        }
                    }

                    @Override
                    public void onNext(CommonNetBean value) {
                        if (callback != null) {
                            callback.onSuccess(value);
                        }
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        disposables.add(d);
                    }
                });
    }
}
