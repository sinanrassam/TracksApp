package com.lostanimals.tracks.tasks;

import android.os.AsyncTask;
import android.util.Log;
import com.lostanimals.tracks.entries.PostEntry;
import com.lostanimals.tracks.utils.ConnectionManager;
import com.lostanimals.tracks.utils.PostsUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.lostanimals.tracks.utils.ConnectionManager.processRequest;

public class GetFollowedPostsTask extends AsyncTask<String, Integer, Boolean> {
    private List<String> mFollowedPostsList;
    @Override
    protected Boolean doInBackground(String... parameters) {
        boolean success = true;
        JSONObject json = null;
        if (!this.isCancelled()) {
            String postData = null;
            try {
                postData = ConnectionManager.postEncoder("get-followed-posts", parameters);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                success = false;
            }

            try {
                json = processRequest("follow.php", postData);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
                success = false;
            }
        }

        if (json != null) {
            PostsUtility.clear();
            mFollowedPostsList = new ArrayList<>();
            try {
                JSONArray jsonArray = (JSONArray) json.get("posts");
                Log.d("test", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    Log.d("jsonObject", jsonObject.toString());

                    mFollowedPostsList.add(jsonObject.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }

    public List<String> getFollowedPosts() {
        return mFollowedPostsList;
    }
}
