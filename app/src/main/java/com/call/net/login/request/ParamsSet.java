package com.call.net.login.request;

import java.io.Serializable;

public class ParamsSet implements Serializable {

    public String name;//用户角色（0为管理员，其他为呼叫员）
    public String value;//上级用户ID
    public String compare;
}
