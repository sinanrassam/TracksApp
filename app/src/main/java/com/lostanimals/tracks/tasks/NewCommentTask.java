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


public class NewCommentTask extends AsyncTask<String, Void, JSONObject> {
	@SuppressLint ("StaticFieldLeak")
	private Context mContext;
	
	public NewCommentTask(Context context) {
		mContext = context;
	}
	
	public NewCommentTask() {
		super();
	}
	
	@Override
	protected JSONObject doInBackground(String... parameters) {
		// Try encode and send the NEW_POST request.
		JSONObject json = null;
		try {
			String postData = ConnectionManager.postEncoder("new-comment", parameters);
			json = ConnectionManager.processRequest("comment.php", postData);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@Override
	protected void onPostExecute(JSONObject jsonObject) {
		// super.onPostExecute(jsonObject);
		try {
			if (jsonObject.get("response").equals("successful")) {
				Toast.makeText(mContext, "CommentEntry created", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(mContext, "CommentEntry creation error", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(mContext, "CommentEntry creation error", Toast.LENGTH_LONG).show();
		}
	}
}
