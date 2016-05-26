package com.sdust.zhihudaily.comment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.base.BaseAppCompatActivity;

public class CommentActivity extends BaseAppCompatActivity {

    private static final String STORY_ID = "story_id";
    private static final String COMMENT_NUM = "comment_num";

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        mActionBarToolbar.getBackground().setAlpha(0xff);
        String storyId = intent.getStringExtra(STORY_ID);
        String storyNum = intent.getStringExtra(COMMENT_NUM);
        setTitle(storyNum + "条评论");
        if (savedInstanceState == null) {
            CommentFragment fragment = (CommentFragment) CommentFragment.newInstance(storyId);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().add(R.id.container, fragment);
            new CommentPresenter(fragment);
        }
    }

    public static void showCommentActivity(Context context, String storyId,String commentNum) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(STORY_ID, storyId);
        intent.putExtra(COMMENT_NUM,commentNum);
        context.startActivity(intent);
    }
}
