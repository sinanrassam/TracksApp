package com.lostanimals.tracks.tasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import com.lostanimals.tracks.FeedActivity;
import com.lostanimals.tracks.utils.ConnectionManager;
import com.lostanimals.tracks.utils.PreferencesUtility;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegisterTask extends AsyncTask<String, Void, JSONObject> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    public RegisterTask(Context context) {
        this.mContext = context;
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
                if (PreferencesUtility.setUserInfo(ConnectionManager.login((JSONObject) data.get("details")))) {
                    Intent intent = new Intent(mContext, FeedActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    ActivityCompat.finishAffinity((Activity) mContext);
                    mContext.startActivity(intent);
                }
            } else {
                Toast.makeText(mContext, (String) data.get("reason"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
