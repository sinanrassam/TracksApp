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
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class FeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	
	private ActionBarDrawerToggle mToggle;
	private DrawerLayout mDrawerLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);

		mDrawerLayout = findViewById(R.id.drawer);

		mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_action_bar,
				R.string.close_action_bar);
		mDrawerLayout.addDrawerListener(mToggle);
		mToggle.syncState();
		
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		NavigationView navigationView = findViewById(R.id.navigation_view);
		navigationView.setNavigationItemSelectedListener(this);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Feed");
		
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
		Intent navigationIntent = null;
		
		switch (menuItem.getItemId()) {
			case R.id.feed_nav:
				navigationIntent = new Intent(this, FeedActivity.class);
				break;
			case R.id.myPosts_nav:
				navigationIntent = new Intent(this, MyPostActivity.class);
				break;
			case R.id.logOut_nav:
				navigationIntent = new Intent(this, LogoutActivity.class);
				break;
			case R.id.myProfile_nav:
				navigationIntent = new Intent(this, MyProfileActivity.class);
				break;
			case R.id.map_nav:
				navigationIntent = new Intent(this, MapsActivity.class);
		}
		
		if (navigationIntent != null) {
			startActivity(navigationIntent);
			if (navigationIntent.getComponent().getClassName().equals("LogoutActivity")) {
				finish();
			}
		}
		
		mDrawerLayout.closeDrawers();
		return false;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (PreferencesUtility.getUserInfo().getUsername().equals("")) {
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		}
	}
}