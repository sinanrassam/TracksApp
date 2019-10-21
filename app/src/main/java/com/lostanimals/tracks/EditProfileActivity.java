package com.lostanimals.tracks;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private TextView mUsername, mEmail, mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");

        getSupportFragmentManager().beginTransaction().replace(R.id.editProfileContainer, new EditProfileFragment()).commit();
    }
}