package com.lostanimals.tracks;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceScreen;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lostanimals.tracks.utils.PreferencesUtility;


public class SettingsFragment  extends PreferenceFragmentCompat  implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String logout = "logout_KEY";
    private final String information = "information";
    private final String notif = "notification";
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);
        final Preference logoutPref = findPreference(logout);

    }

    /*@Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String value = newValue.toString();

        if(preference instanceof PreferenceScreen){

        }

        return false;
    }
    */

    /*Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals(logout)){
            PreferenceScreen logoutPref = findPreference(logout);
            logoutPref.setSummary("PLEASE WORK");
            Log.d("SHRD_PREF", "Bundle+ "+sharedPreferences.toString()+ " String: " + key);
            Toast.makeText(getContext(), "Hellow", Toast.LENGTH_SHORT).show();
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
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
