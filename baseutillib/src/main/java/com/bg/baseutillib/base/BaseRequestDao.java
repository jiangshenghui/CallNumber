package com.bg.baseutillib.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 解决
 * 请求的过程中Activity已经退出了, 这个时候如果回到主线程去更新UI, APP崩溃
 */
public class BaseRequestDao {

    public CompositeDisposable disposables = new CompositeDisposable();

    /**
     * 关闭所有网络请求
     */
    public void onDisposed() {
        if (disposables != null) {
            disposables.clear();
        }
    }
}
