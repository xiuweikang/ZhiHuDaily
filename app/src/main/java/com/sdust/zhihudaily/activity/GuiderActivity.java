package com.sdust.zhihudaily.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.fragment.StartFragment;
import com.sdust.zhihudaily.util.IntentUtils;
import com.sdust.zhihudaily.Listener.AnimationEndListener;

/**
 * GuiderActivity
 */
public class GuiderActivity extends Activity {

	private StartFragment mStartFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guider);

		start();
	}

	private void start() {
		FragmentManager fragmentManager = getFragmentManager();
		//屏幕旋转会导致Activity发生重新启动,Fragment会覆盖
		if(fragmentManager.findFragmentByTag(StartFragment.TAG) == null) {
			mStartFragment = new StartFragment();
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.add(R.id.container, mStartFragment, StartFragment.TAG);
			transaction.commit();
		}
	}

	private Animation.AnimationListener mAnimationListener = new AnimationEndListener() {
		@Override
		public void onAnimationEnd(Animation animation) {
			mStartFragment.getStartImg().setVisibility(View.GONE);
			intentToMainActivity();
		}
	};

	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		if (mStartFragment != null) {
			mStartFragment.getStartAnim().setAnimationListener(
					mAnimationListener);
		}

	}

	private void intentToMainActivity() {
		IntentUtils.intentToMainActivity(this);
	}



}
