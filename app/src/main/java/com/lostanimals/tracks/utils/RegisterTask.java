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
        // Try encode the REGISTER request and save the request in postData
        String postData = null;
        try {
            postData = ConnectionManager.postEncoder("register", parameters);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try get process the REGISTER request and save the result in json
        JSONObject json = null;
        try {
            json = ConnectionManager.processRequest("user.php", postData);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject data) {
        try {
            if (data.get("response").equals("successful")) {
                JSONObject details = (JSONObject) data.get("details");

                // Save the user LOGIN in PreferenceEntry and start the FEED activity.
                PreferenceEntry preferenceEntry = new PreferenceEntry(details.getString("name"),
                        details.getString("username"), details.getString("email"), true);
                if (mPreferencesUtility.setUserInfo(preferenceEntry)) {
                    Intent intent = new Intent(mContext, FeedActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    ActivityCompat.finishAffinity((Activity) mContext);
                    mContext.startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
