package com.lostanimals.tracks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.ListFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lostanimals.tracks.tasks.GetCommentsTask;

public class CommentsFragment extends ListFragment {
	private TextView mTextView;
	private ProgressBar mProgressBar;
	private String post_id;
	
	public CommentsFragment() {
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.comments_fragment, container, false);
		mTextView = view.findViewById(R.id.textView);
		mProgressBar = view.findViewById(R.id.progress_bar);
		mProgressBar.setProgress(0);
		
		final SwipeRefreshLayout refreshLayout = view.findViewById(R.id.pullToRefresh);
		
		post_id = getArguments().getString("post_id");
		
		refresh();
		
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				refresh();
				refreshLayout.setRefreshing(false);
			}
		});
		return view;
	}
	
	void refresh() {
		new GetCommentsTask(this, mTextView, mProgressBar).execute(post_id);
	}
}