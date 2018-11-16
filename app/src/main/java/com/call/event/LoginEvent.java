package com.call.event;

import java.io.Serializable;

public class LoginEvent implements Serializable {

    private boolean isLogin = false;

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
