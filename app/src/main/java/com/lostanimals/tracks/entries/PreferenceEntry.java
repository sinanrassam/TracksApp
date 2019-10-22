package com.lostanimals.tracks.entries;

import android.util.Log;

public class PreferenceEntry {
    private static String mName = null;
    private static String mUsername = null;
    private static String mEmail = null;
    public static boolean mNotifications = true;
    public static boolean mDarkMode = false;


    public PreferenceEntry(String name, String username, String email) {
        mName = name;
        mUsername = username;
        mEmail = email;
    }

    public boolean isNotificationsEnabled() {
        return mNotifications;
    }


    public boolean isDarkModeEnabled() {
        return mDarkMode;
    }

    public String getName() {
        return mName;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getEmail() {
        return mEmail;
    }
}