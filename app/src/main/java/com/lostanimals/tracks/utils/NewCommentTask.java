package com.lostanimals.tracks.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class NewCommentTask extends AsyncTask<String, Void, JSONObject> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    public NewCommentTask(Context context) {
        mContext = context;
    }


    @Override
    protected JSONObject doInBackground(String... parameters) {
        // Try encode and send the NEW_POST request.
        JSONObject json = null;
        try {
            String postData = ConnectionManager.postEncoder("new-comment", parameters);
            json = ConnectionManager.processRequest("comment.php", postData);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        Log.d("JSON", json.toString());

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Toast.makeText(mContext, "Comment created", Toast.LENGTH_LONG).show();
        super.onPostExecute(jsonObject);
    }
}
