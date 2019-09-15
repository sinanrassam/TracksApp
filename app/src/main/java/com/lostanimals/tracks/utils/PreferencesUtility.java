package com.lostanimals.tracks.utils;

import android.content.SharedPreferences;
import android.util.Log;

public class PreferencesUtility {
    private final static String KEY_NAME = "key_name";
    private final static String KEY_USERNAME = "key_username";
    private final static String KEY_EMAIL = "key_email";
    private final static String KEY_LOGIN = "key_login_bool";

    private final SharedPreferences mSharedPreferences;

    public PreferencesUtility(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public boolean setUserInfo(PreferenceEntry preferenceEntry) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_NAME, preferenceEntry.getName());
        editor.putString(KEY_USERNAME, preferenceEntry.getUsername());
        editor.putString(KEY_EMAIL, preferenceEntry.getEmail());
        editor.putBoolean(KEY_LOGIN, preferenceEntry.getLogin());
        Log.d("PREFS_UTIL", "ENTRY_SAVED_ENTRY_SAVED_ENTRY_SAVED | OBJECT: " + getUserInfo() + " | Username:" + preferenceEntry.getUsername());
        return editor.commit();
    }

    public PreferenceEntry getUserInfo() {
        String name = mSharedPreferences.getString(KEY_NAME, "");
        String username = mSharedPreferences.getString(KEY_USERNAME, "");
        String email = mSharedPreferences.getString(KEY_EMAIL, "");
        boolean login = mSharedPreferences.getBoolean(KEY_LOGIN, false);
        return new PreferenceEntry(name, username, email, login);
    }
}
