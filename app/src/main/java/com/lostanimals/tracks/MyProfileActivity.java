package com.lostanimals.tracks;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class MyProfileActivity extends AppCompatActivity {
	private TextView mUsername, mName, mEmail;
	// private Button mEmailEdit;
	
	@SuppressLint ("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myprofile);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		mUsername = findViewById(R.id.usernameProfile);
		mName = findViewById(R.id.nameProfile);
		mEmail = findViewById(R.id.emailProfile);
		
		mUsername.setText("Your username is: " + PreferencesUtility.getUserInfo().getUsername());
		mName.setText("Hi, " + PreferencesUtility.getUserInfo().getName()+"!");
		mEmail.setText("Your email address is: " + PreferencesUtility.getUserInfo().getEmail());
		
		// Button mEmailEdit = findViewById(R.id.EmailEdit);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("My Profile");
	}
}