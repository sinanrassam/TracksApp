package com.lostanimals.tracks.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.lostanimals.tracks.entries.PreferenceEntry;

public class PreferencesUtility {
    private final static String KEY_NAME = "key_name";
    private final static String KEY_USERNAME = "key_username";
    private final static String KEY_EMAIL = "key_email";
    private final static String KEY_NOTIFICATIONS = "key_notifications";
    private final static String KEY_DARK_MODE = "Key_dark_mode";

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
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_NAME, preferenceEntry.getName());
        editor.putString(KEY_USERNAME, preferenceEntry.getUsername());
        editor.putString(KEY_EMAIL, preferenceEntry.getEmail());
        return editor.commit();
    }

    public static void setDarkMode(boolean darkMode) {
        getUserInfo().setDarkModeEnabled(darkMode);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_DARK_MODE, getUserInfo().isDarkModeEnabled());
        // PreferencesUtility.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        editor.apply();
    }

    public static void setNotifications(boolean notifications) {
        getUserInfo().setNotificationsEnabled(notifications);
        NotificationUtility.setNotificationsEnabled(notifications);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_NOTIFICATIONS, getUserInfo().isNotificationsEnabled());
        editor.apply();
    }

    public static PreferenceEntry getUserInfo() {
        String name = mSharedPreferences.getString(KEY_NAME, "");
        String username = mSharedPreferences.getString(KEY_USERNAME, "");
        String email = mSharedPreferences.getString(KEY_EMAIL, "");
        return new PreferenceEntry(name, username, email);
    }
}
