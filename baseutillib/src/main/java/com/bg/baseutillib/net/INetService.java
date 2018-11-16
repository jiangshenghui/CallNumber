package com.bg.baseutillib.net;

import com.bg.baseutillib.net.base.BaseResponse;
import com.bg.baseutillib.net.tools.OSSUploadUrlBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 网络服务标记接口
 */

public interface INetService {

    /**
     * 文件下载
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String fileUrl);

    /**
     * 获取阿里云OSS文件上传链接
     *
     * @param suffix      文件后缀
     * @param contentType 文件类型
     * @return
     */
    @POST("/api/base/sys/app/getOSSUploadUrl")
    Observable<BaseResponse<OSSUploadUrlBean>> getOSSUploadUrl(@Query("suffix") String suffix,
                                                               @Query("contentType") String contentType,
                                                               @Query("sessionId") String sessionId);

    /**
     * 上传文件
     *
     * @return
     */
    @PUT
    Observable<String> upLoadFile(@Header("Content-Type") String contentType,
                                  @Url String url,
                                  @Body RequestBody description);
}
