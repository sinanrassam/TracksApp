package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.lostanimals.tracks.utils.PreferencesUtility;
import com.lostanimals.tracks.utils.UpdateFeedTask;
import com.lostanimals.tracks.utils.UpdateMyPostsTask;

public class MyPostsFragment extends ListFragment {
    private UpdateMyPostsTask updateFeed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.feed_fragment, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setProgress(0);


        String username = PreferencesUtility.getUserInfo().getUsername();
        updateFeed = (UpdateMyPostsTask) new UpdateMyPostsTask(this, progressBar).execute("4",username);

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        updateFeed.execute("4");
//    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getContext(), PostActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}