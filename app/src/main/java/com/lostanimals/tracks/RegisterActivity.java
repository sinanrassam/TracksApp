package com.lostanimals.tracks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etUsername, etPassword;
    private String name, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setEtName((EditText) findViewById(R.id.register_et_name));
        setEtUsername((EditText) findViewById(R.id.register_et_username));
        setEtPassword((EditText) findViewById(R.id.register_et_password));
    }

    public void OnRegister(View view) {
        setName(getName());
        setPassword(getPassword());

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}