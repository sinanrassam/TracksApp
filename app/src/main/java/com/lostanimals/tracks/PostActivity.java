package com.lostanimals.tracks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

    private void addComment() {
        mCommentView.setError(null);
        String msg = mCommentView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(msg)) {
            mCommentView.setError(getString(R.string.error_field_required));
            focusView = mCommentView;
            cancel = true;
        }

        String username = PreferencesUtility.getUserInfo().getUsername();


        if (cancel) {
            focusView.requestFocus();
        } else {
            NewCommentTask addCommentTask = new NewCommentTask(this);
            addCommentTask.execute("-1", username, msg);
        }
    }
}
