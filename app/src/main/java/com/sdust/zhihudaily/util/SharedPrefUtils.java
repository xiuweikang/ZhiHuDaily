
package com.sdust.zhihudaily.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sdust.zhihudaily.R;
import com.sdust.zhihudaily.ZhiHuApplication;


public class SharedPrefUtils {
	
	private static final String SHARED_PREF_START_JSON = "shared_pref-start_json";

	private static final String SHARED_PREF_NIGHT_MODE = "pref_night";

	public static SharedPreferences getDefaultSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(ZhiHuApplication.getContext());
	}

	public static boolean getIsNiaghtMode() {
		SharedPreferences sp = getDefaultSharedPreferences();
		return sp.getBoolean(SHARED_PREF_NIGHT_MODE,false);
	}

	public static void setNightMode(boolean isNight) {
		SharedPreferences sp = getDefaultSharedPreferences();
		sp.edit().putBoolean(SHARED_PREF_NIGHT_MODE,isNight).commit();
	}
	 public static String getStartJson(){
	        SharedPreferences sp = getDefaultSharedPreferences();
	        return sp.getString(SHARED_PREF_START_JSON, null);
	 }
	 
	 
	 public static void setStartJson(String value) {
		 SharedPreferences sp = getDefaultSharedPreferences();
		 sp.edit().putString(SHARED_PREF_START_JSON, value).commit();
	 }

	public static int getTheme() {
		Log.d("theme",getIsNiaghtMode()?"night" : "day");
		return getIsNiaghtMode() ? R.style.NightTheme : R.style.DayTheme;
	}


}
