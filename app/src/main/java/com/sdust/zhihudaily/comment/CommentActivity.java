package com.sdust.zhihudaily.comment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.base.BaseAppCompatActivity;

public class CommentActivity extends BaseAppCompatActivity {

    private static final String STORY_ID = "story_id";
    private static final String LONG_COMMENT_NUM = "long_comment_num";
    private static final String SHORT_COMMENT_NUM = "short_comment_num";


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
        int longCommentNum = intent.getIntExtra(LONG_COMMENT_NUM,0);
        int shortCommentNum = intent.getIntExtra(SHORT_COMMENT_NUM,0);
        setTitle((longCommentNum + shortCommentNum) + "条评论");
        if (savedInstanceState == null) {
            CommentFragment fragment = (CommentFragment) CommentFragment.newInstance(storyId,longCommentNum,shortCommentNum);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().add(R.id.container, fragment);
            new CommentPresenter(fragment);
        }
    }

    public static void showCommentActivity(Context context, String storyId,int longNum,int shortNum) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(STORY_ID, storyId);
        intent.putExtra(LONG_COMMENT_NUM,longNum);
        intent.putExtra(SHORT_COMMENT_NUM,shortNum);
        context.startActivity(intent);
    }
}
