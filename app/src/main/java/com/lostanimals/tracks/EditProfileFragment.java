package com.lostanimals.tracks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class EditProfileFragment extends Fragment {

    TextView mUsername,mName,mEmail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_edit_profile, container, false);

        mUsername = (TextView)view.findViewById(R.id.username_edit);
        mUsername.setText(PreferencesUtility.getUserInfo().getUsername());

        mName = (TextView)view.findViewById(R.id.password_edit);
        mName.setText(PreferencesUtility.getUserInfo().getName());

        mEmail = (TextView)view.findViewById(R.id.email_edit);
        mEmail.setText(PreferencesUtility.getUserInfo().getEmail());


        return view;
    }
}