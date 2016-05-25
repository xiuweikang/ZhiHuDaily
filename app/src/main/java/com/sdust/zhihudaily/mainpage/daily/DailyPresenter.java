package com.sdust.zhihudaily.mainpage.daily;

import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.data.model.DailyStories;
import com.sdust.zhihudaily.data.source.Repository;

/**
 * Created by Kevin on 16/5/24.
 */
public class DailyPresenter implements DailyContract.Presenter{

    private DailyContract.View mView;
    public DailyPresenter(DailyContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }
    @Override
    public void start() {
        refresh();
    }
    @Override
    public void refresh() {
        mView.showLoadProgress();
        ZhiHuApplication.getRepository().getLatestDailyStories(new Repository.Callback<DailyStories>() {
            @Override
            public void success(DailyStories dailyStories, boolean outDate) {
                mView.setStories(dailyStories);
            }

            @Override
            public void failure(Exception e) {
                mView.showLoadError();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void loadMore(String date) {
        ZhiHuApplication.getRepository().getBeforeDailyStories(date, new Repository.Callback<DailyStories>() {
            @Override
            public void success(DailyStories dailyStories, boolean outDate) {
                mView.setMore(dailyStories);
            }


            @Override
            public void failure(Exception e) {
                mView.showLoadMoreError();
                e.printStackTrace();
            }
        });
    }


}
