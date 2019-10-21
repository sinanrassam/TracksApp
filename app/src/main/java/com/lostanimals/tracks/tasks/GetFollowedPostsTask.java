package com.lostanimals.tracks.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import androidx.fragment.app.ListFragment;
import com.lostanimals.tracks.entries.PostEntry;
import com.lostanimals.tracks.utils.PostsUtility;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GetFollowedPostsTask extends AsyncTask<String, Integer, Boolean> {
    private Context mContext;
    private ListFragment mFragment;
    private ProgressBar mProgressBar;
    private ArrayList<Map<String, String>> mFollowedPostsList;
    private UpdatePostsTask mUpdatePostsTask;
    private HashMap<Integer, Integer> idMap;

    public GetFollowedPostsTask(ListFragment fragment, ProgressBar progressBar) {
        mFragment = fragment;
        mContext = mFragment.getContext();
        mProgressBar = progressBar;
        mFollowedPostsList = new ArrayList<>();
        idMap = new HashMap<>();
    }

    @Override
    protected void onPreExecute() {
        mUpdatePostsTask = new UpdatePostsTask(mFragment, mContext, mProgressBar);
        mUpdatePostsTask.execute("", PreferencesUtility.getUserInfo().getUsername(), "", "");
    }

    @Override
    protected Boolean doInBackground(String... parameters) {
        boolean success = false;
        try {
            success = mUpdatePostsTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        mFollowedPostsList.clear();
        List<PostEntry> postList = PostsUtility.getPostEntries();

        int index = 0;
        for (int i = 0; i < postList.size(); i++) {
            PostEntry entry = postList.get(i);
            if (entry.isFollowed()) {
                Map<String, String> post = new HashMap<>(2);
                post.put("Title", entry.getPostTitle());
                post.put("Desc", entry.getPostDesc());
                mFollowedPostsList.add(post);
                idMap.put(index, i);
                index++;
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(mContext, mFollowedPostsList, android.R.layout.simple_list_item_2, new String[]{"Title", "Desc"}, new int[]{android.R.id.text1, android.R.id.text2});
        mFragment.setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    public int getActualId(int index) {
        return idMap.get(index);
    }
}
