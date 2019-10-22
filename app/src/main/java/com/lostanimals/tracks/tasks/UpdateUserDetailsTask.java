package com.lostanimals.tracks.tasks;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.lostanimals.tracks.entries.PreferenceEntry;
import com.lostanimals.tracks.utils.ConnectionManager;
import com.lostanimals.tracks.utils.PreferencesUtility;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UpdateUserDetailsTask extends AsyncTask<String, Integer, JSONObject> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private String savedName, savedEmail;
    private String originalUsername=PreferencesUtility.getUserInfo().getUsername();


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
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject data) {
        try {
            if (data.get("response").equals("successful")) {
                Toast.makeText(mContext, "Post Successfully Updated", Toast.LENGTH_SHORT).show();
                PreferencesUtility.setUserInfo(new PreferenceEntry(savedName,originalUsername, savedEmail));
            }
        } catch (JSONException e) {
            Toast.makeText(mContext, "Error Updating User Details", Toast.LENGTH_SHORT).show();
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
