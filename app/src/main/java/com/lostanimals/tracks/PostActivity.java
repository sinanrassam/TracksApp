package com.lostanimals.tracks;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
        Log.d("Post Position - oncreate", mPostPosition + "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mPostPosition = getIntent().getIntExtra("position", 0);

        mPostTitleView = findViewById(R.id.post_txt_title);
        mPostDescView = findViewById(R.id.post_et_desc);
        mPostDateView = findViewById(R.id.post_date);
        mPostAuthorView = findViewById(R.id.post_author);

        mPostEntry = PostsUtility.getPostEntry(mPostPosition);
        if (mPostEntry != null) {
            mPostTitleView.setText(mPostEntry.getPostTitle());

            mPostDescView.setText(mPostEntry.getPostDesc());
            mPostDateView.setText("Posted on: " + "DATE");
            mPostAuthorView.setText("By: " + mPostEntry.getUsername());
        }
        mCommentView = findViewById(R.id.comment_field);

        Button mSignInBtn = findViewById(R.id.comment_btn);
        mSignInBtn.setOnClickListener(new View.OnClickListener() {
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
                onFoundClicked();
                return true;
            case R.id.popup_edit:
                return true;
            case R.id.popup_delete:
                return true;
        }

        return false;
    }

    private void addComment() {
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

    private void onFoundClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to mark the post as found? It will no longer be " +
                "accessible in the main feed but you can view it in my posts.");
        builder.setTitle("Warning!");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                markAsFound();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void markAsFound() {

    }
}
