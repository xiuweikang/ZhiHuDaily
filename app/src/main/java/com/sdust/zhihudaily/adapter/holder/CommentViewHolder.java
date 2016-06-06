package com.sdust.zhihudaily.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.data.model.Comment;
import com.sdust.zhihudaily.util.DateUtils;
import com.sdust.zhihudaily.util.TextFoldHelper;
import com.sdust.zhihudaily.widget.CircleBitmapDisplayer;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Kevin on 16/5/27.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder implements TextFoldHelper.TextFoldListener {

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
    private TextFoldHelper mFoldHelper;

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
        ButterKnife.inject(this, view);
        mFoldHelper = new TextFoldHelper(mTxtReply,2);
        mFoldHelper.setFoldListener(this);
        mTxtFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFoldHelper.flipFold(true);
            }
        });

    }

    public void bindViewHolder(Comment comment) {
        mTxtName.setText(comment.getAuthor());
        mTxtVoteNum.setText(comment.getLikes() + "");
        mTxtContent.setText(comment.getContent());
        if (comment.getReply() != null) {
            String replyContent = "//" + comment.getReply().getAuthor() + ": " + comment.getReply().getCotent();
            SpannableString span = new SpannableString(replyContent);
            int length =  (comment.getReply().getAuthor() == null ? 0 : comment.getReply().getAuthor().length()) + 3;
            span.setSpan(new TextAppearanceSpan(mView.getContext(), R.style.TextViewBase_Main), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mFoldHelper.setText(span, true);
        } else {
            mFoldHelper.setText("", true);
            mTxtReply.setVisibility(View.GONE);
        }
        mTxtTime.setText(DateUtils.getTime(comment.getTime() * 1000));
        mAvatarView.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(comment.getAvatar(), mAvatarView, mOptions);
    }

    @Override
    public void onFoldableChange(boolean foldable) {
        mTxtFold.setVisibility(foldable ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onFoldStatusChange(boolean fold) {
        if(fold) {
            mTxtFold.setText("展开");
        } else {
            mTxtFold.setText("收起");
        }
    }

    @Override
    public void onFolding(float interpolatedTime) {

    }

    @Override
    public void onExpanding(float interpolatedTime) {

    }
}
