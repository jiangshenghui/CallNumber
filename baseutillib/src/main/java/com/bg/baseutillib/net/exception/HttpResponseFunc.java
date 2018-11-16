package com.bg.baseutillib.net.exception;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 这个拦截器主要是将异常信息转化为用户”能看懂”的友好提示
 */

public class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {

    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandler.handleException(throwable));
    }
}

