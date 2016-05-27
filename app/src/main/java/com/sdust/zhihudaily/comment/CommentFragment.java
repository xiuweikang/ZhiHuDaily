package com.sdust.zhihudaily.comment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.widget.LoadMoreRecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Kevin on 16/5/26.
 */
public class CommentFragment extends Fragment implements CommentContract.View {


    @InjectView(R.id.txt_long_comment_num)
    TextView mTxtLongCommentNum;
    @InjectView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;
    @InjectView(R.id.layout_empty)
    LinearLayout mLayoutEmpty;
    @InjectView(R.id.txt_short_comment_num)
    TextView mTxtShortCommentNum;
    @InjectView(R.id.img_fold)
    ImageView mImgFold;
    @InjectView(R.id.pb)
    ProgressBar mPb;
    private CommentContract.Presenter mPresenter;

    private static final String STORY_ID = "story_id";
    private static final String SHORT_COMMENT_NUM = "short_comment_num";
    private static final String LONG_COMMENT_NUM = "long_comment_num";

    public static Fragment newInstance(String storyId, int longNum, int shortNum) {

        Bundle bundle = new Bundle();
        bundle.putString(STORY_ID, storyId);
        bundle.putInt(SHORT_COMMENT_NUM, shortNum);
        bundle.putInt(LONG_COMMENT_NUM, longNum);
        Fragment fragment = new CommentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mStoryId;
    private int mShortCommentNum;
    private int mLongCommentNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getArguments());
    }

    private void init(Bundle bundle) {
        mStoryId = bundle.getString(STORY_ID);
        mShortCommentNum = bundle.getInt(SHORT_COMMENT_NUM, 0);
        mLongCommentNum = bundle.getInt(LONG_COMMENT_NUM, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.refresh(mStoryId);
    }

    @Override
    public void setPresenter(CommentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void showProgress() {
        mPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mPb.setVisibility(View.GONE);
    }
}
