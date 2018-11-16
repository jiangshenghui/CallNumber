package com.bg.baseutillib.base.permissions.inter;

/**
 * Created by pc on 2017/9/18.
 * 权限申请接口
 */

public interface PermissionsResultListener {

    /**
     * 已申请权限
     */
    void onPermissionGranted();

    /**
     * 拒绝授权
     */
    void onPermissionDenied();
}
