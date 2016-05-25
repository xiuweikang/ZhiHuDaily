package com.sdust.zhihudaily.welcome;

import com.sdust.zhihudaily.base.BasePresenter;
import com.sdust.zhihudaily.base.BaseView;
import com.sdust.zhihudaily.data.model.StartImage;

/**
 * Created by Kevin on 16/5/24.
 */
public interface WelcomeContract {
    interface View extends BaseView<Presenter> {
        int getWidth();
        int getHeight();
        void showSuccessImage(StartImage image);
        void showFailureImage();
    }

    interface Presenter extends BasePresenter{

    }
}
