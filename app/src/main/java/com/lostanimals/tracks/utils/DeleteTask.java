package com.lostanimals.tracks.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DeleteTask extends AsyncTask<String, Integer, JSONObject> {
    private Context mContext;

    public DeleteTask(Context context) {
        mContext = context;
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {
        JSONObject json = null;
        try {
            String postData = ConnectionManager.postEncoder("delete-post", parameters);
            json = ConnectionManager.processRequest("post.php", postData);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject data) {
        try {
            if (data.get("response").equals("successful")) {
                Toast.makeText(mContext, "Post Successfully Deleted", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(mContext, "Error Deleting Post", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d("DELETE_TASK", "onCancelled: DELETE_TASK_DELETE_TASK_DELETE_TASK_DELETE_TASK_DELETE_TASK");
    }
}
