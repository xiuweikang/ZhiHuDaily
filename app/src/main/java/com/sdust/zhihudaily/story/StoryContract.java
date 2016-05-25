package com.sdust.zhihudaily.story;

import com.sdust.zhihudaily.base.BasePresenter;
import com.sdust.zhihudaily.base.BaseView;
import com.sdust.zhihudaily.data.model.Story;

/**
 * Created by Kevin on 16/5/25.
 */
public class StoryContract {

    interface Presenter extends BasePresenter{
        void refresh(String storyId);
    }
    interface View extends BaseView<Presenter> {
        void showStory(Story story);
        void showError();
    }
}
