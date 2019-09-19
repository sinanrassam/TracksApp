package com.lostanimals.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.lostanimals.tracks.utils.ServerManager;
import android.view.View;

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
                startActivity(new Intent(FeedActivity.this, MyPostActivity.class));
            }
        });
    }

    public void onAdd(View view) {
        startActivity(new Intent(this, NewPostActivity.class));
    }
}