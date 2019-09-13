package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.lostanimals.tracks.utils.PostEntry;
import com.lostanimals.tracks.utils.PostFragmentAdapter;
import com.lostanimals.tracks.utils.ServerManager;

public class FeedActivity extends AppCompatActivity implements PostListFragment.OnListFragmentInteractionListener {

    PostFragmentAdapter mPostListFragmentAdapter;
    // PostFragmentAdapter mPostFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        mPostListFragmentAdapter = new PostFragmentAdapter(getSupportFragmentManager());
        mPostListFragmentAdapter.addFragment(Fragment.instantiate(this, PostListFragment.class.getName()));

//        ViewPager pager = findViewById(R.id.viewpager);
//        pager.setAdapter(mPostListFragmentAdapter);

        // addFragment(new PostListFragment(), false, "one");
    }

    public void onAdd(View view) {
        startActivity(new Intent(this, NewPostActivity.class));
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        Log.d("ADDFRAG", "In Method");

//        ServerManager serverManager = new ServerManager(this);
//        serverManager.execute("get", "5");

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.frame_container, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    // TODO: Remove this garbage.
    public void onClick(View view) {
        ServerManager serverManager = new ServerManager(this);
        serverManager.execute("get", "5");
    }

    @Override
    public void onListFragmentInteraction(PostEntry item) {
        Log.d("INTERACTION", "User input: " + item.toString());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
