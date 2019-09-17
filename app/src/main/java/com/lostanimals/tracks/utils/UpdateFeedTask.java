package com.lostanimals.tracks.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.lostanimals.tracks.FeedFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lostanimals.tracks.utils.ConnectionManager.processRequest;

public class UpdateFeedTask extends AsyncTask<String, Integer, Boolean> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar mProgressBar;
    private FeedFragment mFragment;
    private List<Map<String, String>> mPostList = new ArrayList<>();

    public UpdateFeedTask(FeedFragment activity, ProgressBar progressBar) {
        this.mFragment = activity;
        this.mContext = mFragment.getContext();
        this.mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(mContext, "Loading posts", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Boolean doInBackground(String... parameters) {
        JSONObject json = null;
        if (!this.isCancelled()) {
            String postData = null;
            try {
                postData = ConnectionManager.postEncoder("get", parameters);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                json = processRequest("post.php", postData);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }

        if (json != null) {
            mPostList = new ArrayList<>();
            try {
                JSONArray jsonArray = (JSONArray) json.get("posts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String title = (String) jsonObject.get("title");
                    String desc = (String) jsonObject.get("description");

                    Map<String, String> post = new HashMap<>(2);
                    post.put("Title", title);
                    post.put("Desc", desc);

                    mPostList.add(post);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (this.mProgressBar != null) {
            mProgressBar.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            // TODO: Remove the sleep.
            SystemClock.sleep(1000);
            mProgressBar.setVisibility(View.GONE);
        }
        SimpleAdapter adapter = new SimpleAdapter(mContext, mPostList,
                android.R.layout.simple_list_item_2,
                new String[]{"Title", "Desc"},
                new int[]{android.R.id.text1, android.R.id.text2});
        mFragment.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        Toast.makeText(mContext, "Posts refreshed", Toast.LENGTH_LONG).show();
    }
}