package com.lostanimals.tracks.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.lostanimals.tracks.FeedActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.support.constraint.Constraints.TAG;

public class LoginTask extends AsyncTask<String, Integer, JSONObject> {
    @SuppressLint("StaticFieldLeak")
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
        Log.d(TAG, "onCancelled: LOGIN_TASK_LOGIN_TASK_LOGIN_TASK_LOGIN_TASK");
    }
}
