package com.bg.baseutillib.tool;

import android.content.Context;

import com.bg.baseutillib.net.INetService;
import com.bg.baseutillib.net.NetworkRequest;
import com.bg.baseutillib.net.RxNetCallback;
import com.bg.baseutillib.net.base.BaseObserver;
import com.bg.baseutillib.net.exception.ApiException;
import com.bg.baseutillib.net.exception.HttpResponseFunc;
import com.bg.baseutillib.net.exception.ServerResponseFunc;
import com.bg.baseutillib.net.tools.OSSUploadUrlBean;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 文件上传类
 * 1、获取阿里云de上传与下载链接
 * 2、获取链接再调起阿里云的接口上传文件或下载文件
 */
public class FileUploadUtil {

    private static Context mContext;
    public static String mHostUrl;

    /**
     * 初始化
     *
     * @param context 上下文
     * @param hostUrl 域名路径
     */
    public static void init(Context context, String hostUrl) {
        mContext = context;
        mHostUrl = hostUrl;
    }

    /**
     * 获取阿里云OSS文件上传链接
     *
     * @param filePath 文件路径
     * @param callBack 回调
     */
    public static void startUploadFile(final String filePath, final RxNetCallback<OSSUploadUrlBean> callBack) {
        // TODO: 获取文件后缀名
        String suffix = FileUtils.getFileTypeForPath(filePath);//文件后缀
        final String contentType = URLConnection.getFileNameMap().getContentTypeFor(filePath);//文件类型

        NetworkRequest.getNetService(mContext, INetService.class, mHostUrl)
                .getOSSUploadUrl(suffix, contentType, SharedPreferencesUtil.readString("sessionId"))
                .map(new ServerResponseFunc<OSSUploadUrlBean>())
                .onErrorResumeNext(new HttpResponseFunc<OSSUploadUrlBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<OSSUploadUrlBean>(mContext, false) {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtil.showShortToast(e.getMessage());
                        if (callBack != null) {
                            callBack.onError(e);
                        }
                    }

                    @Override
                    public void onNext(OSSUploadUrlBean uploadUrlBean) {
                        if (uploadUrlBean != null && uploadUrlBean.getBussData() != null) {
                            UploadFile(filePath, contentType, uploadUrlBean, callBack);
                        }
                    }
                });
    }

    /**
     * 获得上传链接开始上传文件
     *
     * @param filePath
     * @param uploadUrlBean
     * @param callBack
     */
    private static void UploadFile(String filePath, String contentType, final OSSUploadUrlBean uploadUrlBean,
                                   final RxNetCallback<OSSUploadUrlBean> callBack) {
        File file = null;
        try {
            file = new Compressor(mContext).compressToFile(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: 执行
        RequestBody requestFile = RequestBody.create(MediaType.parse(contentType), file);
        String uploadUrl = uploadUrlBean.getBussData().getUploadUrl();

        int c = 0, n = 0;
        while (c < 3) {
            n = uploadUrl.indexOf("/", n + 1);
            c++;
        }
        NetworkRequest.getUploadNetService(mContext, INetService.class, uploadUrl.substring(0, n))
                .upLoadFile(contentType, uploadUrl.substring(n + 1), requestFile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(mContext, false) {
                    @Override
                    public void onError(ApiException e) {
                        if (callBack != null) {
                            callBack.onError(e);
                        }
                    }

                    @Override
                    public void onNext(String value) {
                        if (callBack != null) {
                            callBack.onSuccess(uploadUrlBean);
                        }
                    }
                });
    }
}
