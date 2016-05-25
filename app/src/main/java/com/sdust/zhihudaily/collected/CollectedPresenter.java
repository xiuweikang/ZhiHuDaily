package com.sdust.zhihudaily.collected;

import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.collected.CollectedContract.Presenter;
import com.sdust.zhihudaily.data.model.Story;

import java.util.List;

/**
 * Created by Kevin on 16/5/25.
 */
public class CollectedPresenter implements Presenter{

    private CollectedContract.View mView;


    public CollectedPresenter(CollectedContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }
    @Override
    public void start() {

    }

    @Override
    public List<Story> getAllStory() {
        return ZhiHuApplication.getRepository().getAllCollectedStory();
    }
}
