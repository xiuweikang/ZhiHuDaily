package com.sdust.zhihudaily.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.data.model.Comment;
import com.sdust.zhihudaily.widget.CircleBitmapDisplayer;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Kevin on 16/5/27.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.avatarView)
    ImageView mAvatarView;
    @InjectView(R.id.txt_name)
    TextView mTxtName;
    @InjectView(R.id.txt_vote_num)
    TextView mTxtVoteNum;
    @InjectView(R.id.txt_content)
    TextView mTxtContent;
    @InjectView(R.id.txt_reply)
    TextView mTxtReply;
    @InjectView(R.id.txt_time)
    TextView mTxtTime;
    @InjectView(R.id.txt_fold)
    TextView mTxtFold;
    private View mView;
    private DisplayImageOptions mOptions;
    public CommentViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        initView(itemView);
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.comment_avatar)
                .showImageForEmptyUri(R.drawable.comment_avatar)
                .showImageOnFail(R.drawable.comment_avatar)
                .displayer(new CircleBitmapDisplayer())
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    private void initView(View view) {
        ButterKnife.inject(this,view);
    }

    public void bindViewHolder(Comment comment) {
        ImageLoader.getInstance().displayImage(comment.getAvatar(), mAvatarView, mOptions);
        mTxtName.setText(comment.getAuthor());
        mTxtVoteNum.setText(comment.getLikes());
        mTxtContent.setText(comment.getContent());
        TextView txtReplyTo = new TextView(mTxtReply.getContext());
        txtReplyTo.setTextAppearance(mTxtReply.getContext(),R.style.TextViewBase_Main);
        txtReplyTo.setText("//" + comment.getReply().getAuthor());
        txt

    }
}
