package com.bg.baseutillib.net.exception;

import com.bg.baseutillib.net.StatusCode;
import com.bg.baseutillib.net.base.BaseStringResponse;

import io.reactivex.functions.Function;

/**
 * 拦截服务器返回的错误
 * <p>
 * 只返回有效数据data，不返回msg、code等数据
 */

public class ServerResponseStringFunc<T> implements Function<String, String> {
    @Override
    public String apply(String response) throws Exception {
//        if (response.getStatus() != StatusCode.STATUS_CODE_SUCCESS) {
//            throw new ServerException(response.getStatus(), response.getMsg());
//        }
        return response;
    }
}

