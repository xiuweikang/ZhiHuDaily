package com.sdust.zhihudaily.comment;

import android.text.TextUtils;

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
    public void getLongCommnet(String storyId) {
        mView.showLongProgress();
        ZhiHuApplication.getRepository().getLongComment(storyId, new Repository.Callback<Comments>() {
            @Override
            public void success(Comments comments, boolean outDate) {
                mView.showLongComment(comments);
                mView.hideProgress();
            }

            @Override
            public void failure(Exception e) {
                mView.showLongCommentError();
                mView.hideProgress();
            }
        });
    }

    @Override
    public void getShortComment(String storyId) {
        mView.showProgress();
        ZhiHuApplication.getRepository().getShortComment(storyId, new Repository.Callback<Comments>() {
            @Override
            public void success(Comments comments, boolean outDate) {
                mView.hideProgress();
                mView.showShortComment(comments);
            }

            @Override
            public void failure(Exception e) {
                mView.hideProgress();
                mView.showShortCommentError();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void getLongCommentBefore(String storyId, String id) {
        if(TextUtils.isEmpty(id)) {
            return;
        }
        ZhiHuApplication.getRepository().getLongCommentBefore(storyId, id, new Repository.Callback<Comments>() {
            @Override
            public void success(Comments comments, boolean outDate) {
                mView.showLongCommentMore(comments);
            }

            @Override
            public void failure(Exception e) {
                mView.showError();
            }
        });
    }

    @Override
    public void getShortCommentBefore(String storyId, String id) {
        if(TextUtils.isEmpty(id)) {
            return;
        }
        ZhiHuApplication.getRepository().getShortCommentBefore(storyId, id, new Repository.Callback<Comments>() {
            @Override
            public void success(Comments comments, boolean outDate) {
                mView.showShortCommentMore(comments);
            }

            @Override
            public void failure(Exception e) {
                mView.showError();
            }
        });
    }
}
