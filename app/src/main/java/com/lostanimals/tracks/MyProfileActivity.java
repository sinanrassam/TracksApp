package com.lostanimals.tracks;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class MyProfileActivity extends AppCompatActivity {
	
	private Button mEmailEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myprofile);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		TextView mUsername = findViewById(R.id.usernameProfile);
		TextView mName = findViewById(R.id.nameProfile);
		TextView mEmail = findViewById(R.id.emailProfile);
		
		
		mUsername.setText(PreferencesUtility.getUserInfo().getUsername());
		mName.setText(PreferencesUtility.getUserInfo().getName());
		mEmail.setText(PreferencesUtility.getUserInfo().getEmail());
		
		
		Button mEmailEdit = findViewById(R.id.EmailEdit);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("My Profile");
	}
	
	
}