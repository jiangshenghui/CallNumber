package com.call.event;

public class RechargeEvent {
    private int code;

    public RechargeEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
