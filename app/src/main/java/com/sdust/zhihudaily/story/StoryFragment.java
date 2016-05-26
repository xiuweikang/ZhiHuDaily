package com.sdust.zhihudaily.story;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.data.model.Editor;
import com.sdust.zhihudaily.data.model.Story;
import com.sdust.zhihudaily.data.model.StoryExtra;
import com.sdust.zhihudaily.util.IntentUtils;
import com.sdust.zhihudaily.util.LogUtils;
import com.sdust.zhihudaily.util.SharedPrefUtils;
import com.sdust.zhihudaily.util.WebUtils;
import com.sdust.zhihudaily.widget.AvatarsView;
import com.sdust.zhihudaily.widget.ScrollPullDownHelper;
import com.sdust.zhihudaily.widget.StoryHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Kevin on 2015/8/10.
 */
public class StoryFragment extends Fragment implements StoryContract.View {
    public static final String TAG = StoryFragment.class.getSimpleName();

    @InjectView(R.id.pb)
    ProgressBar progressBar;

    @InjectView(R.id.rl_container_header)
    RelativeLayout rlStoryHeader;

    @InjectView(R.id.scrollView)
    ScrollView scrollView;

    @InjectView(R.id.webViewContainer)
    LinearLayout llWebViewContainer;

    @InjectView(R.id.avatarsView)
    AvatarsView avatarsView;

    @InjectView(R.id.spaceView)
    View spaceView;//占位用的

    @InjectView(R.id.webview)
    WebView mWebView;//加载webview可能会造成OOM，所以将其设置为软引用，防止OOM

    private StoryHeaderView storyHeaderView;

    private DisplayImageOptions mOptions;

    private ScrollPullDownHelper mScrollPullDownHelper;

    private String mStoryId;

    private Story mStory;

    private Toolbar mActionBarToolbar;

    private boolean isCollected;

    private boolean mHasImage;

    boolean isNight;

    boolean mBackFromSetting;

    private StoryContract.Presenter mPresenter;

    public StoryFragment() {

    }

    /**
     * 无法通过重载构造器传递StoryId
     *
     * @param storyId
     * @return
     */
    public static StoryFragment newInstance(String storyId, String storyTitle, String storyImages, String storyMultipic) {
        StoryFragment fragment = new StoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntentUtils.EXTRA_STORY_ID, storyId);
        bundle.putString(IntentUtils.EXTRA_STORY_TITLE, storyTitle);
        bundle.putString(IntentUtils.EXTRA_STORY_IMAGES, storyImages);
        bundle.putString(IntentUtils.EXTRA_STORY_MULTIPIC, storyMultipic);

