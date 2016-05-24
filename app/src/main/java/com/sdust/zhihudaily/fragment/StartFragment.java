package com.sdust.zhihudaily.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.data.model.StartImage;
import com.sdust.zhihudaily.data.source.Repository;
import com.sdust.zhihudaily.util.LogUtils;
import com.sdust.zhihudaily.util.SystemUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StartFragment extends Fragment {
    @InjectView(R.id.tv_author)
    TextView mAuthorView;

    @InjectView(R.id.img_start)
    ImageView mStartImg;

    private int mHeight;
    private int mWidth;
    private Animation mStartAnim;

    private DisplayImageOptions mOptions;

    public static final String TAG = StartFragment.class.getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mStartAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.start);
        mWidth = SystemUtils.getScreenWidth(activity);
        mHeight = SystemUtils.getScreenHeight(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOptions = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisk(true).considerExifParams(true).build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this,view);
        mStartImg.startAnimation(mStartAnim);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadImage();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mStartAnim = null;
    }
    private void loadImage() {
        ZhiHuApplication.getRepository().getStartImage(mWidth, mHeight,
                mOptions, new Repository.Callback<StartImage>() {

                    @Override
                    public void success(StartImage image,boolean outDate) {
                        mAuthorView.setText(image.getText());

                        ImageLoader.getInstance().displayImage(image.getImg(),
                                mStartImg, mOptions);
                    }

                    @Override
                    public void failure(Exception e) {
                        LogUtils.i(TAG, "default image.");
                        mStartImg.setBackgroundResource(R.drawable.bg_splash);
                        mAuthorView.setText("Kevin");
                        e.printStackTrace();
                    }
                });

    }

    public Animation getStartAnim() {
        return mStartAnim;
    }

    public ImageView getStartImg() {
        return mStartImg;
    }
}
