package com.bg.baseutillib.net.exception;

import com.bg.baseutillib.net.base.BaseResponse;
import com.bg.baseutillib.net.StatusCode;

import io.reactivex.functions.Function;

/**
 * 拦截服务器返回的错误
 * <p>
 * 只返回有效数据data，不返回msg、code等数据
 */

public class ServerResponseFunc<T> implements Function<BaseResponse<T>, T> {
    @Override
    public T apply(BaseResponse<T> response) throws Exception {
        if (!response.success) {
            throw new ServerException(response.getStatus(), response.getMsg());
        }
        return response.getData();
    }
}

