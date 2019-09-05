package com.lostanimals.tracks.utils;

import android.content.SharedPreferences;

public class PreferencesUtility {
    final static String KEY_NAME = "key_name";
    final static String KEY_USERNAME = "key_username";
    final static String KEY_EMAIL = "key_email";
    final static String KEY_LOGIN = "key_login_bool";

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
