package com.lostanimals.tracks;

/**
 * This class stores the Key values for the users' SharedPreferences. All values need to be final static strings.
 * String names should follow the Java-style final declaration and be UPPERCASE_SNAKE_CASE while the Key values
 * should be lower_case_snake_case.
 *
 * No methods or code should be written here as the values are only queried when needed to by the SaveSharedPreference
 * utility.
 *
 * @version 1.0
 */
class PreferencesUtility {
    final static String LOGGED_IN_PREF = "logged_in_status";
    final static String USERNAME = "username";
}
