package com.lostanimals.tracks.utils;

public class PreferenceEntry {
    private final String mName;
    private final String mUsername;
    private final String mEmail;

    public PreferenceEntry(String name, String username, String email) {
        mName = name;
        mUsername = username;
        mEmail = email;
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