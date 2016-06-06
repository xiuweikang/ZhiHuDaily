package com.sdust.zhihudaily.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.collected.CollectedActivity;
import com.sdust.zhihudaily.setting.SettingsActivity;
import com.sdust.zhihudaily.util.SharedPrefUtils;


public abstract class BaseAppCompatActivity extends AppCompatActivity {
    public Toolbar mActionBarToolbar;

    /**
     * 子类覆写返回layoutId
     * @return
     */
    protected abstract int getContentViewLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(SharedPrefUtils.getTheme());
        setContentView(getContentViewLayoutId());
        mActionBarToolbar = (Toolbar) findViewById(R.id.actionbarToolbar);
        setupActionBar();
    }

    private void setupActionBar() {
        setSupportActionBar(mActionBarToolbar);
        ActionBar actionBar = getSupportActionBar();
        Log.d("actionbar","setting");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }else if(id == R.id.action_collected){
            startActivity(new Intent(this,CollectedActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
