package com.call.net.login.request;

import java.io.Serializable;
import java.util.List;

public class CommonBody implements Serializable {

    public String actionName;
    public String timeStamp;
    public String status;
    public String token;

    public List<ParamsSet> paramsSet;

}
