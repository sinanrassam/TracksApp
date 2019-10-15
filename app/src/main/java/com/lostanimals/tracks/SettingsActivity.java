package com.lostanimals.tracks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences mPreference;
    private String logout = "logout_KEY";
    private final String information = "information";
    private final String notif = "notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Settings");

        getSupportFragmentManager().beginTransaction().replace(R.id.settingsContainer, new SettingsFragment()).commit();

    }



    /*@Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals(logout)){
            Log.d("SHRD_PREF", "Bundle+ "+sharedPreferences.toString()+ " String: " + key);
            Toast.makeText(this, "Hellow", Toast.LENGTH_SHORT).show();
        } else if(key.equals(information)) {
            Log.d("BUTTON CLICKED!!!!!!", information+"NFO NFO NFO");
        } else if (key.equals(notif)) {
            Log.d("BUTTON CLICKED!!!!!!", information+"NOTIF NOTIF NOTIF");
        }
    }*/

}
