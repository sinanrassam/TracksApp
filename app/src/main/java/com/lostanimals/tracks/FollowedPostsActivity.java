package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

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