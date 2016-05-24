package com.sdust.zhihudaily.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.ZhiHuApplication;
import com.sdust.zhihudaily.adapter.NavigationDrawerAdapter;
import com.sdust.zhihudaily.interfaces.NavigationDrawerCallbacks;
import com.sdust.zhihudaily.data.model.Theme;
import com.sdust.zhihudaily.data.model.Themes;
import com.sdust.zhihudaily.data.source.Repository;

import java.util.List;


public class NavigationFragment extends Fragment implements NavigationDrawerCallbacks {
    public static final String TAG = NavigationFragment.class.getSimpleName();


    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";


    /**
     * 指向NavigationActivity的引用
     */
    private NavigationDrawerCallbacks mCallbacks;


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mNavigationFragment;
    private RecyclerView mRecyclerView;
    private int mCurrentSelectedPosition = -1;
    private NavigationDrawerAdapter mDrawerAdapter;

    private List<Theme> mThemes;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }
        mDrawerAdapter = new NavigationDrawerAdapter();
        mDrawerAdapter.setNavigationCallbacks(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//设置RecyclerView为竖着的
        mRecyclerView.setAdapter(mDrawerAdapter);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (position == mCurrentSelectedPosition) {
            closeDrawer();
            return;
        }
        closeDrawer();
        mCurrentSelectedPosition = position;
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        refresh();
    }


    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mNavigationFragment);
    }

    public void openDrawer() {
        if (mDrawerLayout != null)
            mDrawerLayout.openDrawer(mNavigationFragment);
    }

    public void closeDrawer() {
        if (mDrawerLayout != null)
            mDrawerLayout.closeDrawer(mNavigationFragment);
    }

    /**
     * 获取theme的ID
     * @param sectionNumber
     * @return
     */
    public String getSectionId(int sectionNumber) {
        return sectionNumber == 0 ? null : mThemes.get(sectionNumber - 1).getId();
    }



    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mNavigationFragment = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();//添加导航的按钮
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private void refresh() {

        ZhiHuApplication.getRepository().getThemes(new Repository.Callback<Themes>() {
            @Override
            public void success(Themes themes, boolean outDate) {
                mThemes = themes.getOthers();
                mDrawerAdapter.setThemes(mThemes);
            }

            @Override
            public void failure(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public String getTitle(int sectionNumber) {
        return sectionNumber == 0 ? getString(R.string.title_activity_main) : mThemes.get(sectionNumber - 1).getName();
    }

    public int getCurrentSelectedPosition() {
        return  mCurrentSelectedPosition;
    }

    /**
     * 选择主页item上的theme选择主题，暂时没用
     * @param t
     */
    public void selectTheme(Theme t) {
        int size = 0;
        if (mThemes != null && (size = mThemes.size()) > 0 && isAdded()) {
            for (int i = 0; i < size; i++) {
                if (mThemes.get(i).getId().equals(t.getId())) {
                    selectItem(i + 1);
                    break;
                }
            }
        }
    }
    public static int getDefaultNavDrawerItem() {
        return 0;
    }

    public void selectItem(int position) {
        if (position == mCurrentSelectedPosition) {
            closeDrawer();
            return;
        }
        mDrawerAdapter.selectPosition(position);
    }

}
