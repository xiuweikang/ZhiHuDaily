package com.sdust.zhihudaily.comment;

import com.sdust.zhihudaily.base.BasePresenter;
import com.sdust.zhihudaily.base.BaseView;
import com.sdust.zhihudaily.data.model.Comments;

/**
 * Created by Kevin on 16/5/26.
 */
public interface CommentContract {

    interface Presenter extends BasePresenter {
        void getLongCommnet(String storyId);
        void getShortComment(String storyId);

    }
    interface View extends BaseView<Presenter>{
        void showLongComment(Comments commnet);
        void showLongCommentError();
        void showShortComment(Comments comment);
        void showProgress();
        void hideProgress();
    }
}
