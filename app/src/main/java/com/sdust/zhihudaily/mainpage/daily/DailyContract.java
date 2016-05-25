package com.sdust.zhihudaily.mainpage.daily;

import com.sdust.zhihudaily.base.BasePresenter;
import com.sdust.zhihudaily.base.BaseView;
import com.sdust.zhihudaily.data.model.DailyStories;

/**
 * Created by Kevin on 16/5/24.
 */
public class DailyContract {

    interface Presenter extends BasePresenter{
        void refresh();
        void loadMore(String date);
    }

    interface View extends BaseView<Presenter> {
        void showLoadProgress();
        void setStories(DailyStories stories);
        void showLoadError();
        void setMore(DailyStories stories);
        void showLoadMoreError();
    }


}
