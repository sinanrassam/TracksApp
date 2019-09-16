package com.lostanimals.tracks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.lostanimals.tracks.utils.LoginTask;
import com.lostanimals.tracks.utils.PreferencesUtility;

// TEST DATA
import java.util.Objects;

import static com.lostanimals.tracks.utils.DEV_MODE.*;

public class LoginActivity extends AppCompatActivity {
    // TODO: Before submission, remove test log
    private final static String TAG = "LOGIN_ACTIVITY";
    private static PreferencesUtility mPreferencesUtility;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPreferencesUtility = new PreferencesUtility(sharedPreferences);

        /*
         *  START TESTING
         */

        // LOGIN with admin user
//        if (DEV_MODE) mPreferencesUtility.setUserInfo(ADMIN_USER);

        // Force the LOGIN activity
         if (DEV_MODE) mPreferencesUtility.setUserInfo(NULL_USER);

        /*
         *  END TESTING
         */

        // If user is logged in, start the feed.
        if (!mPreferencesUtility.getUserInfo().getUsername().equals("")) {
            Log.d(TAG, "onCreate: USER ACCOUNT_USER_ACCOUNT_USER_ACCOUNT: " +
                    mPreferencesUtility.getUserInfo().getUsername());
            Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
            startActivity(intent);
            this.finish();
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
            LoginTask loginTask = new LoginTask(this, mPreferencesUtility);
            loginTask.execute(email, password);
        }
    }
}
