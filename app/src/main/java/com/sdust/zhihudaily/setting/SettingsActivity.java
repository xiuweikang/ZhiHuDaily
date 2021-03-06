package com.sdust.zhihudaily.setting;

import android.os.Bundle;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.base.BaseAppCompatActivity;

/**
 * Created by Kevin on 2015/7/25
 * 设置界面.
 */
public class SettingsActivity extends BaseAppCompatActivity {

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBarToolbar.getBackground().setAlpha(0xff);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new SettingFragment())
                    .commit();
        }
    }
}
