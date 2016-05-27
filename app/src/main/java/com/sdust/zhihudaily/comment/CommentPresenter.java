package com.sdust.zhihudaily.comment;

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

    }
}
