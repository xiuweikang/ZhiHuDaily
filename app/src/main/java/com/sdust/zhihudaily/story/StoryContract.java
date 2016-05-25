package com.sdust.zhihudaily.story;

import com.sdust.zhihudaily.base.BasePresenter;
import com.sdust.zhihudaily.base.BaseView;

/**
 * Created by Kevin on 16/5/25.
 */
public class StoryContract {

    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView<Presenter> {

    }
}
