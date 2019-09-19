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
import com.lostanimals.tracks.utils.*;

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
                onEditClicked();
                return true;
            case R.id.popup_delete:
                onDeleteClicked();
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

        if (mPostEntry.getFound().equals("0")) {
            builder.setMessage("Are you sure you want to mark the post as found? It will no longer be " +
                    "accessible in the main feed but you can view it in my posts.");
            builder.setTitle("Warning!");

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
        }
        else {
            builder.setMessage("This post has already been marked as found!");
            builder.setTitle("Attention!");

            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void markAsFound() {
        EditTask editTask = new EditTask(this);
        editTask.execute(mPostEntry.getId(), mPostEntry.getUsername(), mPostEntry.getPostDate(),
                mPostEntry.getTime(), mPostEntry.getPostTitle(), mPostEntry.getPostDesc(), "1");
    }

    // TODO: Complete onEditClicked
    private void onEditClicked() {

    }

    private void onDeleteClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to delete the post? Once deleted this cannot be " +
                "reversed.");
        builder.setTitle("Warning!");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletePost();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // TODO: Complete delete post method
    private void deletePost() {

    }
}
