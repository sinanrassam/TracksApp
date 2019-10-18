package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import androidx.fragment.app.ListFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lostanimals.tracks.tasks.UpdatePostsTask;
import com.lostanimals.tracks.utils.PreferencesUtility;

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
	
	private void refresh() {
		new UpdatePostsTask(this, progressBar).execute("", PreferencesUtility.getUserInfo().getUsername(), "",
                "", PreferencesUtility.getFiltersCommand());
	}
	
	@Override
	public void onResume() {
		super.onResume();
		refresh();
	}
}