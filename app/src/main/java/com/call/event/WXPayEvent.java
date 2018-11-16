package com.call.event;

import java.io.Serializable;

/**
 * 微信支付
 */

public class WXPayEvent implements Serializable {
    private int code;

    public WXPayEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
