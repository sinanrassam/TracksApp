package com.lostanimals.tracks.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.lostanimals.tracks.entries.PreferenceEntry;

public class PreferencesUtility {
	private final static String KEY_NAME = "key_name";
	private final static String KEY_USERNAME = "key_username";
	private final static String KEY_EMAIL = "key_email";
	
	private static SharedPreferences mSharedPreferences;
	
	public PreferencesUtility(SharedPreferences sharedPreferences) {
		mSharedPreferences = sharedPreferences;
	}
	
	public static void setSharedPreferences(Context context) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public static boolean setUserInfo(PreferenceEntry preferenceEntry) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(KEY_NAME, preferenceEntry.getName());
		editor.putString(KEY_USERNAME, preferenceEntry.getUsername());
		editor.putString(KEY_EMAIL, preferenceEntry.getEmail());
		Log.d("PREFS_UTIL", "ENTRY_SAVED_ENTRY_SAVED_ENTRY_SAVED | OBJECT: " + getUserInfo() +
				" | Username:" + preferenceEntry.getUsername());
		return editor.commit();
	}
	
	public static PreferenceEntry getUserInfo() {
		String name = mSharedPreferences.getString(KEY_NAME, "");
		String username = mSharedPreferences.getString(KEY_USERNAME, "");
		String email = mSharedPreferences.getString(KEY_EMAIL, "");
		return new PreferenceEntry(name, username, email);
	}
	
	public static boolean removeUserEntry() {
		return setUserInfo(new PreferenceEntry("", "", ""));
	}
}
