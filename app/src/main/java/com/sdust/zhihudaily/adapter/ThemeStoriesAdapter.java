package com.sdust.zhihudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.adapter.holder.StoryViewHolder;
import com.sdust.zhihudaily.data.model.Editor;
import com.sdust.zhihudaily.data.model.Story;
import com.sdust.zhihudaily.data.model.Theme;
import com.sdust.zhihudaily.util.DensityUtils;
import com.sdust.zhihudaily.widget.AvatarsView;
import com.sdust.zhihudaily.widget.StoryHeaderView;

import java.util.ArrayList;
import java.util.List;


public class ThemeStoriesAdapter extends RecyclerView.Adapter {

    public static final class Type {
        public static final int TYPE_HEADER = 0;

        public static final int TYPE_AVATARS = 1;

        public static final int TYPE_ITEM = 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(mTheme.getBackground()) && position == 0) {
            return Type.TYPE_HEADER;
        } else if (mTheme.getEditors() != null && mTheme.getEditors().size() > 0 && position == 1) {
            return Type.TYPE_AVATARS;
        } else {
            return Type.TYPE_ITEM;
        }
    }

    private Theme mTheme;

    public ThemeStoriesAdapter() {
    }

    public void setTheme(Theme theme) {
        mTheme = theme;
        notifyDataSetChanged();
    }

    public void appendStories(List<Story> stories) {
        mTheme.getStories().addAll(stories);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case Type.TYPE_HEADER:
                viewHolder = new HeaderViewHolder(StoryHeaderView.newInstance(parent));
                break;
            case Type.TYPE_AVATARS:
                AvatarsView avatarsView = new AvatarsView(parent.getContext());
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, DensityUtils.dipToPx(parent.getContext(), 8), 0, 0);
                avatarsView.setLayoutParams(lp);
                viewHolder = new AvatarViewHolder(avatarsView);
                break;
            case Type.TYPE_ITEM:
                viewHolder = new StoryViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.recycler_item_story, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case Type.TYPE_HEADER:
                ((StoryHeaderView) holder.itemView)
                        .bindData(mTheme.getDescription(), null, mTheme.getBackground());
                break;
            case Type.TYPE_AVATARS:
                List<String> images = new ArrayList<>();
                for (Editor editor : mTheme.getEditors()) {
                    images.add(editor.getAvatar());
                }
                ((AvatarsView) holder.itemView).bindData(holder.itemView.getResources().getString(R.string.avatar_title_editor), images);
                break;
            case Type.TYPE_ITEM:
                StoryViewHolder storyViewHolder = (StoryViewHolder) holder;
                storyViewHolder.bindStoryView(mTheme.getStories().get(position - 2));
                break;
            default:
                throw new IllegalArgumentException("error view type!");
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mTheme != null) {
            if (!TextUtils.isEmpty(mTheme.getBackground())) {
                count += 1;
            }
            if (mTheme.getEditors() != null && mTheme.getEditors().size() > 0) {
                count += 1;
            }
            if (mTheme.getStories() != null) {
                count = count + mTheme.getStories().size();
            }
        }
        return count;
    }

    public static final class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static final class AvatarViewHolder extends RecyclerView.ViewHolder {

        public AvatarViewHolder(View itemView) {
            super(itemView);
        }
    }

}
