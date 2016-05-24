package com.sdust.zhihudaily.welcome;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.util.IntentUtils;

/**
 * WelcomeActivity
 */
public class WelcomeActivity extends Activity {

    private WelcomeFragment mWelcomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider);

        start();
    }

    private void start() {
        FragmentManager fragmentManager = getFragmentManager();
        //屏幕旋转会导致Activity发生重新启动,Fragment会覆盖
        if (fragmentManager.findFragmentByTag(WelcomeFragment.TAG) == null) {
            mWelcomeFragment = new WelcomeFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.container, mWelcomeFragment, WelcomeFragment.TAG);
            transaction.commit();
        }
        new WelcomePresenter(mWelcomeFragment);
    }

    private Animation.AnimationListener mAnimationListener = new AnimationEndListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            mWelcomeFragment.getStartImg().setVisibility(View.GONE);
            intentToMainActivity();
        }
    };

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (mWelcomeFragment != null) {
            mWelcomeFragment.getStartAnim().setAnimationListener(
                    mAnimationListener);
        }

    }

    private void intentToMainActivity() {
        IntentUtils.intentToMainActivity(this);
    }

    class AnimationEndListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

    }
}


