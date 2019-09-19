package com.lostanimals.tracks;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lostanimals.tracks.utils.PreferencesUtility;
import com.lostanimals.tracks.utils.RegisterTask;

import java.util.Objects;

public class MyProfileActivity extends AppCompatActivity {

   private TextView mEmail;
   private Button mEmailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mEmail = (TextView)findViewById(R.id.emailProfile);

        mEmail.setText(PreferencesUtility.getUserInfo().getEmail());

        Button mEmailEdit = findViewById(R.id.EmailEdit);
        mEmailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Profile");
    }

    private void attemptRegister() {
        String email ="editemail@edit.com";
        RegisterTask registerTask = new RegisterTask(this);
        registerTask.execute(email);
    }

}