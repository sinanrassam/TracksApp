package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.lostanimals.tracks.utils.GetCommentsTask;
import com.lostanimals.tracks.utils.PreferencesUtility;
import com.lostanimals.tracks.utils.UpdateFeedTask;

public class CommentsFragment extends ListFragment {
    private GetCommentsTask getComments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.feed_fragment, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setProgress(0);

        String name = PreferencesUtility.getUserInfo().getUsername();

        getComments = (GetCommentsTask) new GetCommentsTask(this, progressBar).execute();

        return view;
    }
}