package com.lostanimals.tracks;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ConfigureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        //generate arrayList
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Dark Mode");
        arrayList.add("Notifications");
        arrayList.add("Logout");
        arrayList.add("Version");

        //instantiate custom adapter
        ConfigureAdapter adapter = new ConfigureAdapter(arrayList, this);

        //handle listview and assign adapter
        // ListView lView = findViewById(R.id.list);
        ListView lView = findViewById(android.R.id.list);
        lView.setAdapter(adapter);
    }
}