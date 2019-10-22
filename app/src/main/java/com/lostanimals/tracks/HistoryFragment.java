package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.ListFragment;
import com.lostanimals.tracks.tasks.HistoryPostsTask;

import java.util.Queue;

public class HistoryFragment extends ListFragment {

    private HistoryPostsTask mHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.feed_fragment, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setProgress(0);

        Queue<String> historyQ = FeedFragment.getHistoryQ();


        Log.d("history", "Q in history queue" + historyQ);

        if (!(historyQ.size() == 0)) {
            new HistoryPostsTask(this, progressBar).execute("", "", "","","");

        } else {
            progressBar.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getContext(), PostActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}