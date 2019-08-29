package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText etUser, etPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setEtUser((EditText) findViewById(R.id.login_et_username));
        setEtPassword((EditText) findViewById(R.id.login_et_password));
    }

    // This method is called when Login button is pressed
    public void OnLogin(View view) {
        // Get the text a user has entered.
        String username = etUser.getText().toString();
        String password = etPassword.getText().toString();

        // TODO: These could be enums depending on how many scripts will be called
        String type = "login";

        // Run the login script
        BackgroundWorker bgWorker = new BackgroundWorker(this);
        bgWorker.execute(type, username, password);
        // if (bgWorker.execute(type, username, password).getStatus() == AsyncTask.Status.FINISHED && )
    }

    private EditText getEtUser() {
        return etUser;
    }

    private void setEtUser(EditText etUser) {
        this.etUser = etUser;
    }

    private EditText getEtPassword() {
        return etPassword;
    }

    private void setEtPassword(EditText etPassword) {
        this.etPassword = etPassword;
    }

    public void openRegisterActivity(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }


    /**
     * Placeholder for moving ontop feed activity when user is signed in.
     */
    public void openFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
    }

}