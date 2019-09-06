package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.lostanimals.tracks.utils.ServerManager;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
    }

    public void onAdd(View view) {
        startActivity(new Intent(this, NewPostActivity.class));
    }

    // TODO: Remove this garbage.
    public void onClick(View view) {
        ServerManager serverManager = new ServerManager(this);
        serverManager.execute("get", "5");
    }
}
