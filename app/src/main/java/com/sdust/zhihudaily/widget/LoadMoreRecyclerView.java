package com.sdust.zhihudaily.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.sdust.zhihudaily.util.LogUtils;


public class LoadMoreRecyclerView extends RecyclerView {
    private static final String TAG = LoadMoreRecyclerView.class.getSimpleName();
    private boolean mIsLoadingMore = false;
    private OnLoadMoreListener mListener;

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void init() {

        this.addOnScrollListener(new onLoadMoreScrollListener());
    }

    public static class onLoadMoreScrollListener extends OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LoadMoreRecyclerView view = (LoadMoreRecyclerView) recyclerView;
            OnLoadMoreListener onLoadMoreListener = view.getOnLoadMoreListener();

            onLoadMoreListener.onScrolled(recyclerView, dx, dy);

            LinearLayoutManager layoutManager = (LinearLayoutManager) view.getLayoutManager();
            int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
            int itemCount = layoutManager.getItemCount();
            if (lastVisibleItem >= itemCount - 1 && !view.getLoadingMore()) {
                onLoadMoreListener.onLoadMore();
                LogUtils.i(TAG, "load more: lastVisibleItem = " + lastVisibleItem + ", itemCount" + itemCount);
            } else {
                super.onScrolled(recyclerView, dx, dy);
            }
        }
    }


    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mListener = listener;
        init();
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return this.mListener;
    }

    public void setLoadingMore(boolean isLoading) {
        mIsLoadingMore = isLoading;
    }

    public boolean getLoadingMore() {
        return mIsLoadingMore;
    }

    public interface OnLoadMoreListener {
        public void onLoadMore();

        public void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof LinearLayoutManager) {
            super.setLayoutManager(layout);
        } else {
            throw new IllegalArgumentException("LoadMoreRecyclerView must have a LinearLayoutManager!");
        }
    }
}
