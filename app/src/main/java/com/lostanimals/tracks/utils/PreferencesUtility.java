package com.lostanimals.tracks.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.lostanimals.tracks.entries.PreferenceEntry;

import static android.content.ContentValues.TAG;

public class PreferencesUtility {
	private final static String KEY_NAME = "key_name";
	private final static String KEY_USERNAME = "key_username";
	private final static String KEY_EMAIL = "key_email";
	private final static String KEY_DARKMODE = "key_darkmode";
	private final static String KEY_NOTIF = "key_notif";
	
	private static SharedPreferences mSharedPreferences;
	
	public PreferencesUtility(SharedPreferences sharedPreferences) {
		mSharedPreferences = sharedPreferences;
	}
	
	public static void setSharedPreferences(Context context) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public static boolean removeUserEntry() {
		return setUserInfo(new PreferenceEntry("", "", ""));
	}
	
	public static boolean setUserInfo(PreferenceEntry preferenceEntry) {
		Log.d(TAG, "setUserInfo: " + preferenceEntry.getName());
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(KEY_NAME, preferenceEntry.getName());
		editor.putString(KEY_USERNAME, preferenceEntry.getUsername());
		editor.putString(KEY_EMAIL, preferenceEntry.getEmail());
		editor.putBoolean(KEY_DARKMODE, preferenceEntry.isDarkMode());
		editot.putBoolean(KEY_NOTIF, preferenceEntry.isNotif());
		Log.d("PREFS_UTIL", "ENTRY_SAVED_ENTRY_SAVED_ENTRY_SAVED | OBJECT: " + getUserInfo() + " | Username:" + preferenceEntry.getUsername());
		return editor.commit();
	}
	
	public static PreferenceEntry getUserInfo() {
		String name = mSharedPreferences.getString(KEY_NAME, "");
		Log.d(TAG, "getUserInfo: " + name);
		String username = mSharedPreferences.getString(KEY_USERNAME, "");
		String email = mSharedPreferences.getString(KEY_EMAIL, "");
		return new PreferenceEntry(name, username, email);
	}

	public void setDarkMode(boolean darkMode) {
		PreferenceEntry.mDarkmode = darkMode;
	}

	public static void setNotif(boolean notif) {
		PreferenceEntry.mNotif = notif;
	}
}