        fragment.setArguments(bundle);
        return fragment;
    }

    public void setToolBar(Toolbar toolbar) {
        this.mActionBarToolbar = toolbar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//设置含有选项按钮
        if (getArguments() != null) {
            mStoryId = getArguments().getString(IntentUtils.EXTRA_STORY_ID);
        }
        isNight = SharedPrefUtils.getIsNiaghtMode(getActivity());
        mBackFromSetting = false;
        mScrollPullDownHelper = new ScrollPullDownHelper();
        this.mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        storyHeaderView = StoryHeaderView.newInstance(container);
        avatarsView = (AvatarsView) inflater.inflate(R.layout.layout_avatars, container, false);
        return inflater.inflate(R.layout.fragment_story, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        scrollView.setOverScrollMode(ScrollView.OVER_SCROLL_NEVER);
        mPresenter.refresh(mStoryId);
        mPresenter.getStoryExtra(mStoryId);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mBackFromSetting && isNight != SharedPrefUtils.getIsNiaghtMode(getActivity())) {
            isNight = !isNight;
            bindWebView(mHasImage);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        mBackFromSetting = true;
    }

    private Menu mMenu;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_story, menu);
        MenuItem collectedMenu = menu.findItem(R.id.action_collect);
        isCollected = mPresenter.isCollected(mStoryId);
        if (isCollected) {
            collectedMenu.setIcon(R.drawable.collected);
        }
        mMenu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                if (mStory != null) {
                    IntentUtils.share(getActivity(), mStory);
                }
                break;
            case R.id.action_collect:
                if (!isCollected) {
                    insertCollectedDao();
                    item.setIcon(R.drawable.collected);
                    Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                    isCollected = true;
                    updateStrotyExtra();
                } else {
                    mPresenter.deleteStory(mStoryId);
                    item.setIcon(R.drawable.collect);
                    Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_SHORT).show();
                    isCollected = false;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateStrotyExtra() {
        MenuItem item = mMenu.findItem(R.id.action_collect);
        item.setTitle("xiu");
    }

    private void insertCollectedDao() {
        Story collected = new Story();
        collected.setId(mStoryId);
        collected.setTitle(getArguments().getString(IntentUtils.EXTRA_STORY_TITLE));
        List<String> imageUrlList = new ArrayList<String>();
        imageUrlList.add(getArguments().getString(IntentUtils.EXTRA_STORY_IMAGES));
        collected.setImages(imageUrlList);
        collected.setMultiPic(getArguments().getString(IntentUtils.EXTRA_STORY_MULTIPIC));
        mPresenter.saveStory(collected);
    }

    public void initWebView() {
        WebSettings settings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);//API大于19时，如果多张图片url相同时，只会加载一个IMAGE所以直接加载
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                }
            }
        });


    }


    private void bindData(Story story) {
        mHasImage = !TextUtils.isEmpty(story.getImage());
        bindHeaderView(mHasImage);
        bindAvatarsView();
        llWebViewContainer.post(new Runnable() {
            @Override
            public void run() {
                bindWebView(mHasImage);
            }
        });
    }

    private void bindHeaderView(boolean hasImage) {
        if (hasImage) {
            if (mActionBarToolbar != null) {
                mActionBarToolbar.getBackground().setAlpha(0);
            }
            spaceView.setVisibility(View.VISIBLE);
            rlStoryHeader.addView(storyHeaderView);
            storyHeaderView.bindData(mStory.getTitle(), mStory.getImageSource(), mStory.getImage(), mOptions);
        } else {
            spaceView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mActionBarToolbar.getHeight()));
        }
    }

    private void bindAvatarsView() {
        List<Editor> recommenders = mStory.getRecommenders();
        if (recommenders != null && recommenders.size() > 0) {
            avatarsView.setVisibility(View.VISIBLE);
            List<String> avatars = new ArrayList<>(recommenders.size());
            for (Editor editor : recommenders) {
                avatars.add(editor.getAvatar());
            }
            avatarsView.bindData(getString(R.string.avatar_title_referee), avatars);
        } else {
            avatarsView.setVisibility(View.GONE);
        }
    }

    private void bindWebView(boolean hasImage) {
        initWebView();

        if (TextUtils.isEmpty(mStory.getBody())) {

            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            mWebView.loadUrl(mStory.getShareUrl());
        } else {

            String data = WebUtils.BuildHtmlWithCss(mStory.getBody(), mStory.getCssList(), isNight);
            LogUtils.d(TAG, data);
            mWebView.loadDataWithBaseURL(null, data, WebUtils.MIME_TYPE, WebUtils.ENCODING, null);

            if (hasImage) {
                addSrollListener();
            }
        }
    }

    private void addSrollListener() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (!isAdded()) {
                    return;
                }
                changeToolbarAlpha();
            }
        });
    }


    private void changeToolbarAlpha() {

        int scrollY = scrollView.getScrollY();
        LogUtils.d(TAG, "scrolly " + scrollY + "");
        int storyHeaderViewHeight = getStoryHeaderViewHeight();
        LogUtils.d(TAG, "storyHeaderHeight" + storyHeaderViewHeight);
        int toolbarHeight = mActionBarToolbar.getHeight();
        LogUtils.d(TAG, "toolbarHeight" + toolbarHeight);
        float contentHeight = storyHeaderViewHeight - toolbarHeight;
        LogUtils.d(TAG, "ratio" + scrollY / contentHeight);
        float ratio = Math.min(scrollY / contentHeight, 1.0f);//设置toolbar的透明度 当scrool滑到HeaderViewHeight - toolbarHeight时 为1
        mActionBarToolbar.getBackground().setAlpha((int) (ratio * 0xFF));
        if (scrollY <= contentHeight) {
            mActionBarToolbar.setY(0f);//让其一直在顶部
            return;
        }

        if (scrollY + scrollView.getHeight() > llWebViewContainer.getMeasuredHeight() + storyHeaderViewHeight) {
            return;
        }

        boolean isPullingDown = mScrollPullDownHelper.onScrollChanged(scrollY);
        float toolBarPositionY = isPullingDown ? 0 : (contentHeight - scrollY);
        if (scrollY < storyHeaderViewHeight + toolbarHeight) {
            toolBarPositionY = storyHeaderViewHeight - scrollY - toolbarHeight;
        }

        mActionBarToolbar.setY(toolBarPositionY);
    }

    private int getStoryHeaderViewHeight() {
        return getResources().getDimensionPixelSize(R.dimen.view_header_story_height);
    }

    @Override
    public void setPresenter(StoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showStory(Story story) {
        if (getActivity() == null) {
            return;
        }
        progressBar.setVisibility(View.GONE);
        mStory = story;
        bindData(story);
    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(ZhiHuApplication.getContext(), "网络异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showStoryExtra(StoryExtra storyExtra) {
        updateMenu(storyExtra);
    }

    @Override
    public void showStoryExtraError() {
    }

    private void updateMenu(StoryExtra storyExtra) {
        String commentNum = storyExtra.getLongComment() + storyExtra.getShortComment() + "";
        TextView commentTxt = (TextView)mMenu.findItem(R.id.action_comment).getActionView().findViewById(R.id.value);
        commentTxt.setText(commentNum);
        TextView praiseTxt = (TextView)mMenu.findItem(R.id.action_praise).getActionView().findViewById(R.id.value);
        praiseTxt.setText(storyExtra.getPopularity() + "");
    }

}
