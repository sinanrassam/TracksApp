package com.lostanimals.tracks.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
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
    private Fragment mFragment;
    private ProgressBar mProgressBar;

    public GetFollowedPostsTask(Fragment fragment, ProgressBar progressBar) {
        mFragment = fragment;
        mProgressBar = progressBar;
    }

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

            Log.d("postData", postData);

            try {
                json = processRequest("follow.php", postData);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
                success = false;
            }

            Log.d("JSON", json.toString());
        }

        if (json != null) {
            PostsUtility.clear();
            mFollowedPostsList = new ArrayList<>();
            try {
                JSONArray jsonArray = (JSONArray) json.get("posts");
                Log.d("test", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    String id = (String) jsonArray.get(i);
                    new UpdatePostsTask((ListFragment) mFragment, mProgressBar).execute("", id, "");
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
