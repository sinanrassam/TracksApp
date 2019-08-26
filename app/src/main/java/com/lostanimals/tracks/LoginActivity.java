package com.lostanimals.tracks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUser, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setup EditText fields to get properties from .xml
        editTextUser = findViewById(R.id.etUsername);
        editTextPassword = findViewById(R.id.etPassword);
    }

    // This method is called when Login button is pressed
    public void OnLogin(View view) {
        // Get the text a user has entered.
        String username = editTextUser.getText().toString();
        String password = editTextPassword.getText().toString();

        // TODO: These could be enums depending on how many scripts will be called
        String type = "login";

        // Run the login script
        BackgroundWorker bgWorker = new BackgroundWorker(this);
        bgWorker.execute(type, username, password);
    }
}
