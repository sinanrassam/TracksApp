package com.lostanimals.tracks;

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
}