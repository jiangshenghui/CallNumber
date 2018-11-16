package com.bg.baseutillib.net.exception;

import com.bg.baseutillib.net.base.BaseResponse;
import com.bg.baseutillib.net.StatusCode;

import io.reactivex.functions.Function;

/**
 * 拦截服务器返回的错误
 * <p>
 * 返回的数据包括最外层，分页加载的时候需要使用该类。
 */

public class ServerResponseFunc1<T> implements Function<BaseResponse<T>, BaseResponse<T>> {

    @Override
    public BaseResponse<T> apply(BaseResponse<T> response) throws Exception {
        if (response.getStatus() != StatusCode.STATUS_CODE_SUCCESS) {
            throw new ServerException(response.getStatus(), response.getMsg());
        }
        return response;
    }
}

