package com.lostanimals.tracks.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.lostanimals.tracks.entries.PreferenceEntry;
import com.lostanimals.tracks.utils.ConnectionManager;
import com.lostanimals.tracks.utils.PreferencesUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginTask extends AsyncTask<String, Integer, JSONObject> {
	private Context mContext;

	public LoginTask(Context context) {
		this.mContext = context;
	}
	
	@Override
	protected JSONObject doInBackground(String... parameters) {
		// Try encode and send the LOGIN request.
		JSONObject json = null;
		try {
			String postData = ConnectionManager.postEncoder("login", parameters);
			json = ConnectionManager.processRequest("user.php", postData);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@Override
	protected void onPostExecute(JSONObject data) {
		try {
			if (data.get("response").equals("successful")) {
				PreferencesUtility.setUserInfo(ConnectionManager.login((JSONObject) data.get("details")));
			} else {
				Toast.makeText(mContext, (String) data.get("reason"), Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		Log.d("LOGIN_TASK", "onCancelled: LOGIN_TASK_LOGIN_TASK_LOGIN_TASK_LOGIN_TASK");
	}
}
