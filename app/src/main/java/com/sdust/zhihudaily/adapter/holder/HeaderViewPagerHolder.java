package com.sdust.zhihudaily.adapter.holder;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.data.model.Story;
import com.sdust.zhihudaily.util.IntentUtils;
import com.sdust.zhihudaily.widget.CirclePageIndicator;
import com.sdust.zhihudaily.widget.MyViewPager;
import com.sdust.zhihudaily.widget.StoryHeaderView;

import java.util.List;

/**
 * 实现Header的viewHolder
 * 同时实现一个HeaderPagerAdapter
 */
public class HeaderViewPagerHolder extends RecyclerView.ViewHolder {
    public static final String TAG = HeaderViewPagerHolder.class.getSimpleName();
    private MyViewPager viewPager;
    private CirclePageIndicator indicator;
    private PagerAdapter mPagerAdapter;

    public HeaderViewPagerHolder(View itemView, List<Story> stories) {
        super(itemView);
        viewPager = (MyViewPager) itemView.findViewById(R.id.viewPager);
        indicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);
        if(stories == null || stories.size() == 0)
            return;
        else if(stories.size() < 2) {//如果图片数量小于2的话，指示设置为不可见
            indicator.setVisibility(View.GONE);
        }
        mPagerAdapter = new HeaderPagerAdapter(stories);
    }

    public void bindHeaderView() {
        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(mPagerAdapter);
            indicator.setViewPager(viewPager);
        } else {
            mPagerAdapter.notifyDataSetChanged();
        }
    }
    private final static class HeaderPagerAdapter extends PagerAdapter {
        private List<Story> mStories;
        private DisplayImageOptions mOptions;

        private int mChildCount;

        public HeaderPagerAdapter(List<Story> stories) {
            mStories = stories;
            this.mOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .build();
        }

        @Override
        public int getCount() {
            return mStories == null ? 0 : mStories.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            StoryHeaderView storyHeaderView =  StoryHeaderView.newInstance(container);
            final Story story = mStories.get(position);
            storyHeaderView.bindData(story.getTitle(), story.getImageSource(), story.getImage(), mOptions);
            storyHeaderView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.intentToStoryActivity((Activity) v.getContext(), story);
                }
            });
            container.addView(storyHeaderView);
            return storyHeaderView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((StoryHeaderView) object);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            mChildCount = getCount();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
    }
    public boolean isAutoScrolling() {
        if (viewPager != null) {
            return viewPager.isAutoScrolling();
        }
        return false;
    }

    public void stopAutoScroll() {
        if (viewPager != null) {
            viewPager.stopAutoScroll();
        }
    }

    public void startAutoScroll() {
        if (viewPager != null) {
            viewPager.startAutoScroll();
        }
    }
}
