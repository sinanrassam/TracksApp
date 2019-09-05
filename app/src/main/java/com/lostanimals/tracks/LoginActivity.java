package com.lostanimals.tracks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.lostanimals.tracks.utils.PreferencesUtility;
import com.lostanimals.tracks.utils.ServerManager;

public class LoginActivity extends AppCompatActivity {
    /*
    DEV_MODE: Change this to false to run in user mode.
     */
    static final boolean DEV_MODE = false;
    private EditText etUser, etPassword;
    private PreferencesUtility mPreferencesUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPreferencesUtility = new PreferencesUtility(sharedPreferences);



        setEtUser((EditText) findViewById(R.id.login_et_username));
        setEtPassword((EditText) findViewById(R.id.login_et_password));

        if (!DEV_MODE) {
            if (mPreferencesUtility.getUserInfo() == null) {
                Intent feedIntent = new Intent(getApplicationContext(), FeedActivity.class);
                startActivity(feedIntent);
            }
        }
    }

    public void OnLogin(View view) {
        String username = etUser.getText().toString();
        String password = etPassword.getText().toString();

        // TODO: These could be enums depending on how many scripts will be called
        String type = "login";

        // Run the login script
        ServerManager serverManager = new ServerManager(this);
        serverManager.setPreferencesUtility(mPreferencesUtility);
        serverManager.execute(type, username, password);
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

}