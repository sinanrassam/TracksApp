package com.lostanimals.tracks;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.lostanimals.tracks.utils.LoginTask;
import com.lostanimals.tracks.utils.PreferenceEntry;
import com.lostanimals.tracks.utils.PreferencesUtility;

import static com.lostanimals.tracks.utils.DEV_MODE.*;

public class LoginActivity extends AppCompatActivity {
    // TODO: Before submission, remove test log
    private final static String TAG = "LOGIN_ACTIVITY";
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    static String CHANNEL_ID = "channel_0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        PreferencesUtility.setSharedPreferences(this);

        /*
         *  START TESTING
         */

        // LOGIN with admin user
        //if (DEV_MODE) PreferencesUtility.setUserInfo(ADMIN_USER);
        //if (DEV_MODE) PreferencesUtility.setUserInfo(new PreferenceEntry("admin", "admin", "admin@bosh.live"));


        // Force the LOGIN activity
         // if (DEV_MODE) PreferencesUtility.setUserInfo(new PreferenceEntry(null, null, null));

        /*
         *  END TESTING
         */

        // If user is logged in, start the feed.
        if (!PreferencesUtility.getUserInfo().getUsername().equals("")) {
            Log.d(TAG, "onCreate: USER ACCOUNT_USER_ACCOUNT_USER_ACCOUNT: " +
                    PreferencesUtility.getUserInfo().getUsername());
            Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
            startActivity(intent);
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
                attemptLogin();
            }
        });

        Button mRegisterBtn = findViewById(R.id.login_register_btn);
        mRegisterBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void createNotificationChannel() {
        // https://developer.android.com/training/notify-user/build-notification
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void attemptLogin() {
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
            LoginTask loginTask = new LoginTask(this);
            loginTask.execute(email, password);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Tracks")
                    .setContentText("Logged in! :)")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
    }
}
