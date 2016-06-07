package com.sdust.zhihudaily.net.httpclient;

import android.content.Context;

import com.sdust.zhihudaily.Constants;
import com.sdust.zhihudaily.util.FileUtils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;


/**
 * OkHttp的工厂类
 */
public class OkHttp {
    private static OkHttpClient okHttpClient;

    public static OkHttpClient createHttpClient(Context context) {
        if (okHttpClient == null) {
            synchronized (OkHttp.class) {
                okHttpClient = new OkHttpClient();
                okHttpClient.setCache(new Cache(FileUtils.getHttpCacheDir(context), Constants.HTTP_CACHE_SIZE));
                okHttpClient.setConnectTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
                okHttpClient.setReadTimeout(Constants.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
                okHttpClient.networkInterceptors().add(new CacheInterceptor());
            }
        }
        return okHttpClient;
    }
}
