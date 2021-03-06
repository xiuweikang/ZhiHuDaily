
package com.sdust.zhihudaily.data.source;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.sdust.zhihudaily.data.model.Comments;
import com.sdust.zhihudaily.data.model.DailyStories;
import com.sdust.zhihudaily.data.model.StartImage;
import com.sdust.zhihudaily.data.model.Story;
import com.sdust.zhihudaily.data.model.StoryExtra;
import com.sdust.zhihudaily.data.model.Theme;
import com.sdust.zhihudaily.data.model.Themes;
import com.sdust.zhihudaily.data.source.local.CacheRepositoryImp;
import com.sdust.zhihudaily.data.source.local.CacheRepository;
import com.sdust.zhihudaily.data.source.remote.NetRepositoryImp;
import com.sdust.zhihudaily.data.source.remote.NetRepository;

import java.util.List;

/**
 * 仓库的实现类
 */
public class RepositoryImp implements Repository {
    private static final String TAG = RepositoryImp.class.getSimpleName();
    private CacheRepository mCacheReImp;

    private NetRepository mNetReImp;

    private Context mContext;

    public RepositoryImp(Context context) {
        mContext = context;
        mCacheReImp = new CacheRepositoryImp(context);
        mNetReImp = new NetRepositoryImp();
    }

    @Override
    public void getStartImage(final int width, final int height, final DisplayImageOptions options, final Callback<StartImage> callback) {
        mCacheReImp.getStartImage(new CacheRepository.Callback<StartImage>() {

            @Override
            public void success(StartImage image) {
                callback.success(image, false);
            }

            @Override
            public void failure(Exception e) {
                callback.failure(e);
            }
        });

        mNetReImp.getStartImage(width, height, new NetRepository.Callback<StartImage>() {

            @Override
            public void success(StartImage startImage, String url) {
                mCacheReImp.saveStartImage(width, height, options, startImage);
            }

            @Override
            public void failure(Exception e, String url) {
                callback.failure(e);
            }
        });
    }

    @Override
    public void getThemes(final Callback<Themes> callback) {
        mNetReImp.getThemes(new NetRepository.Callback<Themes>() {
            @Override
            public void success(Themes themes, String url) {
                callback.success(themes, false);
                mCacheReImp.saveThemes(themes, url);
            }

            @Override
            public void failure(final Exception error, String url) {
                mCacheReImp.getThemes(url, new CacheRepository.Callback<Themes>() {
                    @Override
                    public void success(Themes themes) {
                        callback.success(themes, false);
                    }

                    @Override
                    public void failure(Exception e) {
                        callback.failure(error);
                    }
                });
            }
        });
    }

    @Override
    public void getLatestDailyStories(final Callback<DailyStories> callback) {
        //get data form network
        mNetReImp.getLatestDailyStories(new NetRepository.Callback<DailyStories>() {
            @Override
            public void success(DailyStories dailyStories, String url) {
                callback.success(dailyStories, false);
                mCacheReImp.saveLatestDailyStories(dailyStories, url);
            }

            @Override
            public void failure(final Exception error, String url) {
                mCacheReImp.getLatestDailyStories(url, new CacheRepository.Callback<DailyStories>() {
                    @Override
                    public void success(DailyStories dailyStories) {
                        callback.success(dailyStories, true);
                    }

                    @Override
                    public void failure(Exception e) {
                        callback.failure(error);
                    }
                });
            }
        });
    }


    @Override
    public void getBeforeDailyStories(String date, final Callback<DailyStories> callback) {
        mNetReImp.getBeforeDailyStories(date, new NetRepository.Callback<DailyStories>() {
            @Override
            public void success(DailyStories dailyStories, String url) {
                callback.success(dailyStories, false);
                mCacheReImp.saveBeforeDailyStories(dailyStories, url);
            }

            @Override
            public void failure(final Exception error, String url) {
                mCacheReImp.getBeforeDailyStories(url, new CacheRepository.Callback<DailyStories>() {
                    @Override
                    public void success(DailyStories dailyStories) {
                        callback.success(dailyStories, false);
                    }

                    @Override
                    public void failure(Exception e) {
                        callback.failure(error);
                    }
                });
            }
        });
    }

    @Override
    public void getThemeStories(String themeId, final Callback<Theme> callback) {
        mNetReImp.getThemeStories(themeId, new NetRepository.Callback<Theme>() {
            @Override
            public void success(Theme theme, String url) {
                callback.success(theme, false);
                mCacheReImp.saveThemeStories(theme, url);
            }

            @Override
            public void failure(final Exception error, String url) {
                mCacheReImp.getThemeStories(url, new CacheRepository.Callback<Theme>() {
                    @Override
                    public void success(Theme theme) {
                        callback.success(theme, true);
                    }

                    @Override
                    public void failure(Exception e) {
                        callback.failure(error);
                    }
                });
            }
        });
    }

