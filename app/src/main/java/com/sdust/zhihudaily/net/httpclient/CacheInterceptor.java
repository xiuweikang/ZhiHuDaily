package com.sdust.zhihudaily.net.httpclient;


import com.sdust.zhihudaily.util.LogUtils;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * OkHttp的拦截器
 */
public class CacheInterceptor implements Interceptor {
    public static final String TAG = CacheInterceptor.class.getSimpleName();
    private static final String CACHE_CONTROL = "Cache-Control";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        LogUtils.d(TAG + "request",request.headers().toString());
        Response response = chain.proceed(request);
        LogUtils.d(TAG + "response",response.headers().toString());
        return response.newBuilder()
                .header(CACHE_CONTROL, "public, max-age=" + 60 * 60 * 24) // 缓存一天
                .build();
    }
}
