package com.lostanimals.tracks.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import androidx.fragment.app.ListFragment;
import com.lostanimals.tracks.entries.PostEntry;
import com.lostanimals.tracks.utils.PostsUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (entry.isFollowed()) {
                Map<String, String> post = new HashMap<>(2);
                post.put("Title", entry.getPostTitle());
                post.put("Desc", entry.getPostDesc());
                // todo: What? Shouldn't remove?
                Log.d("Post", String.valueOf(post));
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
