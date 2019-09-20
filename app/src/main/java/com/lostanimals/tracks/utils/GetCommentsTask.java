package com.lostanimals.tracks.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.lostanimals.tracks.CommentsFragment;
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

public class GetCommentsTask extends AsyncTask<String, Integer, Boolean> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar mProgressBar;
    private CommentsFragment mFragment;
    private List<Map<String, String>> mCommentsList = new ArrayList<>();
    private TextView mTextView;

    public GetCommentsTask(CommentsFragment activity, TextView textView, ProgressBar progressBar) {
        this.mFragment = activity;
        this.mContext = mFragment.getContext();
        this.mProgressBar = progressBar;
        this.mTextView = textView;
        mTextView.setVisibility(View.GONE);
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
                json = processRequest("comment.php", postData);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }

        Log.d("JSON", json.toString());

        if (json != null) {
            mCommentsList = new ArrayList<>();
            try {
                JSONArray jsonArray = (JSONArray) json.get("comments");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String id = (String) jsonObject.get("id");
                    String post_id = (String) jsonObject.get("post_id");
                    String username = (String) jsonObject.get("username");
                    String desc = (String) jsonObject.get("description");
//                    String date = (String) jsonObject.get("date");

                    Comment c = new Comment(id, post_id, username, desc, null);

                    Map<String, String> comment = new HashMap<>(2);

                    comment.put("Title", c.getPostId());
                    comment.put("Desc", c.toString());

                    mCommentsList.add(i, comment);
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
        } else {
            mProgressBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
        }
        SimpleAdapter adapter = new SimpleAdapter(mContext, mCommentsList,
                android.R.layout.simple_list_item_2,
                new String[]{"Title", "Desc"},
                new int[]{android.R.id.text1, android.R.id.text2});
        mFragment.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        Toast.makeText(mContext, "Comments received", Toast.LENGTH_LONG).show();
    }
}