package com.lostanimals.tracks.entries;

public class PreferenceEntry {
	private static String mName = null;
	private static String mUsername = null;
	private static String mEmail = null;
	
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