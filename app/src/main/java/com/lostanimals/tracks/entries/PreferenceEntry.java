package com.lostanimals.tracks.entries;

import java.util.LinkedList;
import java.util.Queue;

public class PreferenceEntry {
	private static String mName = null;
	private static String mUsername = null;
	private static String mEmail = null;
	private static boolean mDarkMode = null;
	private static boolean mNotif = null;
	//private static Queue<Integer> mHistory = new LinkedList<>();
	public PreferenceEntry(String name, String username, String email, boolean darkmode, boolean notif) {
		mName = name;
		mUsername = username;
		mEmail = email;
		mDarkMode = darkmode;
		mNotif = notif;
	}

	public boolean isDarkMode() {
		return mDarkMode;
	}

	public static boolean isNotif() {
		return mNotif;
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