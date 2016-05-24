
package com.sdust.zhihudaily.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPrefUtils {
	
	private static final String SHARED_PREF_START_JSON = "shared_pref-start_json";

	private static final String SHARED_PREF_NIGHT_MODE = "pref_night";

	public static SharedPreferences getDefaultSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static boolean getIsNiaghtMode(Context context) {
		SharedPreferences sp = getDefaultSharedPreferences(context);
		return sp.getBoolean(SHARED_PREF_NIGHT_MODE,false);
	}
	
	 public static String getStartJson(Context context){
	        SharedPreferences sp = getDefaultSharedPreferences(context);
	        return sp.getString(SHARED_PREF_START_JSON, null);
	 }
	 
	 
	 public static void setStartJson(Context context,String value) {
		 SharedPreferences sp = getDefaultSharedPreferences(context);
		 sp.edit().putString(SHARED_PREF_START_JSON, value).commit();
	 }



}
