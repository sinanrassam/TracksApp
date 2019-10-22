package com.lostanimals.tracks.tasks;


import android.annotation.SuppressLint;
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

public class UpdateUserDetailsTask extends AsyncTask<String, Integer, JSONObject> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;


    public UpdateUserDetailsTask(Context context) {
        mContext = context;
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {
        // Try encode and send the LOGIN request.
        JSONObject json = null;
        try {
            String postData = ConnectionManager.postEncoder("update", parameters);
            json = ConnectionManager.processRequest("user.php", postData);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        Log.d("background", "test");
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject data) {

        Log.d("onPostExecute", "test");
        Log.d("onPostExecute", data.toString());
        String name = null, email = null, username = null;
        try {
            if (data.get("response").equals("successful")) {
                JSONObject object = data.getJSONObject("details");
                Log.d("hi", object.toString());
                Toast.makeText(mContext, "Post Successfully Updated", Toast.LENGTH_SHORT).show();
                 name = object.getString("name");
                 email = object.getString("email");
                username = object.getString("username");
            }
        } catch (JSONException e) {
            Toast.makeText(mContext, "Error Updating User Details", Toast.LENGTH_SHORT).show();
        }
        if (username != null) {
            PreferencesUtility.setUserInfo(new PreferenceEntry(name, username, email));
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
