package com.lostanimals.tracks;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedFragment extends ListFragment {
    private List<Map<String, String>> postsData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Map<String, String> post = new HashMap<>(2);
        post.put("Title", "Missing Dog!");
        post.put("Desc","Last seen in Orewa");
        postsData.add(post);

        Map<String, String> post2 = new HashMap<>(2);
        post2.put("Title", "Found Cat!");
        post2.put("Desc","Pickup in Auckland CBD\nHello\n3rd line");
        postsData.add(post2);

        Map<String, String> post3 = new HashMap<>(2);
        post3.put("Title", "Testing!");
        post3.put("Desc","2 lines\nnew line");
        postsData.add(post3);

        SimpleAdapter adapter = new SimpleAdapter(this.getContext(), postsData,
                android.R.layout.simple_list_item_2,
                new String[] {"Title", "Desc" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("FEED_ONCLICK", String.valueOf(position));
        Toast toast = Toast.makeText(this.getContext(), postsData.get(position).toString(), Toast.LENGTH_SHORT);
        toast.show();
        getListView().setSelector(android.R.color.holo_blue_bright);
    }
}