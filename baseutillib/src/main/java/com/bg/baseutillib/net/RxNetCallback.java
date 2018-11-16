package com.bg.baseutillib.net;

import com.bg.baseutillib.net.exception.ApiException;

/**
 * 使用RxJava与Retrofit联网请求数据的回调接口
 */

public interface RxNetCallback<T> {

//    void onSubscribe(Disposable d);

    /**
     * 联网成功
     *
     * @param t
     */
    void onSuccess(T t);

    /**
     * 联网失败
     *
     * @param e
     */
    void onError(ApiException e);

//    /**
//     * 回调完成，onError与onComplete只会回调一个
//     */
//    void onComplete();
}
