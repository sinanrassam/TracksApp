package com.lostanimals.tracks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.lostanimals.tracks.tasks.EditTask;
import com.lostanimals.tracks.tasks.NewPostTask;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {
	private boolean isEditTask;
	private String postID, postTitle, postDescription, postIsFound;
	private EditText etTitle, etDescription;
	private Button backBtn, postBtn, imageBtn;
	private static final int RESULT_LOAD_IMAGE = 1;
	private ImageView imageToUpload;

	@SuppressLint ("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_post);

		Bundle b = this.getIntent().getExtras();
		if (b != null) {
			isEditTask = b.getBoolean("isEditTask");
			postID = b.getString("postID");
			postTitle = b.getString("postTitle");
			postDescription = b.getString("postDesc");
			postIsFound = b.getString("isFound");
		}

		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

		etTitle = findViewById(R.id.post_et_post_title);
		etDescription = findViewById(R.id.post_et_desc);

		if (isEditTask) {
			etTitle.setText(postTitle);
			etDescription.setText(postDescription);
		}

		imageBtn = this.findViewById(R.id.post_upload_picture_bttn);
		imageBtn.setOnClickListener(this);

		imageToUpload = this.findViewById(R.id.imageToUpload);

		backBtn = this.findViewById(R.id.back);
		backBtn.setOnClickListener(this);
	}

	public void onNewPost(View view) {
		etTitle.setError(null);
		etDescription.setError(null);

		String title = etTitle.getText().toString();
		String description = etDescription.getText().toString();
		Bitmap image = null;
		if (imageToUpload.getDrawable() != null) {
			image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
		}

		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(title)) {
			etTitle.setError(getString(R.string.error_field_required));
			focusView = etTitle;
			cancel = true;
		}

		if (TextUtils.isEmpty(description)) {
			etDescription.setError(getString(R.string.error_field_required));
			focusView = etDescription;
			cancel = true;
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			if (!isEditTask) {
				NewPostTask newPostTask = new NewPostTask(this, image);
				newPostTask.execute(title, description, PreferencesUtility.getUserInfo().getUsername());
			} else {
				EditTask editTask = new EditTask(this);
				editTask.execute(postID, title, description, postIsFound);
			}
			finish();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			Intent intent = new Intent(NewPostActivity.this, FeedActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.post_upload_picture_bttn:
				Log.d("Image", "Browse for image");
				Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				galleryIntent.setType("image/*.jpg");
				galleryIntent.createChooser(galleryIntent, "Select a post picture");
				startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
				break;
			case R.id.post_btn_post:
				onNewPost();
				break;
			case R.id.back:
				finish();
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
			Log.d("onActivityResult", "yup");
			Uri selectedImage = data.getData();
			imageToUpload.setImageURI(selectedImage);
		}
	}
}
