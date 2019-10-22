package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.navigation.NavigationView;
import com.lostanimals.tracks.utils.PreferencesUtility;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FeedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> dataAdapter;

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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Feed");
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
            case R.id.followedPosts_nav:
                navigationIntent = new Intent(this, FollowedPostsActivity.class);
                break;
        }

        if (navigationIntent != null) {
            startActivity(navigationIntent);
            if (navigationIntent.getComponent().getClassName().equals("LogoutActivity")) {
                finish();
            }
        }

        mDrawerLayout.closeDrawers();

        // TODO: Why hardcode a false return? Yup
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showFiltersDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FiltersFragment filtersFragment = new FiltersFragment();
        filtersFragment.show(fragmentManager, "Filters Dialog");

		/*Spinner sortingSpinner = (Spinner) findViewById(R.id.sorting_spinner);
		sortingSpinner.setOnItemSelectedListener(mSpinnerHandler);
		ArrayAdapter<String> adapter_state = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item,  R.id.sorting_spinner);
		adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sortingSpinner.setAdapter(adapter_state);*/
    }

    public void onSpeciesCheckboxClicked(@NotNull View v) {
        // Get references of the CheckBoxes
        CheckBox cb_dog = (CheckBox) findViewById(R.id.species_checkbox_dog);
        CheckBox cb_cat = (CheckBox) findViewById(R.id.species_checkbox_cat);
        CheckBox cb_bird = (CheckBox) findViewById(R.id.species_checkbox_bird);
        CheckBox cb_rabbit = (CheckBox) findViewById(R.id.species_checkbox_rabbit);
        CheckBox cb_horse = (CheckBox) findViewById(R.id.species_checkbox_horse);
        CheckBox cb_cow = (CheckBox) findViewById(R.id.species_checkbox_cow);
        CheckBox cb_sheep = (CheckBox) findViewById(R.id.species_checkbox_sheep);
        CheckBox cb_goat = (CheckBox) findViewById(R.id.species_checkbox_goat);
        CheckBox cb_guniea_pig = (CheckBox) findViewById(R.id.species_checkbox_guinea_pig);
        CheckBox cb_turtle = (CheckBox) findViewById(R.id.species_checkbox_turtle);
        CheckBox cb_other = (CheckBox) findViewById(R.id.species_checkbox_other);

        // If the view is now checked
        boolean checked = ((CheckBox) v).isChecked();

        switch (v.getId()) {
            case R.id.species_checkbox_dog:
                if (checked) {
                    PreferencesUtility.setmSpeciesDog("'Dog'");
                } else {
                    PreferencesUtility.setmSpeciesDog("");
                }
                break;
            case R.id.species_checkbox_cat:
                if (checked) {
                    PreferencesUtility.setmSpeciesCat("'Cat'");
                } else {
                    PreferencesUtility.setmSpeciesCat("");
                }
                break;
            case R.id.species_checkbox_bird:
                if (checked) {
                    PreferencesUtility.setmSpeciesBird("'Bird'");
                } else {
                    PreferencesUtility.setmSpeciesBird("");
                }
                break;
            case R.id.species_checkbox_rabbit:
                if (checked) {
                    PreferencesUtility.setmSpeciesRabbit("'Rabbit'");
                } else {
                    PreferencesUtility.setmSpeciesRabbit("");
                }
                break;
            case R.id.species_checkbox_horse:
                if (checked) {
                    PreferencesUtility.setmSpeciesHorse("'Horse'");
                } else {
                    PreferencesUtility.setmSpeciesHorse("");
                }
                break;
            case R.id.species_checkbox_cow:
                if (checked) {
                    PreferencesUtility.setmSpeciesCow("'Cow'");
                } else {
                    PreferencesUtility.setmSpeciesCow("");
                }
                break;
            case R.id.species_checkbox_sheep:
                if (checked) {
                    PreferencesUtility.setmSpeciesSheep("'Sheep'");
                } else {
                    PreferencesUtility.setmSpeciesSheep("");
                }
                break;
            case R.id.species_checkbox_goat:
                if (checked) {
                    PreferencesUtility.setmSpeciesGoat("'Goat'");
                } else {
                    PreferencesUtility.setmSpeciesGoat("");
                }
                break;
            case R.id.species_checkbox_guinea_pig:
                if (checked) {
                    PreferencesUtility.setmSpeciesGuineaPig("'Guinea Pig'");
                } else {
                    PreferencesUtility.setmSpeciesGuineaPig("");
                }
                break;
            case R.id.species_checkbox_turtle:
                if (checked) {
                    PreferencesUtility.setmSpeciesTurtle("'Turtle'");
                } else {
                    PreferencesUtility.setmSpeciesTurtle("");
                }
                break;
            case R.id.species_checkbox_other:
                if (checked) {
                    PreferencesUtility.setmSpeciesOther("'Other'");
                } else {
                    PreferencesUtility.setmSpeciesOther("");
                }
                break;
        }
    }

    public void onColourCheckboxClicked(@NotNull View v) {
        CheckBox cb_black = (CheckBox) findViewById(R.id.colour_checkbox_black);
        CheckBox cb_white = (CheckBox) findViewById(R.id.colour_checkbox_white);
        CheckBox cb_grey = (CheckBox) findViewById(R.id.colour_checkbox_grey);
        CheckBox cb_brown = (CheckBox) findViewById(R.id.colour_checkbox_brown);
        CheckBox cb_gold = (CheckBox) findViewById(R.id.colour_checkbox_gold);
        CheckBox cb_red = (CheckBox) findViewById(R.id.colour_checkbox_red);
        CheckBox cb_other = (CheckBox) findViewById(R.id.colour_checkbox_other);

        boolean checked = ((CheckBox) v).isChecked();

        switch (v.getId()) {
            case R.id.colour_checkbox_black:
                if (checked) {
                    PreferencesUtility.setmColourBlack("'Black'");
                } else {
                    PreferencesUtility.setmColourBlack("");
                }
                break;
            case R.id.colour_checkbox_white:
                if (checked) {
                    PreferencesUtility.setmColourWhite("'White'");
                } else {
                    PreferencesUtility.setmColourWhite("");
                }
                break;
            case R.id.colour_checkbox_grey:
                if (checked) {
                    PreferencesUtility.setmColourGrey("'Grey'");
                } else {
                    PreferencesUtility.setmColourGrey("");
                }
                break;
            case R.id.colour_checkbox_brown:
                if (checked) {
                    PreferencesUtility.setmColourBrown("'Brown'");
                } else {
                    PreferencesUtility.setmColourBrown("");
                }
                break;
            case R.id.colour_checkbox_gold:
                if (checked) {
                    PreferencesUtility.setmColourGold("'Gold'");
                } else {
                    PreferencesUtility.setmColourGold("");
                }
                break;
            case R.id.colour_checkbox_red:
                if (checked) {
                    PreferencesUtility.setmColourRed("'Red'");
                } else {
                    PreferencesUtility.setmColourRed("");
                }
                break;
            case R.id.colour_checkbox_other:
                if (checked) {
                    PreferencesUtility.setmColourOther("'Other'");
                } else {
                    PreferencesUtility.setmColourOther("");
                }
                break;
        }
    }

    public void onChippedCheckboxClicked(@NotNull View v) {
        CheckBox cb_chipped = (CheckBox) findViewById(R.id.chipped_checkbox_yes);
        CheckBox cb_not_chipped = (CheckBox) findViewById(R.id.chipped_checkbox_no);

        boolean checked = ((CheckBox) v).isChecked();

        if (v.getId() == R.id.chipped_checkbox_yes) {
            if (checked) {
                PreferencesUtility.setmMicroYes("'1'");
            } else {
                PreferencesUtility.setmMicroYes("");
            }
        } else {
            if (checked) {
                PreferencesUtility.setmMicroNo("'0'");
            } else {
                PreferencesUtility.setmMicroNo("");
            }
        }
    }
}