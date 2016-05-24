package com.sdust.zhihudaily.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.adapter.ThemeStoriesAdapter;
import com.sdust.zhihudaily.data.model.Theme;
import com.sdust.zhihudaily.data.source.Repository;
import com.sdust.zhihudaily.util.LogUtils;
import com.sdust.zhihudaily.widget.LoadMoreRecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Kevin on 2015/8/7.
 */
public class ThemeStoriesFragment extends BaseFragment {
    public static final  String TAG = ThemeStoriesFragment.class.getSimpleName();
    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;

    private ThemeStoriesAdapter mAdapter;

    private String mThemeId;

    private String mLastStoryId;

    private boolean isDataLoaded;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mAdapter = new ThemeStoriesAdapter();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(TAG, getThemeNumber() + " : " + getThemeId());
        return inflater.inflate(R.layout.fragment_theme_stories, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                loadMore();
            }

            @Override
            public void onScrolled(RecyclerView mRecyclerView, int dx, int dy) {

            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mThemeId = getThemeId();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (!isDataLoaded) {

                    refresh();
                }
            }
        });
    }
    private void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        ZhiHuApplication.getRepository().getThemeStories(mThemeId, new Repository.Callback<Theme>() {
            @Override
            public void success(Theme theme, boolean outDate) {
                isDataLoaded = true;
                mSwipeRefreshLayout.setRefreshing(false);
                if (theme != null && mAdapter != null) {
                    if (theme.getStories().size() > 0) {
                        mLastStoryId = theme.getStories().get(theme.getStories().size() - 1).getId();
                    }
                    LogUtils.i(TAG, "last story id: " + mLastStoryId);
                    mAdapter.setTheme(theme);
                }
            }

            @Override
            public void failure(Exception e) {
                isDataLoaded = false;
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "刷新错误", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }


    private void loadMore() {
        isDataLoaded = false;
        mRecyclerView.setLoadingMore(true);
        ZhiHuApplication.getRepository().getBeforeThemeStories(mThemeId, mLastStoryId, new Repository.Callback<Theme>() {
            @Override
            public void success(Theme theme, boolean outDate) {
                mRecyclerView.setLoadingMore(false);
                if (theme != null && mAdapter != null) {
                    if (theme.getStories().size() > 0) {
                        mLastStoryId = theme.getStories().get(theme.getStories().size() - 1).getId();
                        mAdapter.appendStories(theme.getStories());
                    }
                }
            }

            @Override
            public void failure(Exception e) {
                mRecyclerView.setLoadingMore(false);
                Toast.makeText(getActivity(), "加载错误", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }
}
