package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.lostanimals.tracks.utils.ServerManager;

public class FeedActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        button = (Button)findViewById(R.id.myPostsButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(FeedActivity.this, MyPostActivity.class));

                }
                catch (Exception e) {
                    Toast.makeText(FeedActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
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
