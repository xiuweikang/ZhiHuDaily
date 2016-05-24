package com.sdust.zhihudaily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sdust.zhihudaily.R;

/**
 * Created by Kevin on 2015/7/15.
 */
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
        setContentView(getContentViewLayoutId());
        mActionBarToolbar = (Toolbar) findViewById(R.id.actionbarToolbar);

        setupActionBar();
    }

    private void setupActionBar() {
        setSupportActionBar(mActionBarToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && !(this instanceof NavigationDrawerActivity)) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
