package com.sdust.zhihudaily.comment;

import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.data.model.Comments;
import com.sdust.zhihudaily.data.source.Repository;

/**
 * Created by Kevin on 16/5/26.
 */
public class CommentPresenter implements CommentContract.Presenter {


    private CommentContract.View mView;

    public CommentPresenter(CommentContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void refresh(String storyId) {
        mView.showProgress();
        ZhiHuApplication.getRepository().getLongComment(storyId, new Repository.Callback<Comments>() {
            @Override
            public void success(Comments comments, boolean outDate) {
                mView.showLongComment(comments);
                mView.hideProgress();
            }

            @Override
            public void failure(Exception e) {
                mView.showLongCommnetError();
                mView.hideProgress();
            }
        });
    }
}
