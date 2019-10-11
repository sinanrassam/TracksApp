package com.lostanimals.tracks;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.lostanimals.tracks.tasks.LoginTask;
import com.lostanimals.tracks.utils.ConnectionManager;
import com.lostanimals.tracks.utils.NotificationUtility;
import com.lostanimals.tracks.utils.PreferencesUtility;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

/**
 *
 */
// Login activity class to execute logging in

// Setting feedIntent and registerIntent to switch activities depending on the
// users actions, clicking on login or register button

// onClick listener to check if user clicked Register or Login
// If login button, then attempt to login and check for valid login information
// If register button, then start register activity using registerIntent

public class LoginActivity extends AppCompatActivity {
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private Intent feedIntent;
    private Intent registerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creation of action bar with Login as title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");

        feedIntent = new Intent(this, FeedActivity.class);
        feedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        registerIntent = new Intent(this, RegisterActivity.class);
        registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        // Creation of pendingIntent to enable notifications
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, feedIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationUtility.createNotification(this, "Tracks", "Test", true, pendingIntent);
        PreferencesUtility.setSharedPreferences(this);


        // If user is logged in, start the feed.
        if (!PreferencesUtility.getUserInfo().getUsername().equals("")) {
            startActivity(feedIntent);
            this.finish();
        }

        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = findViewById(R.id.login_username);
        mPasswordView = findViewById(R.id.login_password);

        // Listening for click on login button
        // If clicked, call attemptLogin() to attempt login
        // If exception is returned, catch and print what exception was thrown
        Button mSignInBtn = findViewById(R.id.login_btn);
        mSignInBtn.setOnClickListener(new OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                try {

                    attemptLogin();
                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // Listening for click on register button
        // If this was clicked, start register activity using registerIntent
        Button mRegisterBtn = findViewById(R.id.login_register_btn);
        mRegisterBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(registerIntent);
                finish();
            }
        });
    }

    /**
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws JSONException
     */

    // attemptLogin()
    //~~~~~~~~~~~~~~~~
    // Attempt to login using the information that the user has entered (email and password)
    // Throw exceptions for onCreate methods to catch to print exceptions to re-evaluate
    private void attemptLogin() throws ExecutionException, InterruptedException, JSONException {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString().toLowerCase();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        Context context = LoginActivity.this;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check if valid email has been entered, if not display error
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for network, if none, alert user.
        if (!ConnectionManager.isNetworkAvailable(context)) {
            focusView = mEmailView;
            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_LONG).show();
            cancel = true;
        }

        // Checking if loginTask was executed successfully, if it was then display notification and start feed activity
        if (cancel) {
            focusView.requestFocus();
        } else {
            LoginTask loginTask = new LoginTask(this);
            loginTask.execute(email, password);
            if (loginTask.get().get("response").equals("successful")) {
                NotificationUtility.displayNotification(0);
                startActivity(feedIntent);
                finish();
            }
        }
    }
}