package com.lostanimals.tracks;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.lostanimals.tracks.utils.LoginTask;
import com.lostanimals.tracks.utils.NotificationUtility;
import com.lostanimals.tracks.utils.PreferenceEntry;
import com.lostanimals.tracks.utils.PreferencesUtility;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import static com.lostanimals.tracks.utils.DEV_MODE.*;

public class LoginActivity extends AppCompatActivity {
    // TODO: Before submission, remove test log
    private final static String TAG = "LOGIN_ACTIVITY";

//    private static String CHANNEL_ID = "channel_0";
//    private NotificationManager notificationManager;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private Intent feedIntent;
    private Intent registerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        feedIntent = new Intent(this, FeedActivity.class);
        feedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        registerIntent = new Intent(this, RegisterActivity.class);
        registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        NotificationUtility.createNotification(this, "Tracks", "Test", true);
        PreferencesUtility.setSharedPreferences(this);

        /*
         *  START TESTING
         */

        // LOGIN with admin user
        //if (DEV_MODE) PreferencesUtility.setUserInfo(ADMIN_USER);
        if (DEV_MODE) PreferencesUtility.setUserInfo(new PreferenceEntry("admin", "admin", "admin@bosh.live"));


        //Force the LOGIN activity
        //if (DEV_MODE) PreferencesUtility.setUserInfo(new PreferenceEntry(null, null, null));

        /*
         *  END TESTING
         */

        // If user is logged in, start the feed.
        if (!PreferencesUtility.getUserInfo().getUsername().equals("")) {
            Log.d(TAG, "onCreate: USER ACCOUNT_USER_ACCOUNT_USER_ACCOUNT: " +
                    PreferencesUtility.getUserInfo().getUsername());
            startActivity(feedIntent);

            this.finish();
        } else {
            Log.d(TAG, "onCreate: USERNAMEUSERNAME: "+PreferencesUtility.getUserInfo().getUsername());
        }

        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = findViewById(R.id.login_username);
        mPasswordView = findViewById(R.id.login_password);

        Button mSignInBtn = findViewById(R.id.login_btn);
        mSignInBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptLogin();
                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button mRegisterBtn = findViewById(R.id.login_register_btn);
        mRegisterBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(registerIntent);
                finish();
            }
        });
    }

    private void attemptLogin() throws ExecutionException, InterruptedException, JSONException {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString().toLowerCase();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            LoginTask loginTask = new LoginTask();
            loginTask.execute(email, password);
            if (loginTask.get().get("response").equals("successful")) {
                NotificationUtility.displayNotification(0);

                startActivity(feedIntent);
                loginTask.cancel(true);

                finish();
            } else {
                loginTask.cancel(true);
            }
        }
    }
}
