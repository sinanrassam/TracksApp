package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class FeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	
	private ActionBarDrawerToggle mToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);
		
		// Setup DrawerLayout and ActionBar
		DrawerLayout mDrawerLayout = findViewById(R.id.drawer);
		
		mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_action_bar,
				R.string.close_action_bar);
		mDrawerLayout.addDrawerListener(mToggle);
		mToggle.syncState();
		
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		NavigationView navigationView = findViewById(R.id.navigation_view);
		navigationView.setNavigationItemSelectedListener(this);
		
	}
	
	public void openNewPostActivity(View view) {
		startActivity(new Intent(this, NewPostActivity.class));
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
		int id = menuItem.getItemId();
		
		if (id == R.id.feed_nav) {
			startActivity(new Intent(this, FeedActivity.class));
		}
		
		if (id == R.id.myPosts_nav) {
			startActivity(new Intent(this, MyPostActivity.class));
		}
		
		if (id == R.id.logOut_nav) {
			startActivity(new Intent(this, LogoutActivity.class));
			finish();
		}
		if (id == R.id.myProfile_nav) {
			startActivity(new Intent(this, MyProfileActivity.class));
		}
		
		return false;
	}
}