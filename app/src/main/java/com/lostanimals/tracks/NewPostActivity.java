package com.lostanimals.tracks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.lostanimals.tracks.utils.EditTask;
import com.lostanimals.tracks.utils.NewPostTask;
import com.lostanimals.tracks.utils.PostEntry;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class NewPostActivity extends AppCompatActivity {
    private EditText etTitle, etDescription;
    private boolean isEditTask;
    private String postID, postIsFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            isEditTask = b.getBoolean("isEditTask");
            postID = b.getString("postID");
            postIsFound = b.getString("isFound");
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // TODO: Do we need the action bar?
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etTitle = findViewById(R.id.post_et_post_title);
        etDescription = findViewById(R.id.post_et_desc);
    }

    public void onNewPost(View view) {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();

        if (!isEditTask) {
            NewPostTask newPostTask = new NewPostTask(this);
            newPostTask.execute(title, description, PreferencesUtility.getUserInfo().getUsername());
        }
        else {
            EditTask editTask = new EditTask(this);
            editTask.execute(postID, title, description, postIsFound);
        }
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
