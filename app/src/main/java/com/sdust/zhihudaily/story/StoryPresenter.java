package com.sdust.zhihudaily.story;

import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.data.model.Story;
import com.sdust.zhihudaily.data.model.StoryExtra;
import com.sdust.zhihudaily.data.source.Repository;

/**
 * Created by Kevin on 16/5/25.
 */
public class StoryPresenter implements StoryContract.Presenter {

    private StoryContract.View mView;
    public StoryPresenter(StoryContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void start() {
    }

    @Override
    public void refresh(String storyId) {
        ZhiHuApplication.getRepository().getStoryDetail(storyId, new Repository.Callback<Story>() {
            @Override
            public void success(Story story, boolean outDate) {
                mView.showStory(story);
            }

            @Override
            public void failure(Exception e) {
                mView.showError();
                e.printStackTrace();
            }
        });
    }


    @Override
    public boolean isCollected(String storyId) {
        return ZhiHuApplication.getRepository().isCollected(storyId);
    }

    @Override
    public void saveStory(Story story) {
        ZhiHuApplication.getRepository().saveStory(story);
    }

    @Override
    public void deleteStory(String storyId) {
        ZhiHuApplication.getRepository().deleteCollected(storyId);
    }

    @Override
    public void getStoryExtra(String storyId) {
        ZhiHuApplication.getRepository().getStroyExtra(storyId, new Repository.Callback<StoryExtra>() {
            @Override
            public void success(StoryExtra storyExtra, boolean outDate) {
                mView.showStoryExtra(storyExtra);
            }

            @Override
            public void failure(Exception e) {
                mView.showStoryExtraError();
                e.printStackTrace();
            }
        });
    }
}
