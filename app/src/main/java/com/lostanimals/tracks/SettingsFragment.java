package com.lostanimals.tracks;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceScreen;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class SettingsFragment  extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String logout = "logout_KEY";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PreferenceScreen ps = (PreferenceScreen) findPreference(logout)

        if(key.equals(logout)){

        }
    }
}
