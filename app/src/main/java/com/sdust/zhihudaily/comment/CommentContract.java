package com.sdust.zhihudaily.comment;

import com.sdust.zhihudaily.base.BasePresenter;
import com.sdust.zhihudaily.base.BaseView;
import com.sdust.zhihudaily.data.model.Comments;

/**
 * Created by Kevin on 16/5/26.
 */
public interface CommentContract {

    interface Presenter extends BasePresenter {
        void refresh(String storyId);

    }
    interface View extends BaseView<Presenter>{
        void showLongComment(Comments commnet);
        void showLongCommnetError();
        void showProgress();
        void hideProgress();
    }
}
