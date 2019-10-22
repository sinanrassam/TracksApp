package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class FeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FiltersFragment.FilterFragmentListener {

    public static final String GOOGLE_ACCOUNT = "google_account";
    private ActionBarDrawerToggle mToggle;
	private DrawerLayout mDrawerLayout;
	private ActionBar mActionBar;
	private FiltersFragment mFiltersFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);
        mActionBar = getSupportActionBar();
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

        mActionBar.setTitle("Feed");

        mFiltersFragment = new FiltersFragment();

        ImageButton editProfileButton = header.findViewById(R.id.editProfile);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_filter, menu);
        return true;
    }

	public void openNewPostActivity(View view) {
		startActivity(new Intent(this, NewPostActivity.class));
	}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (id == R.id.filters_button) {
            showFiltersDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent navigationIntent = null;
        mActionBar = getSupportActionBar();



        switch (menuItem.getItemId()) {
            case R.id.feed_nav:
                loadFragment(new FeedFragment());
                mActionBar.setTitle("Feed");
                break;
            case R.id.myPosts_nav:
                loadFragment(new MyPostsFragment());
                mActionBar.setTitle("My Posts");
                break;
            case R.id.settings_nav:
                navigationIntent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.myProfile_nav:
                loadFragment(new MyProfileActivity());
                mActionBar.setTitle("My Profile");
                break;
            case R.id.history_nav:
                loadFragment(new HistoryFragment());
                mActionBar.setTitle("Recently Viewed");
                break;
            case R.id.followedPosts_nav:
                loadFragment(new FollowedPostsFragment());
                mActionBar.setTitle("Followed Posts");
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

	@Override
	protected void onResume() {
		super.onResume();
	}

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    private void showFiltersDialog() {
	    FragmentManager fragmentManager = getSupportFragmentManager();
        mFiltersFragment.show(fragmentManager, "Filters Dialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(this, "CONFIRM CLICKED!!!", Toast.LENGTH_LONG);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FeedFragment frag =  (FeedFragment)fragmentManager.findFragmentById(R.id.fragment_feed);
        frag.refresh();
    }
}