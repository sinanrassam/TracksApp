package com.lostanimals.tracks.utils;

public class PreferenceEntry {
    private final String mName;
    private final String mUsername;
    private final String mEmail;
    private final boolean mLogin;

    public PreferenceEntry(String name, String username, String email, boolean login) {
        mName = name;
        mUsername = username;
        mEmail = email;
        mLogin = login;
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

    public boolean getLogin() {
        return mLogin;
    }
}