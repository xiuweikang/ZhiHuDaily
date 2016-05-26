package com.sdust.zhihudaily.story;

import com.sdust.zhihudaily.base.BasePresenter;
import com.sdust.zhihudaily.base.BaseView;
import com.sdust.zhihudaily.data.model.Story;
import com.sdust.zhihudaily.data.model.StoryExtra;

/**
 * Created by Kevin on 16/5/25.
 */
public interface StoryContract {

    interface Presenter extends BasePresenter{
        void refresh(String storyId);
        boolean isCollected(String storyId);
        void saveStory(Story story);
        void deleteStory(String storyId);
        void getStoryExtra(String storyId);
    }
    interface View extends BaseView<Presenter> {
        void showStory(Story story);
        void showError();
        void showStoryExtra(StoryExtra storyExtra);
        void showStoryExtraError();
    }
}
