package com.sdust.zhihudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.adapter.holder.DateViewHolder;
import com.sdust.zhihudaily.adapter.holder.HeaderViewPagerHolder;
import com.sdust.zhihudaily.adapter.holder.StoryViewHolder;
import com.sdust.zhihudaily.data.model.DailyStories;
import com.sdust.zhihudaily.data.model.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2015/8/7.
 */
public class DailyStoriesAdapter extends RecyclerView.Adapter {
    public static final String TAG = DailyStoriesAdapter.class.getSimpleName();
    protected List<Item> mItems;
    protected List<Item> mTmpItem;

    public class Type {
        public static final int TYPE_HEADER = 0;
        public static final int TYPE_DATE = 1;
        public static final int TYPE_STORY = 2;
    }

    public DailyStoriesAdapter() {
        mItems = new ArrayList<Item>();
        mTmpItem = new ArrayList<Item>();
    }

    public void setList(DailyStories dailyStories) {
        mItems.clear();
        appendList(dailyStories);
    }

    public void appendList(DailyStories dailyStories) {
        int positionStart = mItems.size();

        if (positionStart == 0) {
            Item headerItem = new Item();
            headerItem.setType(Type.TYPE_HEADER);
            headerItem.setStories(dailyStories.getTopStories());
            mItems.add(headerItem);
        }
        Item dateItem = new Item();
        dateItem.setType(Type.TYPE_DATE);
        dateItem.setDate(dailyStories.getDate());
        mItems.add(dateItem);
        List<Story> stories = dailyStories.getStories();
        for (int i = 0, num = stories.size(); i < num; i++) {
            Item storyItem = new Item();
            storyItem.setType(Type.TYPE_STORY);
            storyItem.setStory(stories.get(i));
            mItems.add(storyItem);
        }

        int itemCount = mItems.size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(positionStart, itemCount);
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof HeaderViewPagerHolder) {
            HeaderViewPagerHolder headerHolder = (HeaderViewPagerHolder) holder;
            if (headerHolder.isAutoScrolling()) {
                headerHolder.stopAutoScroll();
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof HeaderViewPagerHolder) {
            HeaderViewPagerHolder headerHolder = (HeaderViewPagerHolder) holder;
            if (!headerHolder.isAutoScrolling()) {
                headerHolder.startAutoScroll();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case Type.TYPE_HEADER:
                itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.recycler_header_viewpager, parent, false);
                return new HeaderViewPagerHolder(itemView, mItems.get(0).getStories());
            case Type.TYPE_DATE:
                itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.recycler_item_date, parent, false);
                return new DateViewHolder(itemView);
            case Type.TYPE_STORY:
                itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.recycler_item_story, parent, false);
                return new StoryViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Item item = mItems.get(position);
        switch (viewType) {
            case Type.TYPE_HEADER:
                ((HeaderViewPagerHolder) holder).bindHeaderView();
                break;
            case Type.TYPE_DATE:
                ((DateViewHolder) holder).bindDateView(item.getDate());
                break;
            case Type.TYPE_STORY:
                ((StoryViewHolder) holder).bindStoryView(item.getStory());
                break;
        }
    }

    public Item getItem(int position) {
        return mItems.get(position);
    }

    public String getTitleBeforePosition(int position) {
        mTmpItem.clear();
        mTmpItem.addAll(mItems.subList(0, position + 1));
        for(int i = mTmpItem.size() - 1; i >= 0; i--) {
            Item item = mTmpItem.get(i);
            if(item.getType() == Type.TYPE_DATE) {
                return item.getDate();
            }
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class Item {
        private int type;
        private String date;
        private Story story;
        private List<Story> stories;//Header包含的几个滚动的文章

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Story getStory() {
            return story;
        }

        public void setStory(Story story) {
            this.story = story;
        }

        public List<Story> getStories() {
            return stories;
        }

        public void setStories(List<Story> stories) {
            this.stories = stories;
        }
    }
}
