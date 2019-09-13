package com.lostanimals.tracks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.lostanimals.tracks.utils.PreferenceEntry;
import com.lostanimals.tracks.utils.PreferencesUtility;

public class LogoutActivity extends AppCompatActivity {
    private PreferencesUtility mPreferencesUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPreferencesUtility = new PreferencesUtility(sharedPreferences);


        Button mLogoutBtn = findViewById(R.id.logout_btn);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogout();
            }
        });

        Button mFeedBtn = findViewById(R.id.open_feed_btn);
        mFeedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FeedActivity.class));
            }
        });
    }

    private void attemptLogout() {
        PreferenceEntry preferenceEntry = new PreferenceEntry(null, null, null, false);
        if (mPreferencesUtility.setUserInfo(preferenceEntry)) {
            Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            getApplicationContext().startActivity(logoutIntent);
            Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Logout Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
