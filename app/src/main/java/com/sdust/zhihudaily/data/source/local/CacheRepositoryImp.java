
package com.sdust.zhihudaily.data.source.local;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.sdust.zhihudaily.data.model.StoryExtra;
import com.sdust.zhihudaily.data.source.local.db.CacheDao;
import com.sdust.zhihudaily.data.model.Cache;
import com.sdust.zhihudaily.data.model.DailyStories;
import com.sdust.zhihudaily.data.model.StartImage;
import com.sdust.zhihudaily.data.model.Story;
import com.sdust.zhihudaily.data.model.Theme;
import com.sdust.zhihudaily.data.model.Themes;
import com.sdust.zhihudaily.data.source.local.db.CollectedDao;
import com.sdust.zhihudaily.util.LogUtils;
import com.sdust.zhihudaily.util.SharedPrefUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class CacheRepositoryImp implements CacheRepository {

    private static final String TAG = CacheRepositoryImp.class.getSimpleName();

    private static DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private CacheDao mCacheDao;
    private CollectedDao mCollectedDao;
    private Context mContext;

    private Gson mGson;

    public CacheRepositoryImp(Context context) {
        this.mContext = context;
        this.mCacheDao = new CacheDao(context);
        this.mCollectedDao = new CollectedDao(context);
        this.mGson = new Gson();
    }

    @Override
    public void getStartImage(Callback<StartImage> callback) {
        String startOldJsonStr = SharedPrefUtils.getStartJson(mContext);
        if (!TextUtils.isEmpty(startOldJsonStr)) {
            StartImage startImage = new Gson().fromJson(startOldJsonStr,
                    StartImage.class);
            callback.success(startImage);
        } else {
            callback.failure(getException(StartImage.class));
        }
    }

    @Override
    public void saveStartImage(int width, int height,
                               DisplayImageOptions options, StartImage startImage) {
        String oldJsonStr = SharedPrefUtils.getStartJson(mContext);
        StartImage old = new Gson().fromJson(oldJsonStr, StartImage.class);

        if (old == null || !startImage.getImg().equals(old.getImg())) {
            SharedPrefUtils.setStartJson(mContext,
                    new Gson().toJson(startImage));
            ImageLoader.getInstance().loadImage(startImage.getImg(),
                    new ImageSize(width, height), options, null);
        }
    }

    @Override
    public void getThemes(String url, Callback<Themes> callback) {
        getDataObject(url, Themes.class, callback);
    }

    @Override
    public void saveThemes(Themes themes, String url) {
        saveCacheToDB(themes, url);
    }

    @Override
    public void getLatestDailyStories(String url, Callback<DailyStories> callback) {
        getDataObject(url, DailyStories.class, callback);
    }

    @Override
    public void saveLatestDailyStories(DailyStories dailyStories, String url) {
        saveCacheToDB(dailyStories, url);
    }

    @Override
    public void getBeforeDailyStories(String url, Callback<DailyStories> callback) {
        getDataObject(url, DailyStories.class, callback);
    }

    @Override
    public void saveBeforeDailyStories(DailyStories dailyStories, String url) {
        saveCacheToDB(dailyStories, url);
    }

    @Override
    public void getThemeStories(String url, Callback<Theme> callback) {
        getDataObject(url, Theme.class, callback);
    }

    @Override
    public void saveThemeStories(Theme theme, String url) {
        saveCacheToDB(theme, url);
    }

    @Override
    public void getBeforeThemeStories(String url, Callback<Theme> callback) {
        getDataObject(url, Theme.class, callback);
    }

    @Override
    public void saveBeforeThemesStories(Theme theme, String url) {
        saveCacheToDB(theme, url);
    }

    private <T> void getDataObject(String url, Class<T> classOfT, CacheRepository.Callback callback) {
        String json = mCacheDao.getCache(url).getResponse();
        T t = mGson.fromJson(json, classOfT);
        if (t != null) {
            callback.success(t);
            LogUtils.i(TAG, "get" + classOfT.getSimpleName() + " cache");
        } else {
            callback.failure(getException(classOfT));
        }
    }

    private Exception getException(Class clazz) {
        return new Exception(clazz.getSimpleName() + " cache not found!");
    }

    private void saveCacheToDB(Object o, String url) {
        Cache cache = new Cache(url, mGson.toJson(o), Long.valueOf(df.format(Calendar.getInstance().getTimeInMillis())));
        mCacheDao.updateCache(cache);
    }



    @Override
    public void getStoryDetail(String url, Callback<Story> callback) {
        getDataObject(url, Story.class, callback);
    }
    @Override
    public void saveStoryDetail(Story story, String url) {
        saveCacheToDB(story, url);
    }

    @Override
    public void getStroyExtra(String url, Callback<StoryExtra> callback) {
        getDataObject(url,StoryExtra.class,callback);
    }

    @Override
    public void saveStoryExtra(StoryExtra story, String url) {
        saveCacheToDB(story,url);

    }

    @Override
    public boolean isCollected(String storyId) {
        return mCollectedDao.exists(storyId);
    }

    @Override
    public void saveStory(Story story) {
        mCollectedDao.insertCollected(story);
    }

    @Override
    public List<Story> getAllCollectedStory() {
        return mCollectedDao.getAllCollected();
    }

    @Override
    public void deleteCollected(String storyId) {
        mCollectedDao.deleteCollected(storyId);
    }

}
