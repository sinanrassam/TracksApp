package com.lostanimals.tracks;

import android.annotation.SuppressLint;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lostanimals.tracks.utils.LoginTask;
import com.lostanimals.tracks.utils.NewCommentTask;
import com.lostanimals.tracks.utils.PostEntry;
import com.lostanimals.tracks.utils.PreferencesUtility;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class PostEntryActivity extends AppCompatActivity {

    private PostEntry mPostEntry;
    private TextView mPostTitleView, mPostDescView, mPostDateView, mPostAuthorView;
    private EditText mCommentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_entry);

        mPostTitleView = findViewById(R.id.post_txt_title);
        mPostDescView = findViewById(R.id.post_et_desc);
        mPostDateView = findViewById(R.id.post_date);
        mPostAuthorView = findViewById(R.id.post_author);

//        mPostEntry = new PostEntry("title", "title");

//        mPostTitleView.setText(mPostEntry.getPostTitle());
//
//        mPostDescView.setText("mPostEntry.getPostDesc()");
//        mPostDateView.setText("Posted on: " + "mPostEntry.getPostDesc()");
//        mPostAuthorView.setText("By: " + "mPostEntry.getUsername()");

        mCommentView = findViewById(R.id.comment_field);

        Button mSignInBtn = findViewById(R.id.comment_btn);
        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComent();
            }
        });
    }

    private void addComent() {
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
