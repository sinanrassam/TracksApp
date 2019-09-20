package com.lostanimals.tracks;


import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

public class MyPostActivity extends AppCompatActivity {
	
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_posts);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		
	}
}