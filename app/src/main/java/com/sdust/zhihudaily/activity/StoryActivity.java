package com.sdust.zhihudaily.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.fragment.StoryFragment;
import com.sdust.zhihudaily.util.IntentUtils;
import com.sdust.zhihudaily.util.LogUtils;

/**
 * Created by Kevin on 2015/8/8.
 */
public class StoryActivity extends  BaseAppCompatActivity {
    @Override
    protected int getContentViewLayoutId() {
        return  R.layout.activity_story;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(null);
        if (savedInstanceState == null) {
            String storyId = getIntent().getStringExtra(IntentUtils.EXTRA_STORY_ID);
            String storyTitle = getIntent().getStringExtra(IntentUtils.EXTRA_STORY_TITLE);
            String storyImages = getIntent().getStringExtra(IntentUtils.EXTRA_STORY_IMAGES);
            String storyMultipic = getIntent().getStringExtra(IntentUtils.EXTRA_STORY_MULTIPIC);

            StoryFragment storyFragment = StoryFragment.newInstance(storyId,storyTitle,storyImages,storyMultipic);
            storyFragment.setToolBar(mActionBarToolbar);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, storyFragment, StoryFragment.TAG)
                    .commit();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(StoryFragment.TAG);
        if (fm != null && fragment != null) {
            fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
        if (mActionBarToolbar != null) {
            mActionBarToolbar.getBackground().setAlpha(255);
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        LogUtils.i("StoryActivity", "onDestroy");
        super.onDestroy();
    }
}
