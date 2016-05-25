package com.sdust.zhihudaily.mainpage.navigation;

import com.sdust.zhihudaily.base.BasePresenter;
import com.sdust.zhihudaily.base.BaseView;
import com.sdust.zhihudaily.data.model.Themes;

/**
 * Created by Kevin on 16/5/24.
 */
public interface NavigationContract {
    interface View extends BaseView<Presenter>{
        void showThemes(Themes themes);
    }

    interface Presenter extends BasePresenter{

    }
}
