package com.sdust.zhihudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.adapter.holder.StoryViewHolder;
import com.sdust.zhihudaily.data.model.Story;

import java.util.ArrayList;
import java.util.List;


public class CollectedAdapter extends RecyclerView.Adapter {

    private List<Story> mStories  = new ArrayList<Story>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return  new StoryViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler_item_story, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StoryViewHolder storyViewHolder = (StoryViewHolder) holder;
        storyViewHolder.bindStoryView(mStories.get(position));
    }



    public void setStories(List<Story> stories) {
        mStories.clear();
        mStories.addAll(stories);
    }
    public void appendList(Story story) {
        mStories.add(story);
    }

    @Override
    public int getItemCount() {
        if(mStories != null)
            return mStories.size();
        return 0;
    }
}
