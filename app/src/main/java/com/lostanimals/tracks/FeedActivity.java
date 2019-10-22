package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;


public class FeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String GOOGLE_ACCOUNT = "google_account";
    private ActionBarDrawerToggle mToggle;
    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = getSupportActionBar();
        setContentView(R.layout.activity_feed);

        // Test data:
//        PreferencesUtility.setDarkMode(true);
//
//        if (PreferencesUtility.getUserInfo().isDarkModeEnabled()) {
//            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
        final Switch aSwitch = findViewById(R.id.switch_toggle);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //if day mode is enabled, set night mode using AppCompatDelegate class.
                    //		Toast.makeText(this, "Night selected", Toast.LENGTH_SHORT).show();
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    //if night mode is enabled, set day mode using AppCompatDelegate class.
                    //Toast.makeText(this, "Day mode selected", Toast.LENGTH_SHORT).show();
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

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

        ImageButton editProfileButton = header.findViewById(R.id.editProfile);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
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

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }
}