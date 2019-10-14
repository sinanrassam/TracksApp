package com.lostanimals.tracks.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.lostanimals.tracks.utils.ConnectionManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.lostanimals.tracks.utils.ConnectionManager.processRequest;

public class FollowPostTask extends AsyncTask<String, Integer, JSONObject> {
	@SuppressLint ("StaticFieldLeak")
	private Context mContext;
	private String mMsg;

	public FollowPostTask(Context context) {
		mContext = context;
	}
	
	@Override
	protected JSONObject doInBackground(String... parameters) {
		JSONObject json = null;
		mMsg = parameters[2];
		if (!this.isCancelled()) {
			String postData;
			try {
				postData = ConnectionManager.postEncoder(mMsg + "follow-post", parameters);
				json = processRequest("follow.php", postData);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@Override
	protected void onPostExecute(JSONObject data) {
		Log.d("Data", data.toString());
		try {
			if (data.get("response").equals("successful")) {
				Toast.makeText(mContext, "Post " + mMsg + "followed", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(mContext, data.get("reason").toString(), Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(mContext, "Error " + mMsg + "following post", Toast.LENGTH_LONG).show();
		}
	}
}