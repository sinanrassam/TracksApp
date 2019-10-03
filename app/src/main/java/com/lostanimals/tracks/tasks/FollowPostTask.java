package com.lostanimals.tracks.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.lostanimals.tracks.utils.ConnectionManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.lostanimals.tracks.utils.ConnectionManager.processRequest;

public class FollowPostTask extends AsyncTask<String, Integer, Boolean> {
	@SuppressLint ("StaticFieldLeak")
	private Context mContext;

	public FollowPostTask(Context context) {
		mContext = context;
	}
	
	@Override
	protected Boolean doInBackground(String... parameters) {
		boolean success = true;
		JSONObject json = null;
		if (!this.isCancelled()) {
			String postData = null;
			try {
				postData = ConnectionManager.postEncoder("follow-post", parameters);
				json = processRequest("follow.php", postData);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				success = false;
			}
		}
		return success;
	}
	
	@Override
	protected void onPreExecute() {
		Toast.makeText(mContext, "Loading posts", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onPostExecute(final Boolean success) {
		if (success) {
			Toast.makeText(mContext, "Post Followed", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(mContext, "Error following post", Toast.LENGTH_LONG).show();
		}
	}
}