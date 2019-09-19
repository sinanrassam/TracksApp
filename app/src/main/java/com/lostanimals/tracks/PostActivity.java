package com.lostanimals.tracks;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.lostanimals.tracks.utils.NewCommentTask;
import com.lostanimals.tracks.utils.PostEntry;
import com.lostanimals.tracks.utils.PostsUtility;
import com.lostanimals.tracks.utils.PreferencesUtility;

public class PostActivity extends AppCompatActivity {

    private int mPostPosition;
    private PostEntry mPostEntry;
    private TextView mPostTitleView, mPostDescView, mPostDateView, mPostAuthorView;
    private EditText mCommentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mPostPosition = getIntent().getIntExtra("position", 0);
        mPostEntry = PostsUtility.getPostEntry(mPostPosition);

        Fragment f = new CommentsFragment();
        Bundle data = new Bundle();
        data.putString("post_id", mPostEntry.getId());
        f.setArguments(data);

        mPostTitleView = findViewById(R.id.post_txt_title);
        mPostDescView = findViewById(R.id.post_et_desc);
        mPostDateView = findViewById(R.id.post_date);
        mPostAuthorView = findViewById(R.id.post_author);

        if (mPostEntry != null) {
            mPostTitleView.setText(mPostEntry.getPostTitle());

            mPostDescView.setText(mPostEntry.getPostDesc());
            mPostDateView.setText("Posted on: " + "DATE");
            mPostAuthorView.setText("By: " + mPostEntry.getUsername());
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, f).commit();

        Button mCommentBtn = findViewById(R.id.comment_btn);
        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mPostEntry != null) {
            if (mPostEntry.getUsername().equals(PreferencesUtility.getUserInfo().getUsername())) {
                getMenuInflater().inflate(R.menu.nav_popup, menu);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popup_found:
                return true;
            case R.id.popup_edit:
                return true;
            case R.id.popup_delete:
                return true;
        }

        return false;
    }

    private void addComment() {
        mCommentView = findViewById(R.id.comment_field);
        mCommentView.setError(null);
        String msg = mCommentView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid comment, if the user entered one.
        if (TextUtils.isEmpty(msg)) {
            mCommentView.setError(getString(R.string.error_field_required));
            focusView = mCommentView;
            cancel = true;
        }

        String username = PreferencesUtility.getUserInfo().getUsername();

        String post_id = mPostEntry.getId();
        if (cancel) {
            focusView.requestFocus();
        } else {
            NewCommentTask addCommentTask = new NewCommentTask(this);
            // TODO: Remove after fixing the unknown bug
            addCommentTask.execute(post_id, username, msg);
        }
    }
}
