package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.lostanimals.tracks.utils.GetCommentsTask;
import com.lostanimals.tracks.utils.PreferencesUtility;
import com.lostanimals.tracks.utils.UpdateFeedTask;

public class CommentsFragment extends ListFragment {

    public CommentsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.comments_fragment, container, false);
        TextView textView = view.findViewById(R.id.textView);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setProgress(0);

        String post_id = getArguments().getString("post_id");

        Log.d("Testing", post_id);

        new GetCommentsTask(this, textView, progressBar).execute(post_id);

        return view;
    }
}