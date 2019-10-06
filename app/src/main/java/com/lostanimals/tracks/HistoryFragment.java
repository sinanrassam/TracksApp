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

import com.lostanimals.tracks.tasks.UpdatePostsTask;

import java.util.ArrayList;
import java.util.Queue;


public class HistoryFragment extends ListFragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.feed_fragment, container, false);
		ProgressBar progressBar = view.findViewById(R.id.progress_bar);
		progressBar.setProgress(0);

		Queue<String> id = FeedFragment.getHistoryQ();


		Log.d("history","Q in history queue"+id);

        if(id.size()==0)
        {
            System.out.println("Nothin in history");

        }
        else {
            for(String s : id) {
                System.out.println(s);
                new UpdatePostsTask(this, progressBar).execute("",s,"");

            }
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