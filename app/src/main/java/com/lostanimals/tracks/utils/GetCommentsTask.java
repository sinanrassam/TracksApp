package com.lostanimals.tracks.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
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

class GetCommentTask extends AsyncTask<String, Integer, Boolean> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar mProgressBar;
    private FeedFragment mFragment;
    private List<Map<String, String>> mCommentList = new ArrayList<>();

    public GetCommentTask(FeedFragment activity, ProgressBar progressBar) {
        this.mFragment = activity;
        this.mContext = mFragment.getContext();
        this.mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(mContext, "Loading comments", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Boolean doInBackground(String... parameters) {
        JSONObject json = null;
        if (!this.isCancelled()) {
            String postData = null;
            try {
                postData = ConnectionManager.postEncoder("get-comments", parameters);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                json = processRequest("comment.php", postData);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }

        if (json != null) {
            // Clear the array list and post list-map first
            mCommentList = new ArrayList<>();
            try {
                JSONArray jsonArray = (JSONArray) json.get("comments");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String id = (String) jsonObject.get("id");
                    String post_id = (String) jsonObject.get("post_id");
                    String username = (String) jsonObject.get("username");
                    String desc = (String) jsonObject.get("description");
                    String date = (String) jsonObject.get("date");

                    Comment comment = new Comment(id, post_id, username, desc, date);

                    Map<String, String> comments = new HashMap<>(2);

                    comments.put("post_id", comment.getPostId());
                    comments.put("Desc", comment.toString());
                    mCommentList.add(i, comments);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        SimpleAdapter adapter = new SimpleAdapter(mContext, mCommentList,
                android.R.layout.simple_list_item_2,
                new String[]{"Title", "Desc"},
                new int[]{android.R.id.text1, android.R.id.text2});
        mFragment.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        Toast.makeText(mContext, "Comments loaded", Toast.LENGTH_LONG).show();
    }
}
