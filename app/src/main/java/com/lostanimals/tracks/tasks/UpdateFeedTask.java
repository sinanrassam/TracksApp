package com.lostanimals.tracks.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.lostanimals.tracks.FeedFragment;
import com.lostanimals.tracks.MyPostsFragment;
import com.lostanimals.tracks.entries.PostEntry;
import com.lostanimals.tracks.utils.ConnectionManager;
import com.lostanimals.tracks.utils.PostsUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lostanimals.tracks.utils.ConnectionManager.processRequest;

public class UpdateFeedTask extends AsyncTask<String, Integer, Boolean> {
	public SimpleAdapter adapter;
	@SuppressLint ("StaticFieldLeak")
	private Context mContext;
	@SuppressLint ("StaticFieldLeak")
	private ProgressBar mProgressBar;
	private FeedFragment mFragment;
	private List<Map<String, String>> mPostList = new ArrayList<>();
	
	public UpdateFeedTask(FeedFragment activity, ProgressBar progressBar) {
		this.mFragment = activity;
		this.mContext = mFragment.getContext();
		this.mProgressBar = progressBar;
	}
	
	@Override
	protected Boolean doInBackground(String... parameters) {
		JSONObject json = null;
		if (!this.isCancelled()) {
			String postData;
			try {
				postData = ConnectionManager.postEncoder("get-posts", parameters);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			}
			
			try {
				json = processRequest("post.php", postData);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		if (json != null) {
			PostsUtility.clear();
			mPostList = new ArrayList<>();
			try {
				JSONArray jsonArray = (JSONArray) json.get("posts");
				int index = 0;
				
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					String id = (String) jsonObject.get("id");
					String title = (String) jsonObject.get("title");
					String desc = (String) jsonObject.get("description");
					String username = (String) jsonObject.get("username");
					String date = (String) jsonObject.get("post_date");
					String time = (String) jsonObject.get("post_time");
					String found = (String) jsonObject.get("found");
					
					if (found.equals("0")) {
						PostsUtility.addPostEntry(index, new PostEntry(id, title, desc, username, date, time, found));
						
						Map<String, String> post = new HashMap<>(2);
						
						post.put("Title", PostsUtility.getPostEntry(index).getPostTitle());
						post.put("Desc", PostsUtility.getPostEntry(index).getPostDesc());
						
						mPostList.add(index, post);
						++index;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected void onPreExecute() {
		Toast.makeText(mContext, "Fetching posts", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onPostExecute(final Boolean success) {
		if (success) {
			Toast.makeText(mContext, "Posts refreshed.", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(mContext, "Error. Please check network.", Toast.LENGTH_LONG).show();
		}
		
		mProgressBar.setVisibility(View.GONE);
		SimpleAdapter adapter = new SimpleAdapter(mContext, mPostList,
				android.R.layout.simple_list_item_2,
				new String[] {"Title", "Desc"},
				new int[] {android.R.id.text1, android.R.id.text2});
		mFragment.setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		if (this.mProgressBar != null) {
			mProgressBar.setProgress(values[0]);
		}
	}
}