package com.bg.baseutillib.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.bg.baseutillib.net.tools.NetUtils;
import com.bg.baseutillib.tool.SharedPreferencesUtil;
import com.bg.baseutillib.tool.ToastUtil;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 网络请求辅助类
 */
public class NetworkRequest {

    private static OkHttpClient mOkHttpClient;

    private static Retrofit retrofit;
    private static Retrofit retrofitUpload;
    private static String cookieName;
    private static String cookieValue;


    /**
     * 清除Cookie，退出登录时调用
     */
    public static void resetCookie() {
        cookieName = "";
        cookieValue = "";
    }

    static {
        initOkHttpClient();
    }

    /**
     * 获取网络服务
     *
     * @return
     */
    public static <T> T getNetService(Context context, Class<T> service, String baseUrl) {
        return createApi(context, service, baseUrl, false);
    }

    /**
     * 获取上传网络服务
     *
     * @return
     */
    public static <T> T getUploadNetService(Context context, Class<T> service, String baseUrl) {
        return createApi(context, service, baseUrl, true);
    }

    /**
     * 根据传入的baseUrl和api创建retrofit
     *
     * @param clazz
     * @param baseUrl
     * @param <T>
     * @return
     */
    private static <T> T createApi(Context context, Class<T> clazz, String baseUrl, boolean isUpload) {
        if (!NetUtils.isNetworkAvailable(context)) {
            ToastUtil.showLongToast("网络异常，请检查网络");
        }

//        initOkHttpClient();
        if (retrofit == null && !isUpload) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(mOkHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 添加Retrofit到RxJava的转换器
                    .addConverterFactory(ScalarsConverterFactory.create())// 添加String转换器
                    .addConverterFactory(GsonConverterFactory.create())// 添加Gson转换器
                    .build();
        } else if (retrofitUpload == null && isUpload) {
            retrofitUpload = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(mOkHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 添加Retrofit到RxJava的转换器
                    .addConverterFactory(ScalarsConverterFactory.create())// 添加String转换器
                    .addConverterFactory(GsonConverterFactory.create())// 添加Gson转换器
                    .build();
            return retrofitUpload.create(clazz);
        } else if (retrofitUpload != null && isUpload) {
            return retrofitUpload.create(clazz);
        }

        return retrofit.create(clazz);
    }

    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {

        //初始化log拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (NetworkRequest.class) {
                if (mOkHttpClient == null) {
//                    //设置Http缓存，10M,  /data/data/xxx/cache=
//                    Cache cache = new Cache(new File(MyApplication.getContext()
//                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 10);

                    mOkHttpClient = new OkHttpClient.Builder()
//                            .cache(cache)
                            .addInterceptor(interceptor)// 拦截信息并打印出来
                            .retryOnConnectionFailure(true)//连接失败会重新连接
                            .connectTimeout(7, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
//                            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//配置
//                            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置

                            //添加cookie等固定参数到头部
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    if (TextUtils.isEmpty(cookieName) || TextUtils.isEmpty(cookieValue)) {
                                        cookieName = SharedPreferencesUtil.readCookieName();
                                        cookieValue = SharedPreferencesUtil.readCookieVaule();
                                    }


                                    Request request = chain.request();
                                    Response response;
                                    if (!TextUtils.isEmpty(cookieName) && !TextUtils.isEmpty(cookieValue)) {
                                        Log.d("jsh", "intercept :======== Cookie  " + cookieName + "=" + cookieValue);
                                        Request newRequest = request.newBuilder()
                                                .addHeader("Cookie", cookieName + "=" + cookieValue)
                                                .addHeader(cookieName,cookieValue)
//                                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                                                .addHeader("Connection", "keep-alive")
//                                                .method(request.method(), request.body())
//                                                .addHeader("Connection","close")
                                                .build();
                                        response = chain.proceed(newRequest);
                                    } else {
                                        response = chain.proceed(request);
                                    }
                                    return response;
                                }
                            })
                            .build();
                }
            }
        }
    }
}