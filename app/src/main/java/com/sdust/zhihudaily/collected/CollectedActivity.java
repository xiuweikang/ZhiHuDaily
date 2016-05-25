package com.sdust.zhihudaily.collected;

import android.os.Bundle;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.base.BaseAppCompatActivity;

/**
 * Created by Kevin on 2015/8/10.
 */
public class CollectedActivity extends BaseAppCompatActivity {

    private static final String TAG = CollectedActivity.class.getSimpleName();

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_collected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            CollectedFragment fragment = new CollectedFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, TAG)
                    .commit();
            new CollectedPresenter(fragment);
        }
    }
}