    @Override
    public void getBeforeThemeStories(String themeId, String storyId, final Callback<Theme> callback) {
        mNetReImp.getBeforeThemeStories(themeId, storyId, new NetRepository.Callback<Theme>() {
            @Override
            public void success(Theme theme, String url) {
                callback.success(theme, false);
                mCacheReImp.saveBeforeThemesStories(theme, url);
            }

            @Override
            public void failure(final Exception error, String url) {
                mCacheReImp.getBeforeThemeStories(url, new CacheRepository.Callback<Theme>() {
                    @Override
                    public void success(Theme theme) {
                        callback.success(theme, true);
                    }

                    @Override
                    public void failure(Exception e) {
                        callback.failure(error);
                    }
                });
            }
        });
    }

    @Override
    public void getStoryDetail(String storyId, final Callback<Story> callback) {
        mNetReImp.getStoryDetail(storyId, new NetRepository.Callback<Story>() {
            @Override
            public void success(Story story, String url) {
                callback.success(story, false);
                mCacheReImp.saveStoryDetail(story, url);
            }

            @Override
            public void failure(final Exception error, String url) {
                mCacheReImp.getStoryDetail(url, new CacheRepository.Callback<Story>() {
                    @Override
                    public void success(Story story) {
                        callback.success(story, true);
                    }

                    @Override
                    public void failure(Exception e) {
                        callback.failure(error);
                    }
                });
            }
        });
    }

    @Override
    public void getStroyExtra(String storyId, final Callback<StoryExtra> callback) {
        mNetReImp.getStroyExtra(storyId, new NetRepository.Callback<StoryExtra>() {
            @Override
            public void success(StoryExtra storyExtra, String url) {
                callback.success(storyExtra, false);
                mCacheReImp.saveStoryExtra(storyExtra, url);
            }

            @Override
            public void failure(Exception e, String url) {
                mCacheReImp.getStroyExtra(url, new CacheRepository.Callback<StoryExtra>() {
                    @Override
                    public void success(StoryExtra storyExtra) {
                        callback.success(storyExtra, true);
                    }

                    @Override
                    public void failure(Exception e) {
                        callback.failure(e);
                    }
                });
            }
        });
    }

    @Override
    public void getLongComment(String storyId, final Callback<Comments> callback) {
        mNetReImp.getLongComment(storyId, new NetRepository.Callback<Comments>() {
            @Override
            public void success(Comments comment, String url) {
                callback.success(comment, false);
                mCacheReImp.saveCommnet(comment, url);
            }

            @Override
            public void failure(Exception e, String url) {
                mCacheReImp.getComment(url, new CacheRepository.Callback<Comments>() {
                    @Override
                    public void success(Comments comments) {
                        callback.success(comments, true);
                    }
                    @Override
                    public void failure(Exception e) {
                        callback.failure(e);
                    }
                });
            }
        });
    }

    @Override
    public void getShortComment(String storyId, final Callback<Comments> callback) {
        mNetReImp.getShortComment(storyId, new NetRepository.Callback<Comments>() {
            @Override
            public void success(Comments comment, String url) {
                callback.success(comment, false);
                mCacheReImp.saveCommnet(comment, url);
            }

            @Override
            public void failure(Exception e, String url) {
                mCacheReImp.getComment(url, new CacheRepository.Callback<Comments>() {
                    @Override
                    public void success(Comments comments) {
                        callback.success(comments, true);
                    }
                    @Override
                    public void failure(Exception e) {
                        callback.failure(e);
                    }
                });
            }
        });
    }

    @Override
    public void getLongCommentBefore(String storyId, String id, final Callback<Comments> callback) {
        mNetReImp.getLongCommentBefore(storyId,id, new NetRepository.Callback<Comments>() {
            @Override
            public void success(Comments comment, String url) {
                callback.success(comment, false);
                mCacheReImp.saveCommnet(comment, url);
            }

            @Override
            public void failure(Exception e, String url) {
                mCacheReImp.getComment(url, new CacheRepository.Callback<Comments>() {
                    @Override
                    public void success(Comments comments) {
                        callback.success(comments, true);
                    }
                    @Override
                    public void failure(Exception e) {
                        callback.failure(e);
                    }
                });
            }
        });
    }

    @Override
    public void getShortCommentBefore(String storyId, String id,final Callback<Comments> callback) {
        mNetReImp.getShortCommentBefore(storyId,id, new NetRepository.Callback<Comments>() {
            @Override
            public void success(Comments comment, String url) {
                callback.success(comment, false);
                mCacheReImp.saveCommnet(comment, url);
            }

            @Override
            public void failure(Exception e, String url) {
                mCacheReImp.getComment(url, new CacheRepository.Callback<Comments>() {
                    @Override
                    public void success(Comments comments) {
                        callback.success(comments, true);
                    }
                    @Override
                    public void failure(Exception e) {
                        callback.failure(e);
                    }
                });
            }
        });
    }

    @Override
    public boolean isCollected(String storyId) {
        return mCacheReImp.isCollected(storyId);
    }

    @Override
    public void saveStory(Story story) {
        mCacheReImp.saveStory(story);
    }

    @Override
    public List<Story> getAllCollectedStory() {
        return mCacheReImp.getAllCollectedStory();
    }

    @Override
    public void deleteCollected(String storyId) {
        mCacheReImp.deleteCollected(storyId);
    }


}
