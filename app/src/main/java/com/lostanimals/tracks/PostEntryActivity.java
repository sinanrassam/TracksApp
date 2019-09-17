package com.lostanimals.tracks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.lostanimals.tracks.utils.PostEntry;

public class PostEntryActivity extends AppCompatActivity {

    private PostEntry mPostEntry;
    private TextView mPostTitleView, mPostDescView, mPostDateView, mPostAuthorView;

    public PostEntryActivity() {
        super();
    }

    public PostEntryActivity(PostEntry postEntry) {
        this();
        mPostEntry = postEntry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_entry);

        mPostTitleView = findViewById(R.id.post_txt_title);
        mPostDescView = findViewById(R.id.post_et_desc);
        mPostDateView = findViewById(R.id.post_date);
        mPostAuthorView = findViewById(R.id.post_author);

//        mPostEntry = new PostEntry("title", "title");

        mPostTitleView.setText(mPostEntry.getPostTitle());

        mPostDescView.setText("mPostEntry.getPostDesc()");
        mPostDateView.setText("Posted on: " + "mPostEntry.getPostDesc()");
        mPostAuthorView.setText("By: " + "mPostEntry.getUsername()");
    }
}
