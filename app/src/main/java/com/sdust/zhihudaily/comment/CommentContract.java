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
        void getLongCommentBefore(String storyId,String id);
        void getShortCommentBefore(String storyId,String id);

    }
    interface View extends BaseView<Presenter>{
        void showLongComment(Comments commnet);
        void showLongCommentMore(Comments comment);
        void showShortCommentMore(Comments comments);
        void showError();
        void showLongCommentError();
        void showShortComment(Comments comment);
        void showLongProgress();
        void showShortCommentError();
        void showProgress();
        void hideProgress();
    }
}
