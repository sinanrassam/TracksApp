package com.lostanimals.tracks.tasks;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.lostanimals.tracks.FeedActivity;
import com.lostanimals.tracks.utils.ConnectionManager;
import com.lostanimals.tracks.utils.NotificationUtility;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class NewPostTask extends AsyncTask<String, Void, JSONObject> {
	@SuppressLint ("StaticFieldLeak")
	private Context mContext;
	
	public NewPostTask(Context context) {
		mContext = context;
	}
	
	
	@Override
	protected JSONObject doInBackground(String... parameters) {
		// Try encode and send the NEW_POST request.
		JSONObject json = null;
		try {
			String postData = ConnectionManager.postEncoder("new-post", parameters);
			json = ConnectionManager.processRequest("post.php", postData);
			Log.d("JSON", json.toString());
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@Override
	protected void onPostExecute(JSONObject jsonObject) {
		try {
			if (jsonObject.get("response").equals("successful")) {
				Intent feedIntent = new Intent(mContext, FeedActivity.class);
				feedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				
				PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, feedIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				
				NotificationUtility.createNotification(mContext, "Post Created Successfully", "", true, pendingIntent);
				NotificationUtility.displayNotification(0);
				mContext.startActivity(feedIntent);
			}
		} catch (JSONException e) {
			Toast.makeText(mContext, "Error Creating Post", Toast.LENGTH_LONG).show();
		}
		
		super.onPostExecute(jsonObject);
	}
}
