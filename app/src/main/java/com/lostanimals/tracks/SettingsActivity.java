package com.lostanimals.tracks;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private final String information = "information";
    private final String notif = "notification";
    SharedPreferences mPreference;
    private String logout = "logout_KEY";

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
