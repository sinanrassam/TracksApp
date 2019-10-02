package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import com.lostanimals.tracks.utils.PreferencesUtility;
import java.util.Objects;

import static android.app.PendingIntent.getActivity;


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

		View header = navigationView.getHeaderView(0);
		AppCompatTextView mUsername = header.findViewById(R.id.usernameHeader);
		AppCompatTextView mEmail = header.findViewById(R.id.emailHeader);
		
		mUsername.setText(PreferencesUtility.getUserInfo().getUsername());
		mEmail.setText(PreferencesUtility.getUserInfo().getEmail());

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
		ActionBar actionBar = getSupportActionBar();
		switch (menuItem.getItemId()) {
			case R.id.feed_nav:
				//navigationIntent = new Intent(this, FeedActivity.class);
				loadFragment(new FeedFragment());
				actionBar.setTitle("Feed");
				break;
			case R.id.myPosts_nav:
				//navigationIntent = new Intent(this, MyPostActivity.class);
				loadFragment(new MyPostActivity());
				actionBar.setTitle("My Posts");
				break;
			case R.id.logOut_nav:
				navigationIntent = new Intent(this, LogoutActivity.class);
				break;
			case R.id.myProfile_nav:
				//navigationIntent = new Intent(this, MyProfileActivity.class);
				loadFragment(new MyProfileActivity());
				actionBar.setTitle("My Profile");
				break;
		}
		
		if (navigationIntent != null) {
			startActivity(navigationIntent);
			if (navigationIntent.getComponent().getClassName().equals("LogoutActivity")) {
				finish();
			}
		}
		
		mDrawerLayout.closeDrawers();
		
		// TODO: Why hardcode a false return?
		return false;
	}
	public void loadFragment(Fragment fragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.frame, fragment);
		transaction.commit();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
}