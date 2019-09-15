package com.lostanimals.tracks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.lostanimals.tracks.utils.UpdateFeedTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedFragment extends ListFragment {
    private List<Map<String, String>> postsData = new ArrayList<>();
    private List<Map<String, String>> realPosts = new ArrayList<>();

    public void setRealPosts(List<Map<String, String>> newList) {
        realPosts = newList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AsyncTask updateFeed = new UpdateFeedTask(this).execute("4");

        Map<String, String> post = new HashMap<>(2);
        post.put("Tile", "Missing Dog!");
        post.put("Desc", "Last seen in Orewa");
        postsData.add(post);

        Map<String, String> post2 = new HashMap<>(2);
        post2.put("Title", "Found Cat!");
        post2.put("Desc", "Pickup in Auckland CBD\nHello\n3rd line");
        postsData.add(post2);

        Map<String, String> post3 = new HashMap<>(2);
        post3.put("Title", "Testing!");
        post3.put("Desc", "2 lines\nnew line");
        postsData.add(post3);

        return inflater.inflate(R.layout.feed_fragment, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("FEED_ONCLICK", String.valueOf(position));
        Toast toast = Toast.makeText(this.getContext(), postsData.get(position).toString(), Toast.LENGTH_SHORT);
        toast.show();
        getListView().setSelector(android.R.color.holo_blue_bright);
    }
}