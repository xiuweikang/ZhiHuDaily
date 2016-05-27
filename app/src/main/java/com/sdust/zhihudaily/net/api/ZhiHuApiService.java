package com.sdust.zhihudaily.net.api;


import com.sdust.zhihudaily.data.model.Comments;
import com.sdust.zhihudaily.data.model.DailyStories;
import com.sdust.zhihudaily.data.model.StartImage;
import com.sdust.zhihudaily.data.model.Story;
import com.sdust.zhihudaily.data.model.StoryExtra;
import com.sdust.zhihudaily.data.model.Theme;
import com.sdust.zhihudaily.data.model.Themes;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;


public interface ZhiHuApiService {

    @GET("/start-image/{width}*{height}")
    void getStartImage(@Path("width") int width, @Path("height") int height, retrofit.Callback<StartImage> callback);

    @GET("/themes")
    void getThemes(retrofit.Callback<Themes> callback);

    @GET("/news/latest")
    void getLatestDailyStories(Callback<DailyStories> callback);

    @GET("/news/before/{date}")
    void getBeforeDailyStories(@Path("date") String date, Callback<DailyStories> callback);

    @GET("/theme/{themeId}")
    void getThemeStories(@Path("themeId") String themeId, Callback<Theme> callback);

    @GET("/theme/{themeId}/before/{storyId}")
    void getBeforeThemeStories(@Path("themeId") String themeId, @Path("storyId") String storyId, Callback<Theme> callback);

    @GET("/news/{storyId}")
    void getStoryDetail(@Path("storyId") String storyId, Callback<Story> callback);

    @GET("/story-extra/{storyId}")
    void getStoryExtra(@Path("storyId") String storyId,Callback<StoryExtra> callback);

    @GET("/story/{storyId}/long-comments")
    void getLongComment(@Path("storyId") String storyId, Callback<Comments> callback);

    @GET("/story/{storyId}/long-comments/before/{id}")
    void getLongCommentBefore(@Path("storyId") String storyId, @Path("id") String id, Callback<Comments> callback);

    @GET("/story/{storyId}/short-comments")
    void getShortComment(@Path("storyId") String storyId, Callback<Comments> callback);

    @GET("/story/{storyId}/short-comments/before/{id}")
    void getShortCommentBefore(@Path("storyId") String storyId, @Path("id") String id, Callback<Comments> callback);

}
