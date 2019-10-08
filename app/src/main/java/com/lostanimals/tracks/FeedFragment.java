package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import androidx.fragment.app.ListFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lostanimals.tracks.entries.PostEntry;
import com.lostanimals.tracks.tasks.UpdatePostsTask;
import com.lostanimals.tracks.utils.PostsUtility;

import java.util.LinkedList;
import java.util.Queue;

public class FeedFragment extends ListFragment {
	private SwipeRefreshLayout refreshLayout;
	public ProgressBar progressBar;
	private static Queue<String> historyQ = new LinkedList<>();
	private PostEntry mPostEntry;
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
		mPostEntry = PostsUtility.getPostEntry(position);
		String post_id = mPostEntry.getId();
		//String post_id=Long.toString(id);
		Log.d("QUEUE","post id" +post_id);
		addHistory(post_id);
		startActivity(intent);
	}

	public void addHistory(String id)
	{
		if(!historyQ.contains(id)) {
			historyQ.add(id);
		}
		Log.d("QUEUE","Current queue" +historyQ);
	}

	public static Queue<String> getHistoryQ() {
		return historyQ;
	}

	private void refresh() {
		new UpdatePostsTask(this, progressBar).execute("","","");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		refresh();
	}
}