package com.sdust.zhihudaily.setting;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sdust.zhihudaily.Constants;
import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.data.source.local.db.CacheDao;
import com.sdust.zhihudaily.util.FileUtils;
import com.sdust.zhihudaily.util.SharedPrefUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SettingFragment extends Fragment {


    @InjectView(R.id.cbk_night)
    CheckBox mCbkNight;
    @InjectView(R.id.txt_cache_size)
    TextView mTxtCacheSize;
    @InjectView(R.id.txt_version)
    TextView mTxtVersion;
    @InjectView(R.id.txt_about_me)
    TextView mTxtAboutMe;
    @InjectView(R.id.layout_clear_cache)
    LinearLayout mLayoutClearCache;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        initView();
    }

    private void initView() {
        mTxtCacheSize.setText(FileUtils.getCacheSize());
        mLayoutClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCache();
            }
        });
        mTxtAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(false);
            }
        });

        mTxtVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(true);
            }
        });
        boolean isNight = SharedPrefUtils.getIsNiaghtMode();
        if (isNight) {
            mCbkNight.setChecked(true);
        } else {
            mCbkNight.setChecked(false);
        }


        mCbkNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPrefUtils.setNightMode(true);
                } else {
                    SharedPrefUtils.setNightMode(false);
                }
                getActivity().setTheme(SharedPrefUtils.getTheme());
            }
        });
    }

    private void clearCache() {
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
        CacheDao dao = new CacheDao(getActivity());
        dao.deleteAllCache();
        Toast.makeText(getActivity(), "缓存已清除", Toast.LENGTH_SHORT).show();
        mTxtCacheSize.setText(" 0.00K");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void showDialog(boolean isVersion) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.dialog_version);

        TextView textView = (TextView) dialog.findViewById(R.id.dialog_text);

        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        String applicationName = getResources().getString(R.string.app_name);
        String applicationVersion = getResources().getString(R.string.app_version);
        if (isVersion) {
            String data = String.format("%1$s\r\nV%2$s", applicationName, applicationVersion);

            textView.setText(data);
        } else {
            String title = applicationName += "<br/>";
            String subTitleTemp = getResources().getString(R.string.app_sub_name);
            String subTitle = subTitleTemp += "<br/>";
            String author = new StringBuilder().append("@").append(getResources().getString(R.string.app_author)).toString();
            String githubUrl = new StringBuilder().append("<a href='")
                    .append(Constants.GITGUB_PROJECT)
                    .append("'>")
                    .append(Constants.GITGUB_PROJECT)
                    .append("</a>")
                    .append("<br/>")
                    .toString();
            String data = String.format("%1$s%2$s%3$sby %4$s",
                    title,
                    subTitle,
                    githubUrl,
                    author);

            CharSequence charSequence = Html.fromHtml(data);

            textView.setText(charSequence);
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
    }
}
