package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import com.lostanimals.tracks.entries.PreferenceEntry;
import com.lostanimals.tracks.tasks.UpdateUserDetailsTask;
import com.lostanimals.tracks.utils.PreferencesUtility;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private String originalUsername, savedEmail, originalEmail, savedName, originalName;
    private TextView mUsername, mEmail, mName;
    private Button savedprofileButton, changedPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");

        getSupportFragmentManager().beginTransaction().replace(R.id.editProfileContainer, new EditProfileFragment()).commit();

        mUsername = (EditText) findViewById(R.id.username_edit);
        mUsername.setText(PreferencesUtility.getUserInfo().getUsername());
        mUsername.setEnabled(false);

        mName = findViewById(R.id.name_edit);
        mName.setText(PreferencesUtility.getUserInfo().getName());

        mEmail = findViewById(R.id.email_edit);
        mEmail.setText(PreferencesUtility.getUserInfo().getEmail());

        savedprofileButton = findViewById(R.id.saved_profile_button);
        savedprofileButton.setOnClickListener(this);
        changedPasswordButton = findViewById(R.id.change_password_Button);
        changedPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        originalUsername = PreferencesUtility.getUserInfo().getUsername();
        savedEmail = this.mEmail.getText().toString();
        originalEmail = PreferencesUtility.getUserInfo().getEmail();
        savedName = this.mName.getText().toString();
        originalName = PreferencesUtility.getUserInfo().getName();

        if (v.getId() == R.id.saved_profile_button) {
            if ((!savedName.equals(originalName)) || (!savedEmail.equals(originalEmail))) {
                new UpdateUserDetailsTask(this).execute(savedName, originalUsername, savedEmail, "", "");
            } else {
                Toast.makeText(getApplicationContext(), "No Changes Made on Name or Email", Toast.LENGTH_SHORT);
            }




//            if (savedName.equals(originalName)) {
//                if (savedEmail.equals(originalEmail)) {
//                    Toast.makeText(getApplicationContext(), "No Changes Made on Username, Name or Email", Toast.LENGTH_SHORT).show();
//                } else if (!savedEmail.equals(originalEmail)) {
//
//                    Toast.makeText(getApplicationContext(), "Changes made on EMAIL. Successful: " + PreferencesUtility.setUserInfo(new PreferenceEntry(originalName, originalUsername, savedEmail)), Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                if (savedEmail.equals(originalEmail)) {
//                    PreferencesUtility.setUserInfo(new PreferenceEntry(savedName, originalUsername, originalEmail));
//                    Toast.makeText(getApplicationContext(), "Changes made on NAME. Successful: " + PreferencesUtility.setUserInfo(new PreferenceEntry(savedName, originalUsername, originalEmail)), Toast.LENGTH_SHORT).show();
//                } else if (savedEmail != originalEmail) {
//                    PreferencesUtility.setUserInfo(new PreferenceEntry(savedName, originalUsername, savedEmail));
//                    Toast.makeText(getApplicationContext(), "Changes made on EMAIL and NAME. Successful: " + PreferencesUtility.setUserInfo(new PreferenceEntry(savedName, originalUsername, savedEmail)), Toast.LENGTH_SHORT).show();
//                }
//            }
        }
        else if(v.getId() == R.id.change_password_Button){
            Intent changePasswordIntent = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);
        }
    }
}