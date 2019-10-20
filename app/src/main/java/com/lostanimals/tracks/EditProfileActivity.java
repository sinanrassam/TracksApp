package com.lostanimals.tracks;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");

        getSupportFragmentManager().beginTransaction().replace(R.id.editProfileContainer, new EditProfileFragment()).commit();

    }
}