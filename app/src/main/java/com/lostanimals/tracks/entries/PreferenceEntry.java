package com.lostanimals.tracks.entries;

public class PreferenceEntry {
    private static String mName = null;
    private static String mUsername = null;
    private static String mEmail = null;
    private static boolean mNotifications = true;
    private static boolean mDarkMode = false;


    public PreferenceEntry(String name, String username, String email) {
        mName = name;
        mUsername = username;
        mEmail = email;
    }

    public boolean isNotificationsEnabled() {
        return mNotifications;
    }

    public void setNotificationsEnabled(boolean notifications) {
        PreferenceEntry.mNotifications = notifications;
    }

    public boolean isDarkModeEnabled() {
        return mDarkMode;
    }

    public void setDarkModeEnabled(boolean darkMode) {
        PreferenceEntry.mDarkMode = darkMode;
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