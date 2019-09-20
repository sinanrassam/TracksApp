package com.lostanimals.tracks.utils;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.lostanimals.tracks.PostActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

// TODO: Create the edit activity and call this task with the correct parameters. Copy LoginActivity and FeedFrag
public class EditTask extends AsyncTask<String, Integer, JSONObject> {
    private Context mContext;

    public EditTask(Context context) {
        mContext = context;
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {
        // Try encode and send the LOGIN request.
        JSONObject json = null;
        try {
            String postData = ConnectionManager.postEncoder("edit-post", parameters);
            json = ConnectionManager.processRequest("post.php", postData);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject data) {
        //TODO: Something with the returned JSONObject. Maybe check result is successful, if so make a toast / notify
        // the calling UI with an adapter. Maybe go back to the post and refresh it so the update is visible.
        try {
            //TODO: See if we need to return the post
            if (data.get("response").equals("successful")) {
                Toast.makeText(mContext, "Post Successfully Updated", Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e) {
            Toast.makeText(mContext, "Error Updating Post", Toast.LENGTH_SHORT).show();
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
