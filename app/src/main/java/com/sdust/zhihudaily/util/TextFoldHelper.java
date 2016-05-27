package com.sdust.zhihudaily.util;

import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.TextView;

/**
 * TextView折叠/展开逻辑
 * <p/>
 */
public class TextFoldHelper implements View.OnClickListener {

    public interface TextFoldListener {
        /**
         * @param foldable TextView是否可折叠。可在此回调中隐藏或显示折叠/展开按钮
         */
        void onFoldableChange(boolean foldable);

        /**
         * 文本折叠状态改变
         *
         * @param fold 文本是否被折叠
         */
        void onFoldStatusChange(boolean fold);

        /**
         * 平滑折叠
         *
         * @param interpolatedTime 动画进度，0~1
         */
        void onFolding(float interpolatedTime);

        /**
         * 平滑展开
         *
         * @param interpolatedTime 动画进度，0~1
         */
        void onExpanding(float interpolatedTime);
    }

    private PreDrawHandler mPreDrawHandler;

    private final TextView mTextView;
    private FoldAnimation mAnimation;
    private TextFoldListener mListener;

    private final int mFoldLines; // 折叠后的行数
    private int mLines = 0; // 文本总行数
    private boolean mIsFold = false; // 当前折叠状态
    private boolean mFoldOnTextChange = true; // 文本发生改变时，默认折叠状态

    /**
     * @param textView  TextView
     * @param foldLines 折叠后的行数。文字超过该行数，TextView才能折叠。
     */
    public TextFoldHelper(@NonNull TextView textView, int foldLines) {
        mTextView = textView;
        mFoldLines = foldLines;
    }

    /**
     * 设置点击时折叠/展开文本的View
     *
     * @param view 点击view时文本折叠/展开
     */
    public TextFoldHelper setClickToFoldView(@NonNull View view) {
        view.setOnClickListener(this);
        return this;
    }

    /**
     * 设置监听器
     */
    public TextFoldHelper setFoldListener(TextFoldListener listener) {
        mListener = listener;
        return this;
    }

    /**
     * 设置文本。文本可折叠时，默认折叠
     */
    public void setText(String text) {
        setText(text, true);
    }

    /**
     * 设置文本
     *
     * @param fold 文本可折叠时，默认是否折叠
     */
    public void setText(CharSequence text, boolean fold) {
        setLines(0, false);
        mFoldOnTextChange = fold;
        mTextView.setText(text);
        if (TextUtils.isEmpty(text)) { // 不需要折叠
            if (mListener != null) {
                mListener.onFoldableChange(false);
            }
        } else {
            removeCurrentHandler();
            addNewHandler();
        }
    }

    /**
     * 添加新的Handler
     */
    private void addNewHandler() {
        try {
            // 添加新的handler
            mTextView.getViewTreeObserver().addOnPreDrawListener(new PreDrawHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除当前已有的Handler
     */
    private void removeCurrentHandler() {
        if (mPreDrawHandler != null) {
            try {
                mTextView.getViewTreeObserver().removeOnPreDrawListener(mPreDrawHandler);
//                log("remove " + mPreDrawHandler.hashCode());
                mPreDrawHandler = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否可折叠
     */
    public boolean isFoldable() {
        return mLines > mFoldLines;
    }

    /**
     * 当前是否折叠
     */
    public boolean isFold() {
        return mIsFold;
    }

    /**
     * 平滑折叠/展开
     *
     * @return 不可折叠或正在播放动画，则不执行并返回false
     */
    public boolean flipFold() {
        return flipFold(true);
    }

    /**
     * 折叠/展开
     *
     * @param useAnimation 是否使用动画平滑折叠/展开
     * @return 不可折叠或正在播放动画，则不执行并返回false
     */
    public boolean flipFold(boolean useAnimation) {
        return setFold(!isFold(), useAnimation);
    }

    /**
     * 折叠/展开
     *
     * @param fold         是否折叠
     * @param useAnimation 是否使用动画平滑折叠/展开
     * @return 不可折叠或正在播放动画，则不执行并返回false
     */
    public boolean setFold(boolean fold, boolean useAnimation) {
        if (!isFoldable() || (mAnimation != null && mAnimation.isPlaying())) {
            return false;
        }
        mIsFold = fold;
        int foldLines = Math.min(mFoldLines, mLines);
        final int to = getTextViewHeight(fold ? foldLines : mLines);
        if (useAnimation) {
            final int from = getTextViewHeight(fold ? mLines : foldLines);
            if (mAnimation == null) {
                mAnimation = new FoldAnimation();
            }
            mAnimation.setFromTo(from, to);
            mTextView.startAnimation(mAnimation);
        } else {
            mTextView.setMaxHeight(to);
            onFoldStatusChange();
        }
        return true;
    }

    private void setLines(int count, boolean callback) {
        mLines = count;
        boolean foldable = isFoldable();
        if (!foldable) {
            mIsFold = false;
        }
        if (mListener != null && callback) {
            mListener.onFoldableChange(foldable);
        }
    }

    private void onFoldStatusChange() {
        if (mListener != null) {
            mListener.onFoldStatusChange(mIsFold);
        }
    }

    private int getTextViewHeight(int line) {
        Layout layout = mTextView.getLayout();
        int desired = layout.getLineTop(line);
        int padding = mTextView.getCompoundPaddingTop() + mTextView.getCompoundPaddingBottom();
        return desired + padding;
    }

    @Override
    public void onClick(View v) {
        flipFold();
    }


    private class PreDrawHandler implements ViewTreeObserver.OnPreDrawListener {

        public PreDrawHandler() {
            mPreDrawHandler = this;
        }

        @Override
        public boolean onPreDraw() {
            removeCurrentHandler();
            int count = mTextView.getLineCount();
            setLines(count, true);
            setFold(mFoldOnTextChange, false);
            return false;
        }
    }

    private class FoldAnimation extends Animation implements Animation.AnimationListener {

        private int mFrom;
        private int mTo;

        protected FoldAnimation() {
            setDuration(200);
            setInterpolator(new LinearInterpolator());
            setAnimationListener(this);
        }

        protected FoldAnimation setFromTo(int from, int to) {
            mFrom = from;
            mTo = to;
            return this;
        }

        protected boolean isPlaying() {
            return hasStarted() && !hasEnded();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            if (mListener != null) {
                if (mIsFold) {
                    mListener.onFolding(interpolatedTime);
                } else {
                    mListener.onExpanding(interpolatedTime);
                }
            }
            mTextView.setMaxHeight((int) (interpolatedTime * (mTo - mFrom)) + mFrom);
            if (interpolatedTime == 1f) {
                onFoldStatusChange();
            }
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
//            onFoldStatusChange();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
