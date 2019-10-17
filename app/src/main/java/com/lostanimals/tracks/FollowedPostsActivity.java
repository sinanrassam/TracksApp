package com.lostanimals.tracks;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class FollowedPostsActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_followed_posts);
		
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Followed Posts");
	}

}