package com.sdust.zhihudaily.mainpage.theme;

import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.data.model.Theme;
import com.sdust.zhihudaily.data.source.Repository;

/**
 * Created by Kevin on 16/5/25.
 */
public class ThemePresenter implements ThemeContract.Presenter {

    private ThemeContract.View mView;

    public ThemePresenter(ThemeContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    public void refresh(String themeId) {
        mView.showLoadProgress();
        ZhiHuApplication.getRepository().getThemeStories(themeId, new Repository.Callback<Theme>() {
            @Override
            public void success(Theme theme, boolean outDate) {
                mView.setThemeStories(theme);
            }

            @Override
            public void failure(Exception e) {
                mView.showLoadError();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void loadMore(String themeId, String lastStoriesId) {
        ZhiHuApplication.getRepository().getBeforeThemeStories(themeId, lastStoriesId, new Repository.Callback<Theme>() {
            @Override
            public void success(Theme theme, boolean outDate) {
                mView.setMore(theme);
            }

            @Override
            public void failure(Exception e) {
                mView.showLoadMoreError();
                e.printStackTrace();
            }
        });
    }
}
