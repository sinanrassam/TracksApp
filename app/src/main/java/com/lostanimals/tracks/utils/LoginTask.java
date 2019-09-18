package com.lostanimals.tracks.utils;


import android.os.AsyncTask;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class LoginTask extends AsyncTask<String, Integer, JSONObject> {

    public LoginTask() {
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
        Log.d("LOGIN_TASK", "onCancelled: LOGIN_TASK_LOGIN_TASK_LOGIN_TASK_LOGIN_TASK");
    }
}
