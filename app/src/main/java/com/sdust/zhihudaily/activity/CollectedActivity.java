package com.sdust.zhihudaily.activity;

import android.os.Bundle;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.fragment.CollectedFragment;

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
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CollectedFragment(), TAG)
                    .commit();
        }
    }
}
