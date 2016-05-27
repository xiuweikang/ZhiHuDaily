package com.sdust.zhihudaily.comment;

import com.sdust.zhihudaily.base.BasePresenter;
import com.sdust.zhihudaily.base.BaseView;

/**
 * Created by Kevin on 16/5/26.
 */
public interface CommentContract {

    interface Presenter extends BasePresenter {
        void refresh(String storyId);

    }
    interface View extends BaseView<Presenter>{
        void showProgress();
        void hideProgress();
    }
}
