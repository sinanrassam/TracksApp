package com.lostanimals.tracks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText etUser, etPassword;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setup EditText fields to get properties from .xml

        setEtUser((EditText) findViewById(R.id.login_et_username));
        setEtPassword((EditText) findViewById(R.id.login_et_password));
        // etUser = findViewById(R.id.etLoginUsername);
        // etPassword = findViewById(R.id.etLoginPassword);
    }

    // This method is called when Login button is pressed
    public void OnLogin(View view) {
        // Get the text a user has entered.
        setUsername(getEtUser().toString());
        setPassword(getEtPassword().toString());


        //String username = etUser.getText().toString();
        //String password = etPassword.getText().toString();

        // TODO: These could be enums depending on how many scripts will be called
        String type = "login";

        // Run the login script
        BackgroundWorker bgWorker = new BackgroundWorker(this);
        bgWorker.execute(type, getUsername(), getPassword());
    }

    private String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
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