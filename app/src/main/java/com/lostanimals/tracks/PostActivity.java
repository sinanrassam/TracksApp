package com.lostanimals.tracks;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.lostanimals.tracks.entries.PostEntry;
import com.lostanimals.tracks.tasks.DeleteTask;
import com.lostanimals.tracks.tasks.EditTask;
import com.lostanimals.tracks.tasks.FollowPostTask;
import com.lostanimals.tracks.tasks.NewCommentTask;
import com.lostanimals.tracks.utils.PostsUtility;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
	
	private PostEntry mPostEntry;
	private EditText mCommentView;
	private CommentsFragment commentsFragment;
	
	@SuppressLint ("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);

		int mPostPosition = getIntent().getIntExtra("position", -1);
		if (mPostPosition > -1) {
			mPostEntry = PostsUtility.getPostEntry(mPostPosition);
		} else {
			String postTitle = getIntent().getStringExtra("title");
			String postDesc = getIntent().getStringExtra("title");
			ArrayList<PostEntry> posts = PostsUtility.getPostEntries();
			for (PostEntry entry : posts) {
				if (entry.getPostTitle().equals(postTitle) && entry.getPostTitle().equals(postDesc)) {
					mPostEntry = entry;
					break;
				}
			}
		}
		
		TextView mPostTitleView = findViewById(R.id.post_txt_title);
		TextView mPostDescView = findViewById(R.id.post_et_desc);
		TextView mPostDateView = findViewById(R.id.post_date);
		TextView mPostAuthorView = findViewById(R.id.post_author);
		
		mPostTitleView.setText(mPostEntry.getPostTitle());
		
		mPostDescView.setText(mPostEntry.getPostDesc());
		mPostDateView.setText(mPostEntry.getPostDate() + ", at: " + mPostEntry.getPostTime());
		mPostAuthorView.setText("By: " + mPostEntry.getUsername());
		
		mCommentView = findViewById(R.id.comment_field);
		
		commentsFragment = new CommentsFragment();
		Bundle data = new Bundle();
		data.putString("post_id", mPostEntry.getId());
		commentsFragment.setArguments(data);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, commentsFragment).commit();

		Button mCommentBtn = findViewById(R.id.comment_btn);
		mCommentBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				addComment();
			}
		});

		if (PreferencesUtility.getUserInfo().getUsername().equals(mPostEntry.getUsername())) {
			findViewById(R.id.unowned_options).setVisibility(View.GONE);
		} else {
			Button mFollowPostBtn = findViewById(R.id.followPost_btn);
			final boolean following = mPostEntry.isFollowed();
			if (following) {
				mFollowPostBtn.setText(R.string.action_unfollow_post);
			}
			findViewById(R.id.unowned_options).setVisibility(View.VISIBLE);
			mFollowPostBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					followPost(following);
				}
			});
		}
	}

	private void followPost(boolean following) {
		Log.d("Follow Post", "Clicked");
		String followOrToUnfollow = (following)? "un" : "";
		new FollowPostTask(this).execute(PreferencesUtility.getUserInfo().getUsername(), mPostEntry.getId(), followOrToUnfollow);
	}
	
	private void addComment() {
		mCommentView.setError(null);
		String msg = mCommentView.getText().toString();
		
		boolean cancel = false;
		View focusView = null;
		
		// Check for a valid comment, if the user entered one.
		if (TextUtils.isEmpty(msg)) {
			mCommentView.setError(getString(R.string.error_field_required));
			focusView = mCommentView;
			cancel = true;
		}
		
		String username = PreferencesUtility.getUserInfo().getUsername();
		
		String post_id = mPostEntry.getId();
		if (cancel) {
			focusView.requestFocus();
		} else {
			mCommentView.setText("");
			mCommentView.clearFocus();
			NewCommentTask addCommentTask = new NewCommentTask(this);
			addCommentTask.execute(post_id, username, msg);
			commentsFragment.refresh();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mPostEntry != null) {
			if (mPostEntry.getUsername().equals(PreferencesUtility.getUserInfo().getUsername())) {
				getMenuInflater().inflate(R.menu.nav_popup, menu);
			}
		}
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.popup_found:
				onFoundClicked();
				return true;
			case R.id.popup_edit:
				onEditClicked();
				return true;
			case R.id.popup_delete:
				onDeleteClicked();
				return true;
		}
		
		return false;
	}
	
	private void onFoundClicked() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		if (mPostEntry.getFound().equals("0")) {
			builder.setMessage("Are you sure you want to mark the post as found? It will no longer be " + "accessible in the main feed but you can view it in my posts.");
			builder.setTitle("Warning!");
			
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					markAsFound();
				}
			});
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
		} else {
			builder.setMessage("This post has already been marked as found!");
			builder.setTitle("Attention!");
			
			builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
		}
		
		builder.setCancelable(false);
		
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
	private void onEditClicked() {
		if (mPostEntry.getFound().equals("0")) {
			Intent myIntent = new Intent(this, NewPostActivity.class);
			myIntent.putExtra("isEditTask", true); // to set trigger in NewPostActivity to call EditTask
			myIntent.putExtra("postID", mPostEntry.getId());
			myIntent.putExtra("postTitle", mPostEntry.getPostTitle());
			myIntent.putExtra("postDesc", mPostEntry.getPostDesc());
			myIntent.putExtra("isFound", mPostEntry.getFound());
			startActivity(myIntent);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			builder.setMessage("This post cannot be edited as it has been marked as found!");
			builder.setTitle("Attention!");
			
			builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			
			builder.setCancelable(false);
			
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}
	}
	
	private void onDeleteClicked() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setMessage("Are you sure you want to delete the post? Once deleted this cannot be " + "reversed.");
		builder.setTitle("Warning!");
		
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				deletePost();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		builder.setCancelable(false);
		
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
	private void markAsFound() {
		EditTask editTask = new EditTask(this);
		editTask.execute(mPostEntry.getId(), mPostEntry.getPostTitle(), mPostEntry.getPostDesc(), "1");
	}
	
	private void deletePost() {
		DeleteTask deleteTask = new DeleteTask(this);
		deleteTask.execute(mPostEntry.getId());
		finish();
	}
}
