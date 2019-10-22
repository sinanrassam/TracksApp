package com.lostanimals.tracks.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.fragment.app.ListFragment;
import com.lostanimals.tracks.FeedFragment;
import com.lostanimals.tracks.entries.PostEntry;
import com.lostanimals.tracks.utils.ConnectionManager;
import com.lostanimals.tracks.utils.PostsUtility;
import com.lostanimals.tracks.utils.PreferencesUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

import static com.lostanimals.tracks.utils.ConnectionManager.processRequest;

public class HistoryPostsTask extends AsyncTask<String, Integer, Boolean> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar mProgressBar;

    private ListFragment mFragment;

    private List<Map<String, String>> mPostList = new ArrayList<>();

    public HistoryPostsTask(ListFragment activity, ProgressBar progressBar) {
        this.mFragment = activity;
        this.mContext = mFragment.getContext();
        this.mProgressBar = progressBar;
    }

    @Override
    protected Boolean doInBackground(String... parameters) {
        Queue<String> historyQ = FeedFragment.getHistoryQ();
        String user = PreferencesUtility.getUserInfo().getUsername();
        boolean success = true;
        JSONObject json = null;
        if (!this.isCancelled()) {
            String postData;
            try {
                postData = ConnectionManager.postEncoder("get-posts", parameters);
                json = processRequest("post.php", postData);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
                success = false;
            }
        }

        if (json != null) {
            PostsUtility.clear();
            mPostList = new ArrayList<>();
            try {
                JSONArray jsonArray = json.getJSONArray("posts");
                for (int i = 0; i < historyQ.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String title = jsonObject.getString("title");
                    String desc = jsonObject.getString("description");
                    String username = jsonObject.getString("username");
                    String date = jsonObject.getString("post_date");
                    String time = jsonObject.getString("post_time");
                    String found = jsonObject.getString("found");
                    String microChipped = jsonObject.getString("micro_chipped");
                    String following = jsonObject.getString("following");
                    String imageExists = jsonObject.getString("image_exists");
                    String location = jsonObject.getString("location");
                    String stray = jsonObject.getString("stray");
                    String edited = (String) jsonObject.get("edited");

                    if (historyQ.contains(id) && !username.equals(user)) {
                        PostsUtility.addPostEntry(i, new PostEntry(id, title, desc, username, date, time, found,
                                microChipped, following, imageExists, location, stray));

                        Map<String, String> post = new HashMap<>(2);

                        post.put("Title", PostsUtility.getPostEntry(i).getPostTitle());
                        post.put("Desc", PostsUtility.getPostEntry(i).getPostDesc());

                        mPostList.add(post);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(mContext, "Loading posts", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        mProgressBar.setVisibility(View.GONE);

        SimpleAdapter adapter = new SimpleAdapter(mContext, mPostList, android.R.layout.simple_list_item_2, new String[]{"Title", "Desc"}, new int[]{android.R.id.text1, android.R.id.text2});
        mFragment.setListAdapter(adapter);

        adapter.notifyDataSetChanged();

        if (success) {
            Toast.makeText(mContext, "Posts refreshed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Error loading posts", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (this.mProgressBar != null) {
            mProgressBar.setProgress(values[0]);
        }
    }
}