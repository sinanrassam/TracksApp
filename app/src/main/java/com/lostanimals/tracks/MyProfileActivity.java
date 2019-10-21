package com.lostanimals.tracks;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class MyProfileActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView mUsername = findViewById(R.id.usernameProfile);
        TextView mName = findViewById(R.id.nameProfile);
        TextView mEmail = findViewById(R.id.emailProfile);

        mUsername.setText("Your username is: " + PreferencesUtility.getUserInfo().getUsername());
        mName.setText("Hi, " + PreferencesUtility.getUserInfo().getName() + "!");
        mEmail.setText("Your email address is: " + PreferencesUtility.getUserInfo().getEmail());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Profile");
    }
}