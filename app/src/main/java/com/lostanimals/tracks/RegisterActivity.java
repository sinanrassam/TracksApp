package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.lostanimals.tracks.utils.ServerManager;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setEtName((EditText) findViewById(R.id.register_et_name));
        setEtUsername((EditText) findViewById(R.id.register_et_username));
        setEtPassword((EditText) findViewById(R.id.register_et_password));
    }

    public void OnRegister(View view) {
        String name = etName.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        // Run the register script
        ServerManager serverManager = new ServerManager(this);
        serverManager.execute("register", name, username, password);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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