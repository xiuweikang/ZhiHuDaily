package com.sdust.zhihudaily.comment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kevin on 16/5/26.
 */
public class CommentFragment extends Fragment implements CommentContract.View{

    private CommentContract.Presenter mPresenter;

    private static final String STORY_ID = "story_id";
    public static Fragment newInstance(String storyId) {

        Bundle bundle = new Bundle();
        bundle.putString(STORY_ID,storyId);
        Fragment fragment = new CommentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mStoryId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void setPresenter(CommentContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
