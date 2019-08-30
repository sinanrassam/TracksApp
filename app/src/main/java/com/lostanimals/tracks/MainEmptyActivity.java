package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Test class based off this link:
 * https://android.jlelse.eu/login-and-main-activity-flow-a52b930f8351
 * Good way to either go to login activity (if user is not signed in)
 * Or go to feed if they are.
 */
public class MainEmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

        // go straight to main if a token is stored
        // original line:
        // if (Util.getToken() != null)
        if (BackgroundWorker.getToken()) {
            activityIntent = new Intent(this, FeedActivity.class);
        } else {
            activityIntent = new Intent(this, LoginActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }
}
