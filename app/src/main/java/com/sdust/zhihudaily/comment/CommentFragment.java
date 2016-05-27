package com.sdust.zhihudaily.comment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.adapter.CommentAdapter;
import com.sdust.zhihudaily.data.model.Comment;
import com.sdust.zhihudaily.data.model.Comments;
import com.sdust.zhihudaily.widget.LoadMoreRecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentFragment extends Fragment implements CommentContract.View {


    @InjectView(R.id.txt_long_comment_num)
    TextView mTxtLongCommentNum;
    @InjectView(R.id.layout_empty)
    LinearLayout mLayoutEmpty;
    @InjectView(R.id.txt_short_comment_num)
    TextView mTxtShortCommentNum;
    @InjectView(R.id.img_fold)
    ImageView mImgFold;
    @InjectView(R.id.pb)
    ProgressBar mPb;
    @InjectView(R.id.recyclerView_long)
    LoadMoreRecyclerView mRecyclerViewLong;
    @InjectView(R.id.recyclerView_short)
    LoadMoreRecyclerView mRecyclerViewShort;
    @InjectView(R.id.layout_fold)
    LinearLayout mLayoutFold;
    private CommentContract.Presenter mPresenter;

    private static final String STORY_ID = "story_id";
    private static final String SHORT_COMMENT_NUM = "short_comment_num";
    private static final String LONG_COMMENT_NUM = "long_comment_num";

    public static CommentFragment newInstance(String storyId, int longNum, int shortNum) {

        Bundle bundle = new Bundle();
        bundle.putString(STORY_ID, storyId);
        bundle.putInt(SHORT_COMMENT_NUM, shortNum);
        bundle.putInt(LONG_COMMENT_NUM, longNum);
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mStoryId;
    private int mShortCommentNum;
    private int mLongCommentNum;
    private CommentAdapter mLongCommentAdapter;
    private CommentAdapter mShortCommentAdapter;

    private boolean isShortCommentLoad;
    private boolean isShorCommentFold;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle(getArguments());
    }

    private void getBundle(Bundle bundle) {
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
        initView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        mTxtLongCommentNum.setText(mLongCommentNum + "条长评");
        mTxtShortCommentNum.setText(mShortCommentNum + "条短评");
        LinearLayoutManager mLayoutManagerLong = new LinearLayoutManager(getActivity());
        LinearLayoutManager mLayoutManagerShort = new LinearLayoutManager(getActivity());
        mRecyclerViewLong.setLayoutManager(mLayoutManagerLong);
        mRecyclerViewShort.setLayoutManager(mLayoutManagerShort);
        mLongCommentAdapter = new CommentAdapter();
        mShortCommentAdapter = new CommentAdapter();
        mRecyclerViewLong.setAdapter(mLongCommentAdapter);
        mRecyclerViewShort.setAdapter(mShortCommentAdapter);
        isShortCommentLoad = false;
        if (mShortCommentNum == 0) {
            mLayoutFold.setOnClickListener(null);
        } else {
            mLayoutFold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isShortCommentLoad) {
                        mPresenter.getShortComment(mStoryId);
                    } else {
                        mImgFold.setSelected(isShorCommentFold);
                        mRecyclerViewShort.setVisibility(isShorCommentFold ? View.VISIBLE : View.GONE);
                        isShorCommentFold = !isShorCommentFold;
                    }
                }
            });
        }

        mRecyclerViewLong.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
        mRecyclerViewShort.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getLongCommnet(mStoryId);
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
    public void showLongComment(Comments comment) {
        if (comment == null || comment.getCommentList().size() == 0) {
            showLongCommentError();
            return;
        }
        List<Comment> commentList = comment.getCommentList();
        mLongCommentAdapter.setCommentList(commentList);
        mRecyclerViewLong.setVisibility(View.VISIBLE);
        mLayoutEmpty.setVisibility(View.GONE);

    }

    @Override
    public void showLongCommentError() {
        mLayoutEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showShortComment(Comments comment) {
        if (comment == null || comment.getCommentList().size() == 0) {
            return;
        }
        List<Comment> commentList = comment.getCommentList();
        mShortCommentAdapter.setCommentList(commentList);
        mRecyclerViewShort.setVisibility(View.VISIBLE);
        isShorCommentFold = false;
        isShortCommentLoad = true;
        mImgFold.setSelected(true);

    }

    @Override
    public void showLongProgress() {
        mRecyclerViewLong.setVisibility(View.GONE);
        mLayoutEmpty.setVisibility(View.VISIBLE);
        mPb.setVisibility(View.VISIBLE);

    }

    @Override
    public void showShortCommentError() {
        isShorCommentFold = true;
        isShortCommentLoad = false;
        mImgFold.setSelected(false);
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
