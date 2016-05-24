package com.sdust.zhihudaily.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;
import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.adapter.DailyStoriesAdapter;
import com.sdust.zhihudaily.adapter.holder.DateViewHolder;
import com.sdust.zhihudaily.data.model.DailyStories;
import com.sdust.zhihudaily.data.source.Repository;
import com.sdust.zhihudaily.util.LogUtils;
import com.sdust.zhihudaily.widget.LoadMoreRecyclerView;
import com.sdust.zhihudaily.widget.MyViewPager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DailyStoriesFragment extends BaseFragment {
    public static final String TAG = DailyStoriesFragment.class.getSimpleName();

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;

    private DailyStoriesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private String mDate;

    private boolean isDataLoaded;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DailyStoriesAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daily_stories, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this,view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        /**
         * 上拉加载更多，加载beforeDailyStories
         */
        mRecyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }



            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LogUtils.d(TAG,dy+"");
                changeActionBarTitle(dy);
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        /**
         * 下拉刷新，加载latestDailyStories
         */
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (!isDataLoaded) {
                    refresh();
                }
            }
        });
    }


    private String mTitle;
    private int lastTitlePos = -1;


    private void changeActionBarTitle(int dy) {
        int position = mLayoutManager.findFirstVisibleItemPosition();
        if (lastTitlePos == position) {
            return;
        }
        DailyStoriesAdapter.Item item = mAdapter.getItem(position);
        int type = item.getType();
        if (type == DailyStoriesAdapter.Type.TYPE_HEADER) {
            mTitle = getString(R.string.title_activity_main);
        } else if (dy > 0 && type == DailyStoriesAdapter.Type.TYPE_DATE) {
            mTitle = DateViewHolder.getDate(item.getDate(), getActivity());
        } else if (dy < 0) {
            mTitle = DateViewHolder.getDate(mAdapter.getTitleBeforePosition(position), getActivity());
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mTitle);
        lastTitlePos = position;
    }

    @Override
    public void onResume() {
        L.i(TAG, "onResume");
        super.onResume();
        if (mRecyclerView != null) {
            LogUtils.i(TAG, "recyclerView != null");
            View view = mRecyclerView.findViewById(R.id.viewPager);
            if (view != null) {
                LogUtils.i(TAG, "MyViewPager startAutoScroll");
                ((MyViewPager) view).startAutoScroll();
            }
        }
    }

    @Override
    public void onPause() {
        LogUtils.i(TAG, "onPause");
        super.onPause();
        if (mRecyclerView != null) {
            LogUtils.i(TAG, "recyclerView != null");
            View view = mRecyclerView.findViewById(R.id.viewPager);
            if (view != null) {
                LogUtils.i(TAG, "MyViewPager stopAutoScroll");
                ((MyViewPager) view).stopAutoScroll();
            }
        }
    }

    private void refresh() {
        isDataLoaded = false;
        mSwipeRefreshLayout.setRefreshing(true);
        ZhiHuApplication.getRepository().getLatestDailyStories(new Repository.Callback<DailyStories>() {
            @Override
            public void success(DailyStories dailyStories, boolean outDate) {
                isDataLoaded = true;
                mSwipeRefreshLayout.setRefreshing(false);
                mDate = dailyStories.getDate();
                mAdapter.setList(dailyStories);
            }

            @Override
            public void failure(Exception e) {
                isDataLoaded = false;
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "刷新失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    private void loadMore() {
        mRecyclerView.setLoadingMore(true);

        ZhiHuApplication.getRepository().getBeforeDailyStories(mDate, new Repository.Callback<DailyStories>() {
            @Override
            public void success(DailyStories dailyStories, boolean outDate) {
                /**
                 * 注意，若果需要查询 11 月 18 日的消息，before 后的数字应为 20131119
                 * 所以在加载前一日时，只需要mDate = dailyStories.getDate()
                 */
                mDate = dailyStories.getDate();
                mRecyclerView.setLoadingMore(false);
                mAdapter.appendList(dailyStories);
            }


            @Override
            public void failure(Exception e) {
                mRecyclerView.setLoadingMore(false);
                Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

}
