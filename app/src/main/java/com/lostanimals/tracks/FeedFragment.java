package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.lostanimals.tracks.utils.PostsUtility;
import com.lostanimals.tracks.utils.PreferencesUtility;
import com.lostanimals.tracks.utils.UpdateFeedTask;

public class FeedFragment extends ListFragment {
    private UpdateFeedTask updateFeed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.feed_fragment, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setProgress(0);

        // TODO: Nitesh:
        String user = PreferencesUtility.getUserInfo().getUsername();

        updateFeed = (UpdateFeedTask) new UpdateFeedTask(this, progressBar).execute("4");

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        updateFeed.execute("4");
//    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        PostActivity postEntryActivity = new PostActivity();

        postEntryActivity.setPostPosition(position);

        Intent intent = new Intent(getContext(), postEntryActivity.getClass());
        startActivity(intent);
    }
}