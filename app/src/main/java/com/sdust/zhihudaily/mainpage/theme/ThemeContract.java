package com.sdust.zhihudaily.mainpage.theme;

import com.sdust.zhihudaily.base.BasePresenter;
import com.sdust.zhihudaily.base.BaseView;
import com.sdust.zhihudaily.data.model.Theme;

/**
 * Created by Kevin on 16/5/24.
 */
public class ThemeContract {

    interface Presenter extends BasePresenter{
        void refresh(String themeId);
        void loadMore(String themeId,String lastStoriesId);
    }

    interface  View extends BaseView<Presenter> {
        void showLoadProgress();
        void setThemeStories(Theme theme);
        void showLoadError();
        void setMore(Theme theme);
        void showLoadMoreError();
    }
}
