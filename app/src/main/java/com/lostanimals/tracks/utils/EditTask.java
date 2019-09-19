package com.lostanimals.tracks.utils;


import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

// TODO: Create the edit activity and call this task with the correct parameters. Copy LoginActivity and FeedFrag
public class EditTask extends AsyncTask<String, Integer, JSONObject> {

    public EditTask() {
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {
        // Try encode and send the LOGIN request.
        JSONObject json = null;
        try {
            String postData = ConnectionManager.postEncoder("edit", parameters);
            json = ConnectionManager.processRequest("post2.php", postData);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject data) {
        try {
            PreferencesUtility.setUserInfo(ConnectionManager.login((JSONObject) data.get("details")));
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
        Log.d("EDIT_TASK", "onCancelled: EDIT_TASK_EDIT_TASK_EDIT_TASK_EDIT_TASK_EDIT_TASK");
    }
}
