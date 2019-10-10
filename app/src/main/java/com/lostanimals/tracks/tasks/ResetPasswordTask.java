package com.lostanimals.tracks.tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.lostanimals.tracks.utils.PreferencesUtility;
import org.json.JSONObject;

public class ResetPasswordTask extends AsyncTask<String, Void, JSONObject> {
    Context mContext;
    PreferencesUtility mPreferencesUtility;

    public ResetPasswordTask(Context context, PreferencesUtility preferencesUtility) {
        this.mContext = context;
        this.mPreferencesUtility = preferencesUtility;
    }


    @Override
    protected JSONObject doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject data) {

    }
}
