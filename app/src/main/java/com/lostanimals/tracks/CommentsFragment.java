package com.lostanimals.tracks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.lostanimals.tracks.utils.GetCommentsTask;

import static android.support.v4.content.ContextCompat.getSystemService;

public class CommentsFragment extends ListFragment {
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private String post_id;
    private LinearLayout mainLayout;

    public CommentsFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.comments_fragment, container, false);
        mTextView = view.findViewById(R.id.textView);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setProgress(0);

        post_id = getArguments().getString("post_id");

        refresh();

        return view;
    }

    public void refresh() {
        new GetCommentsTask(this, mTextView, mProgressBar).execute(post_id);
    }
}