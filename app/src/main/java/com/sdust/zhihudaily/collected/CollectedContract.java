package com.sdust.zhihudaily.collected;

import com.sdust.zhihudaily.base.BasePresenter;
import com.sdust.zhihudaily.base.BaseView;
import com.sdust.zhihudaily.data.model.Story;

import java.util.List;

/**
 * Created by Kevin on 16/5/25.
 */
public interface CollectedContract {
    interface Presenter extends BasePresenter{
        List<Story> getAllStory();

    }
    interface View extends BaseView<Presenter> {

    }
}
