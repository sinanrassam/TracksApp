package com.lostanimals.tracks;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceScreen;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lostanimals.tracks.utils.PreferencesUtility;


public class SettingsFragment  extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String logout = "logout_KEY";
    private final String information = "information";
    private final String notif = "notification";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Log.d("SHRD_PREF", "Bundle+ "+sharedPreferences.toString()+ " String: " + key);
        PreferenceScreen ps = (PreferenceScreen) findPreference(logout);

        if(key.equals(logout)){
            attemptLogout();
        } else if(key.equals(information)) {
            Log.d("BUTTON CLICKED!!!!!!", information+"NFO NFO NFO");
        } else if (key.equals(notif)) {
            Log.d("BUTTON CLICKED!!!!!!", information+"NOTIF NOTIF NOTIF");
        }
    }

    private void attemptLogout() {
        if (PreferencesUtility.removeUserEntry()) {
            Intent logoutIntent = new Intent(getActivity(), LoginActivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().finish();
            getActivity().startActivity(logoutIntent);
            Toast.makeText(getActivity(), "Logout Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Logout Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
