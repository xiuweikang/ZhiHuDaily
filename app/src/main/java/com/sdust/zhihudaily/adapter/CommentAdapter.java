package com.sdust.zhihudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.adapter.holder.CommentViewHolder;
import com.sdust.zhihudaily.data.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 16/5/27.
 */
public class CommentAdapter extends RecyclerView.Adapter {

    private List<Comment> mCommentList;

    public CommentAdapter() {
        mCommentList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((CommentViewHolder)holder).bindViewHolder(mCommentList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }


    public void setCommentList(List<Comment> commentList) {
        mCommentList.clear();
        mCommentList.addAll(commentList);
        notifyDataSetChanged();
    }

    public void appendCommentList() {

    }
}
