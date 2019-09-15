package com.lostanimals.tracks.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.SimpleAdapter;
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

public class UpdateFeedTask extends AsyncTask<String, Void, Boolean> {
    private static List<Map<String, String>> mPostList;
    List<Map<String, String>> realPosts;
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    // private ProgressDialog dialog;
    private FeedFragment mFragment;


    public UpdateFeedTask(FeedFragment activity) {
        this.mFragment = activity;
        this.mContext = mFragment.getContext();
//        dialog = new ProgressDialog(mContext);
    }

    public List<Map<String, String>> getPostList() {
        return mPostList;
    }

    private void setPostList(List<Map<String, String>> postList) {
        mPostList = postList;
    }

    @Override
    protected void onPreExecute() {
//        this.dialog.setMessage("Progress start");
//        this.dialog.show();
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
            realPosts = new ArrayList<>();
            try {
                JSONArray jsonArray = (JSONArray) json.get("posts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String title = (String) jsonObject.get("title");
                    String desc = (String) jsonObject.get("description");
                    Map<String, String> post = new HashMap<>(2);
                    post.put("Title", title);
                    post.put("Desc", desc);
                    realPosts.add(post);
                }
                mFragment.setRealPosts(realPosts);
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
            // setPostList(postsData);
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
//        if (dialog.isShowing()) {
//            dialog.dismiss();
//        }

        SimpleAdapter adapter = new SimpleAdapter(mContext, realPosts,
                android.R.layout.simple_list_item_2,
                new String[]{"Title", "Desc"},
                new int[]{android.R.id.text1, android.R.id.text2});

        mFragment.setListAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}