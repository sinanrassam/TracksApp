package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import androidx.fragment.app.ListFragment;
import com.lostanimals.tracks.tasks.UpdatePostsTask;
import com.lostanimals.tracks.utils.PreferencesUtility;

public class MyPostsFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.feed_fragment, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setProgress(0);

        String username = PreferencesUtility.getUserInfo().getUsername();
        new UpdatePostsTask(this, progressBar).execute("", "", username);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getContext(), PostActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}