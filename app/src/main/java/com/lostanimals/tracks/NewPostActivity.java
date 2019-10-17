package com.lostanimals.tracks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.lostanimals.tracks.tasks.EditTask;
import com.lostanimals.tracks.tasks.NewPostTask;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class NewPostActivity extends AppCompatActivity {
    static final int LOCATION_CHOOSE_REQUEST = 999;
    private EditText etTitle, etDescription;
    private boolean isEditTask;
    private String postID, postTitle, postDescription, postIsFound;
    private String location;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            isEditTask = b.getBoolean("isEditTask");
            postID = b.getString("postID");
            postTitle = b.getString("postTitle");
            postDescription = b.getString("postDesc");
            postIsFound = b.getString("isFound");
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        etTitle = findViewById(R.id.post_et_post_title);
        etDescription = findViewById(R.id.post_et_desc);

        if (isEditTask) {
            etTitle.setText(postTitle);
            etDescription.setText(postDescription);
        }

        Button backButton = this.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View ignored) {
                finish();
            }
        });

        ImageButton mapButton = this.findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseLocation();
            }
        });
    }

    private void chooseLocation() {
        Intent chooseOnMapIntent = new Intent(this, SetLocationActivity.class);
        startActivityForResult(chooseOnMapIntent, LOCATION_CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_CHOOSE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                location = String.valueOf(data.getParcelableExtra("point"));
                location = location.replace("lat/lng: (", "");
                location = location.replace(")", "");
            }
        }
    }

    public void onNewPost(View view) {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();

        if (!isEditTask) {
            NewPostTask newPostTask = new NewPostTask(this);
            newPostTask.execute(title, description, PreferencesUtility.getUserInfo().getUsername(), location);
        } else {
            EditTask editTask = new EditTask(this);
            editTask.execute(postID, title, description, postIsFound, location);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(NewPostActivity.this, FeedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
