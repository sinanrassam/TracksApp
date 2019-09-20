package com.lostanimals.tracks.utils;

import com.lostanimals.tracks.entries.PreferenceEntry;

/**
 * PLEASE MODIFY DEV_MODE.
 * ADD DEV_MODE AS A CONDITION FOR TESTING AND SWITCH THE GLOBAL VARIABLE HERE.
 * TODO: REMOVE DEV_MODE BEFORE SUBMISSION
 */
public abstract class DEV_MODE {
	public final static boolean DEV_MODE = true;
	public final static PreferenceEntry ADMIN_USER = new PreferenceEntry("admin", "admin", "admin@bosh.live");
	public final static PreferenceEntry NULL_USER = new PreferenceEntry(null, null, null);
}