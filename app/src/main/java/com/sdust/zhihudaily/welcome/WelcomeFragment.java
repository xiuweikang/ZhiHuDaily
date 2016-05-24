package com.sdust.zhihudaily.welcome;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.data.model.StartImage;
import com.sdust.zhihudaily.util.ImageLoaderUtils;
import com.sdust.zhihudaily.util.SystemUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WelcomeFragment extends Fragment implements WelcomeContract.View {
    @InjectView(R.id.tv_author)
    TextView mAuthorView;

    @InjectView(R.id.img_start)
    ImageView mStartImg;

    private Animation mStartAnim;

    private WelcomeContract.Presenter mPresenter;

    private Context mContext;
    public static final String TAG = WelcomeFragment.class.getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mStartAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.start);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        mStartImg.startAnimation(mStartAnim);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mStartAnim = null;
    }


    public Animation getStartAnim() {
        return mStartAnim;
    }

    public ImageView getStartImg() {
        return mStartImg;
    }

    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public int getWidth() {
        return SystemUtils.getScreenWidth(mContext);
    }

    @Override
    public int getHeight() {
        return SystemUtils.getScreenWidth(mContext);

    }

    @Override
    public void showSuccessImage(StartImage image) {
        mAuthorView.setText(image.getText());
        ImageLoader.getInstance().displayImage(image.getImg(),
                mStartImg, ImageLoaderUtils.getImageOptions());
    }

    @Override
    public void showFailureImage() {
        mStartImg.setBackgroundResource(R.drawable.bg_splash);
        mAuthorView.setText("Kevin");
    }

}
