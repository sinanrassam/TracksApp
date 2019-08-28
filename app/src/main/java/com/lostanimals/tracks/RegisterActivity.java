package com.lostanimals.tracks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setEtName((EditText) findViewById(R.id.register_et_name));
        setEtUsername((EditText) findViewById(R.id.register_et_username));
        setEtPassword((EditText) findViewById(R.id.register_et_password));
    }

    public void OnRegister(View view) {
        String name = etName.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();


        Log.d("OnRegister", "Name = " + name);
        Log.d("OnRegister", "username = " + username);
        Log.d("OnRegister", "password = " + password);

        // Run the register script
        BackgroundWorker bgWorker = new BackgroundWorker(this);
        bgWorker.execute("register", name, username, password);
    }

    public EditText getEtName() {
        return etName;
    }

    private void setEtName(EditText etName) {
        this.etName = etName;
    }

    public EditText getEtUsername() {
        return etUsername;
    }

    private void setEtUsername(EditText etUsername) {
        this.etUsername = etUsername;
    }

    public EditText getEtPassword() {
        return etPassword;
    }

    private void setEtPassword(EditText etPassword) {
        this.etPassword = etPassword;
    }

}