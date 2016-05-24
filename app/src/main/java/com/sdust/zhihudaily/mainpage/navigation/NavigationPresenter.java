package com.sdust.zhihudaily.mainpage.navigation;

import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.data.model.Themes;
import com.sdust.zhihudaily.data.source.Repository;

/**
 * Created by Kevin on 16/5/24.
 */
public class NavigationPresenter implements NavigationContract.Presenter {


    private NavigationContract.View mView;
    public NavigationPresenter(NavigationContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }
    @Override
    public void start() {
        refresh();
    }
    private void refresh() {

        ZhiHuApplication.getRepository().getThemes(new Repository.Callback<Themes>() {
            @Override
            public void success(Themes themes, boolean outDate) {
               mView.showThemes(themes);
            }

            @Override
            public void failure(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
