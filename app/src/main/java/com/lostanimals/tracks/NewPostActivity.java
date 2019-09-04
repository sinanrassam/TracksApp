package com.lostanimals.tracks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NewPostActivity extends AppCompatActivity {
    private EditText etTitle, etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        etTitle = (EditText) findViewById(R.id.post_et_post_title);
        etDescription = (EditText) findViewById(R.id.post_et_desc);
    }

    public void onNewPost(View view) {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String username = "USER";

        // Run the register script
        AttemptLogin bgWorker = new AttemptLogin(this);
        bgWorker.execute("new post", title, description, username);
    }
}
