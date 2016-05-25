
package com.sdust.zhihudaily.net.api;


import com.google.gson.GsonBuilder;
import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.net.httpclient.OkHttp;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class ZhiHuApi {

    private static final String API = "http://news.at.zhihu.com/api/4";

    private static ZhiHuApiService dailyApiService;
    private RestAdapter mRetrofit;

    private ZhiHuApi() {
        mRetrofit = new RestAdapter.Builder()
                .setEndpoint(API)
                .setClient(new OkClient(OkHttp.createHttpClient(ZhiHuApplication.getContext())))
                //不导出实体中没有用@Expose注解的属性
                .setConverter(new GsonConverter(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .build();
    }

    public ZhiHuApiService createService() {
        return mRetrofit.create(ZhiHuApiService.class);
    }

    public static ZhiHuApiService createApi() {
        if (dailyApiService == null) {
            synchronized (ZhiHuApi.class) {
                if (dailyApiService == null) {
                    dailyApiService = new ZhiHuApi().createService();
                }
            }
        }
        return dailyApiService;
    }

}
