package com.sdust.zhihudaily.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sdust.zhihudaily.Constants;
import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.db.CacheDao;

import java.io.File;


public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private static final String TAG = StartFragment.class.getSimpleName();
    private static final String PREF_VERSION = "pref_version";
    private static final String PREF_ABOUT_ME = "pref_about";
    private static final String PREF_CLEAR_CACHE = "pref_clear_cache";


    private Preference clearCachePreference;
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        findPreference(PREF_VERSION).setOnPreferenceClickListener(this);
        findPreference(PREF_ABOUT_ME).setOnPreferenceClickListener(this);
        clearCachePreference = findPreference(PREF_CLEAR_CACHE);
        clearCachePreference.setSummary(getCacheSize());
        clearCachePreference.setOnPreferenceClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // setHasOptionsMenu(true);
        // Inflate the layout for this fragment

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_story, menu);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(PREF_ABOUT_ME)) {
            showDialog(false);
        } else if (preference.getKey().equals(PREF_VERSION)) {
            showDialog(true);
        } else if (preference.getKey().equals(PREF_CLEAR_CACHE)) {
            clearCache();
        }
        return false;
    }

    private String getCacheSize() {
        File file = ImageLoader.getInstance().getDiskCache().getDirectory();
        long size = getFileSize(file);
        if (size >= 1 * 1024 * 1024) {
            String result = String.format("%.2f", size * 1.0 / (1024 * 1024));
            return " " + result + "M";
        } else {
            String result = String.format("%.2f", size * 1.0 / (1024));
            return " " + result + "K";
        }
    }

    private long getFileSize(File file) {

        if (file == null)
            return 0;
        long sum = 0;

        if (file.isDirectory()) {
            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    sum += getFileSize(files[i]);
                } else {
                    sum += files[i].length();
                }
            }
        } else {
            sum = file.length();
        }
        return sum;
    }


    private void clearCache() {
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();

        CacheDao dao = new CacheDao(getActivity());
        dao.deleteAllCache();
        Toast.makeText(getActivity(), "缓存已清除", Toast.LENGTH_SHORT).show();
        clearCachePreference.setSummary(" 0.00K");
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
