package com.sdust.zhihudaily.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;


public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    public static final String ARG_THEME_POSITION = "theme_Position";
    public static final String ARG_THEME_ID = "theme_id";


    private int mArgThemeNumber;

    private String mArgThemeId;

    public BaseFragment() {
    }

    public static BaseFragment newInstance(int position, String sectionId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_THEME_POSITION, position);
        bundle.putString(ARG_THEME_ID, sectionId);
        BaseFragment fragment = null;
        if (position == 0) {
            fragment = new DailyStoriesFragment();
        } else {
            fragment = new ThemeStoriesFragment();
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgThemeNumber = getArguments().getInt(ARG_THEME_POSITION);
        mArgThemeId = getArguments().getString(ARG_THEME_ID);
    }

    public int getThemeNumber() {
        return mArgThemeNumber;
    }

    public String getThemeId() {
        return mArgThemeId;
    }
}
