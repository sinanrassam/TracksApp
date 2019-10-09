package com.lostanimals.tracks.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import androidx.fragment.app.ListFragment;
import com.lostanimals.tracks.entries.PostEntry;
import com.lostanimals.tracks.utils.ConnectionManager;
import com.lostanimals.tracks.utils.PostsUtility;
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

public class GetFollowedPostsTask extends AsyncTask<String, Integer, Boolean> {
    private Context mContext;
    private ListFragment mFragment;
    private ProgressBar mProgressBar;
    ArrayList<Map<String, String>> mFollowedPostsList;

    public GetFollowedPostsTask(ListFragment fragment, ProgressBar progressBar) {
        mFragment = fragment;
        mContext = mFragment.getContext();
        mProgressBar = progressBar;
        mFollowedPostsList = new ArrayList<>();
    }

    @Override
    protected Boolean doInBackground(String... parameters) {
        boolean success = true;

        List<PostEntry> postList = PostsUtility.getPostEntries();

        for (PostEntry entry : postList) {
            if (entry.getFollowing()) {
                Map<String, String> post = new HashMap<>(2);
                post.put("Title", entry.getPostTitle());
                post.put("Desc", entry.getPostDesc());
                mFollowedPostsList.add(post);
            }
        }

        return success;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        SimpleAdapter adapter = new SimpleAdapter(mContext, mFollowedPostsList, android.R.layout.simple_list_item_2, new String[] {"Title", "Desc"}, new int[] {android.R.id.text1, android.R.id.text2});
        mFragment.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }
}
