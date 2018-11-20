package com.call.net.window.response;

import com.call.net.login.request.ParamsSet;

import java.io.Serializable;
import java.util.List;

public class ServiceNetWorkBean implements Serializable {

    public String actionName;
    public String status;
    public String timeStamp;

    public List<ParamsSet> paramsSet;

    public List<EntrySetBean> entrySet;

}
