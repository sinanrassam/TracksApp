package com.lostanimals.tracks;


import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.Objects;

public class MyPostsActivity extends Fragment {
	
	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_posts);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("My Posts");}*/

	View view;
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_my_posts, container, false);


		return view;

	}

	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		//  Intent intent = new Intent(getActivity().getIntent());
	}

}