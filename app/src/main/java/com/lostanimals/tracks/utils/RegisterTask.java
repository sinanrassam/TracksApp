package com.lostanimals.tracks.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import com.lostanimals.tracks.FeedActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegisterTask extends AsyncTask<String, Void, JSONObject> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private PreferencesUtility mPreferencesUtility;

    public RegisterTask(Context context, PreferencesUtility preferencesUtility) {
        this.mContext = context;
        this.mPreferencesUtility = preferencesUtility;
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {
        // Try encode and send the REGISTER request.
        JSONObject json = null;
        try {
            String postData = ConnectionManager.postEncoder("register", parameters);
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
                // If the response was successful, try to LOGIN. If LOGIN is successful, start FEED.
                if (mPreferencesUtility.setUserInfo(ConnectionManager.login((JSONObject) data.get("details")))) {
                    Intent intent = new Intent(mContext, FeedActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    ActivityCompat.finishAffinity((Activity) mContext);
                    mContext.startActivity(intent);
                }
            } else {
                // TODO: Print error
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
