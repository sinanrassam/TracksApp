package com.lostanimals.tracks;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.lostanimals.tracks.tasks.GetCommentsTask;
import com.lostanimals.tracks.tasks.UpdateFeedTask;

public class FeedFragment extends ListFragment {
	private SwipeRefreshLayout refreshLayout;
	private ProgressBar progressBar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.feed_fragment, container, false);
		progressBar = view.findViewById(R.id.progress_bar);
		progressBar.setProgress(0);
		
		refreshLayout = view.findViewById(R.id.pullToRefresh);
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				refresh();
				refreshLayout.setRefreshing(false);
			}
		});
		
		refresh();
		
		return view;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getContext(), PostActivity.class);
		intent.putExtra("position", position);
		startActivity(intent);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	private void refresh() {
		new UpdateFeedTask(this, progressBar).execute("", "");
	}
}