package com.lostanimals.tracks;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import static com.lostanimals.tracks.PreferencesUtility.*;

/**
 * This class is for making changes to the SharedPreferences of the user.
 * Any preference that needs to be queried or changed needs to be statically imported from the PreferenceUtility
 * Class.
 *
 * @version 1.0
 */
public class SaveSharedPreference {

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * Only called during login. Apply is called in this case as the task is running in background and called
     * from an async class.
     * @param context Application context fed from calling method in Login class.
     * @param loggedIn Boolean to save the state of the user login.
     * @param username The user's username once validated by the MySQL Database.
     * @param name The user's name once validated.
     * @param email the user's email once validated.
     */
    static void setLoggedIn(Context context, boolean loggedIn, String username, String name, String email) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.putString(USERNAME, username);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.apply();
    }

    /**
     * Get the Login Status
     * Only used when app is opened to check if the user is logged in. If the preference check returns a positive
     * then the LoginActivity is skipped, else a user must log in.
     * @param context Application context fed from calling method.
     * @return boolean: login status
     */
    static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }
}
