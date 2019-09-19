package com.lostanimals.tracks.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class NewPostTask extends AsyncTask<String, Void, JSONObject> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    public NewPostTask(Context context) {
        mContext = context;
    }


    @Override
    protected JSONObject doInBackground(String... parameters) {
        // Try encode and send the NEW_POST request.
        JSONObject json = null;
        try {
            String postData = ConnectionManager.postEncoder("new-post", parameters);
            json = ConnectionManager.processRequest("post.php", postData);
            Log.d("JSON", json.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Toast.makeText(mContext, "Post created", Toast.LENGTH_LONG).show();
        super.onPostExecute(jsonObject);
    }
}
