package com.sdust.zhihudaily.welcome;

import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.data.model.StartImage;
import com.sdust.zhihudaily.data.source.Repository;
import com.sdust.zhihudaily.util.ImageLoaderUtils;

/**
 * Created by Kevin on 16/5/24.
 */
public class WelcomePresenter implements WelcomeContract.Presenter {

    private WelcomeContract.View mView;

    public WelcomePresenter(WelcomeContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadImage();
    }

    private void loadImage() {
        ZhiHuApplication.getRepository().getStartImage(mView.getWidth(), mView.getHeight(),
                ImageLoaderUtils.getImageOptions(), new Repository.Callback<StartImage>() {

                    @Override
                    public void success(StartImage image, boolean outDate) {
                        mView.showSuccessImage(image);
                    }

                    @Override
                    public void failure(Exception e) {
                        mView.showFailureImage();
                        e.printStackTrace();
                    }
                });

    }

}
