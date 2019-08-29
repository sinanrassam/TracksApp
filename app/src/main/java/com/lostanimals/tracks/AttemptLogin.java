package com.lostanimals.tracks;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Test class for rewriting the BGworker class to JSONObjects
 * In the AsyncTask extension, a JSONObject is specified as the 3rd argument.
 * This allows doInBackground to return a JSONObject to onPostExecute.
 */
public class AttemptLogin extends AsyncTask<String, Void, JSONObject> {

    // for testing JSON.
    @Override
    protected JSONObject doInBackground(String... strings) {
        return new JSONObject();
    }

    /**
     * data.get("xyz") is just used for explanation purposes.
     * This is what I think the method may look like, but
     * I really don't know yet.
     *
     * @param data
     */
    @Override
    protected void onPostExecute(JSONObject data) {
        try {
            if (data.get("user") == "user") {
                // create user object
            } else if (data.get("post") == "post") {
                // create post object
            } else {
                // throw an error or something
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
