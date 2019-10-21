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

	// Filter Values
	private static String mSorter = null;
	private static String mSpeciesDog = null;
	private static String mSpeciesCat = null;
	private static String mSpeciesBird = null;
	private static String mSpeciesRabbit = null;
	private static String mSpeciesHorse = null;
	private static String mSpeciesCow = null;
	private static String mSpeciesSheep = null;
	private static String mSpeciesGoat = null;
	private static String mSpeciesGuineaPig = null;
	private static String mSpeciesTurtle = null;
	private static String mSpeciesOther = null;
	private static String mColourBlack = null;
	private static String mColourWhite = null;
	private static String mColourGrey = null;
	private static String mColourBrown = null;
	private static String mColourRed = null;
	private static String mColourGold = null;
	private static String mColourOther = null;
	private static String mMicroYes = null;
	private static String mMicroNo = null;


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

	/**
	 * Creates an SQL command for returning filters
	 * @return the SQL command as a String
	 */
	public static String getFiltersCommand() {
		String command = "";

		// Add microchipped filters to command
		if (mMicroYes != null || !mMicroYes.equals("")) {
			command += "AND microchipped = ";
			command += mMicroYes;
		}

		if (mMicroNo != null || !mMicroYes.equals("")) {
			command += "AND microchipped = ";
			command += mMicroNo;
		}

		return command;
	}

	public static void setmSorter(String mSorter) {
		PreferencesUtility.mSorter = mSorter;
	}

	public static void setmSpeciesDog(String mSpeciesDog) {
		PreferencesUtility.mSpeciesDog = mSpeciesDog;
	}

	public static void setmSpeciesCat(String mSpeciesCat) {
		PreferencesUtility.mSpeciesCat = mSpeciesCat;
	}

	public static void setmSpeciesBird(String mSpeciesBird) {
		PreferencesUtility.mSpeciesBird = mSpeciesBird;
	}

	public static void setmSpeciesRabbit(String mSpeciesRabbit) {
		PreferencesUtility.mSpeciesRabbit = mSpeciesRabbit;
	}

	public static void setmSpeciesHorse(String mSpeciesHorse) {
		PreferencesUtility.mSpeciesHorse = mSpeciesHorse;
	}

	public static void setmSpeciesCow(String mSpeciesCow) {
		PreferencesUtility.mSpeciesCow = mSpeciesCow;
	}

	public static void setmSpeciesSheep(String mSpeciesSheep) {
		PreferencesUtility.mSpeciesSheep = mSpeciesSheep;
	}

	public static void setmSpeciesGoat(String mSpeciesGoat) {
		PreferencesUtility.mSpeciesGoat = mSpeciesGoat;
	}

	public static void setmSpeciesGuineaPig(String mSpeciesGuineaPig) {
		PreferencesUtility.mSpeciesGuineaPig = mSpeciesGuineaPig;
	}

	public static void setmSpeciesTurtle(String mSpeciesTurtle) {
		PreferencesUtility.mSpeciesTurtle = mSpeciesTurtle;
	}

	public static void setmSpeciesOther(String mSpeciesOther) {
		PreferencesUtility.mSpeciesOther = mSpeciesOther;
	}

	public static void setmColourBlack(String mColourBlack) {
		PreferencesUtility.mColourBlack = mColourBlack;
	}

	public static void setmColourWhite(String mColourWhite) {
		PreferencesUtility.mColourWhite = mColourWhite;
	}

	public static void setmColourGrey(String mColourGrey) {
		PreferencesUtility.mColourGrey = mColourGrey;
	}

	public static void setmColourBrown(String mColourBrown) {
		PreferencesUtility.mColourBrown = mColourBrown;
	}

	public static void setmColourRed(String mColourRed) {
		PreferencesUtility.mColourRed = mColourRed;
	}

	public static void setmColourGold(String mColourGold) {
		PreferencesUtility.mColourGold = mColourGold;
	}

	public static void setmColourOther(String mColourOther) {
		PreferencesUtility.mColourOther = mColourOther;
	}

	public static void setmMicroYes(String mMicroYes) {
		PreferencesUtility.mMicroYes = mMicroYes;
	}

	public static void setmMicroNo(String mMicroNo) {
		PreferencesUtility.mMicroNo = mMicroNo;
	}
}
