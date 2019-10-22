package com.lostanimals.tracks;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class MyProfileActivity extends Fragment {
    View view;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_my_profile, container, false);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView mUsername = getView().findViewById(R.id.usernameProfile);
        TextView mName = getView().findViewById(R.id.nameProfile);
        TextView mEmail = getView().findViewById(R.id.emailProfile);

        mUsername.setText("Your username is: " + PreferencesUtility.getUserInfo().getUsername());
        mName.setText("Hi, " + PreferencesUtility.getUserInfo().getName() + "!");
        mEmail.setText("Your email address is: " + PreferencesUtility.getUserInfo().getEmail());
    }
}