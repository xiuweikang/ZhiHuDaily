
package com.sdust.zhihudaily.net.api;


public class ZhiHuApi {

    private static final String API = "http://news.at.zhihu.com/api/4";

    private static ZhiHuApiService dailyApiService;

    public static ZhiHuApiService createApi() {
        if (dailyApiService == null) {
            synchronized (ZhiHuApi.class) {
                if (dailyApiService == null) {
                    dailyApiService = RestApi.createApi(ZhiHuApiService.class, API);
                }
            }
        }
        return dailyApiService;
    }

}
