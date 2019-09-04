package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText etUser, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setEtUser((EditText) findViewById(R.id.login_et_username));
        setEtPassword((EditText) findViewById(R.id.login_et_password));

        /*
          Check is user is logged in, if so, skip this activity
         */
        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            Intent feedIntent = new Intent(getApplicationContext(), FeedActivity.class);
            startActivity(feedIntent);
        }
    }

    // This method is called when Login button is pressed
    public void OnLogin(View view) {
        // Get the text a user has entered.
        String username = etUser.getText().toString();
        String password = etPassword.getText().toString();

        // TODO: These could be enums depending on how many scripts will be called
        String type = "login";

        // Run the login script
        AttemptLogin bgWorker = new AttemptLogin(this);
        bgWorker.execute(type, username, password);
    }

    private void setEtUser(EditText etUser) {
        this.etUser = etUser;
    }

    private void setEtPassword(EditText etPassword) {
        this.etPassword = etPassword;
    }

    public void openRegisterActivity(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    /**
     * Placeholder for moving to feed activity when user is signed in.
     */
    public void openFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
    }
}