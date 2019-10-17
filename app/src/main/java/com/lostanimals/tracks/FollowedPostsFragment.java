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
import com.lostanimals.tracks.tasks.GetFollowedPostsTask;
import com.lostanimals.tracks.utils.PreferencesUtility;

public class FollowedPostsFragment extends ListFragment {
	private SwipeRefreshLayout refreshLayout;
	private ProgressBar mProgressBar;
	private GetFollowedPostsTask task;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.followed_posts_fragment, container, false);
		mProgressBar = view.findViewById(R.id.progress_bar);

		refreshLayout = view.findViewById(R.id.pullToRefresh);
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				refresh();
				refreshLayout.setRefreshing(false);
			}
		});

		return view;
	}

	private void refresh() {
		task = new GetFollowedPostsTask(this, mProgressBar);
		task.execute(PreferencesUtility.getUserInfo().getUsername());
	}

	@Override
	public void onResume() {
		super.onResume();
		refresh();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getContext(), PostActivity.class);
        intent.putExtra("position", task.getActualId(position));
        startActivity(intent);
	}
}