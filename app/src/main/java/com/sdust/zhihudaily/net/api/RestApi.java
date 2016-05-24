package com.sdust.zhihudaily.net.api;

import com.google.gson.GsonBuilder;
import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.net.httpclient.OkHttp;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;


public class RestApi {

    private static RestAdapter restAdapter;

    public static <T> T createApi(Class<T> clazz, String api) {
        if (restAdapter == null) {
            synchronized (RestApi.class) {
                if (restAdapter == null) {
                    restAdapter = new RestAdapter.Builder()
                            .setEndpoint(api)
                            .setClient(new OkClient(OkHttp.createHttpClient(ZhiHuApplication.getContext())))
                            //不导出实体中没有用@Expose注解的属性
                            .setConverter(new GsonConverter(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                            .build();
                }
            }
        }
        return restAdapter.create(clazz);
    }
}
