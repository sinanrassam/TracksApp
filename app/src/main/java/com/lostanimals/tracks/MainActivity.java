package com.lostanimals.tracks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button buttonRegister;
    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;
    private SQLiteHandler sqLiteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Test commit
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
