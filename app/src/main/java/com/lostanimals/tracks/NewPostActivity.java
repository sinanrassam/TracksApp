package com.lostanimals.tracks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.lostanimals.tracks.entries.PostEntry;
import com.lostanimals.tracks.tasks.DownloadImageTask;
import com.lostanimals.tracks.tasks.EditTask;
import com.lostanimals.tracks.tasks.NewPostTask;
import com.lostanimals.tracks.utils.PostsUtility;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.Objects;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {
    static final int LOCATION_CHOOSE_REQUEST = 999;
    private final String DEFAULT_LOCATION = "-36.854018,174.766719";
    private static final int RESULT_LOAD_IMAGE = 1;
    private boolean isEditTask;
    private int mPostPosition;
    private PostEntry mPostEntry;
    private boolean postHasImage;
    private EditText etTitle, etDescription;
    private Button mBackBtn, mPostBtn, mImageBtn, mRemoveImageBtn;
    private ImageView imageToUpload;
    private String location = DEFAULT_LOCATION;
	
	@SuppressLint ("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_post);
		
		Bundle b = this.getIntent().getExtras();
		if (b != null) {
            isEditTask = b.getBoolean("isEditTask");
            mPostPosition = b.getInt("postPosition");
            mPostEntry = PostsUtility.getPostEntry(mPostPosition);
		}
		
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		if (isEditTask) {
            etTitle = findViewById(R.id.post_et_post_title);
            etDescription = findViewById(R.id.post_et_desc);
            String postTitle = mPostEntry.getPostTitle();
            String postDescription = mPostEntry.getPostDesc();
			etTitle.setText(postTitle);
			etDescription.setText(postDescription);
		}

        mImageBtn = this.findViewById(R.id.post_upload_picture_bttn);
        mImageBtn.setOnClickListener(this);

        mRemoveImageBtn = this.findViewById(R.id.post_remove_picture_bttn);
        mRemoveImageBtn.setOnClickListener(this);

        imageToUpload = this.findViewById(R.id.imageToUpload);

        mBackBtn = this.findViewById(R.id.back);
        mBackBtn.setOnClickListener(this);

        ImageButton mapButton = this.findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseLocation();
            }
        });

        mPostBtn = findViewById(R.id.post_btn_post);
        mPostBtn.setOnClickListener(this);

        if (isEditTask) {
            etTitle.setText(mPostEntry.getPostTitle());
            etDescription.setText(mPostEntry.getPostDesc());
            if (mPostEntry.hasImage()) {
                new DownloadImageTask(imageToUpload).execute("post", mPostEntry.getId());
                imageToUpload.setVisibility(View.VISIBLE);
                mRemoveImageBtn.setVisibility(View.VISIBLE);
            }
        } else {
            imageToUpload.setVisibility(View.GONE);
            mRemoveImageBtn.setVisibility(View.GONE);
        }
	}

    private void chooseLocation() {
        Intent chooseOnMapIntent = new Intent(this, SetLocationActivity.class);
        startActivityForResult(chooseOnMapIntent, LOCATION_CHOOSE_REQUEST);
    }

    public void onNewPost() {
        etTitle.setError(null);
        etDescription.setError(null);

        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        Bitmap image = null;
        if (imageToUpload.getDrawable() != null) {
            Log.d("imageToUpload", "is not null");
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
                newPostTask.execute(title, description, PreferencesUtility.getUserInfo().getUsername(), location);
            } else {
                EditTask editTask = new EditTask(this, image);
                editTask.execute(mPostEntry.getId(), title, description, mPostEntry.getFound(), location);
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
                galleryIntent.createChooser(galleryIntent, "Select a post picture");
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.post_btn_post:
                onNewPost();
                break;
            case R.id.post_remove_picture_bttn:
                imageToUpload.setImageURI(null);
                imageToUpload.setImageDrawable(null);
                mRemoveImageBtn.setVisibility(View.GONE);
                imageToUpload.setVisibility(View.GONE);
                mImageBtn.setText(R.string.post_upload_picture_bttn);
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
            Uri selectedImage = data.getData();
            String path = getRealPathFromURI(this, selectedImage).toLowerCase();
            if ((path.endsWith(".jpg") || path.endsWith(".png"))) {
                imageToUpload.setImageURI(selectedImage);
                imageToUpload.setVisibility(View.VISIBLE);
                mRemoveImageBtn.setVisibility(View.VISIBLE);
                mImageBtn.setText("Change Image");
            } else {
                Toast.makeText(this, R.string.file_format_err, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == LOCATION_CHOOSE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Marker added!", Toast.LENGTH_SHORT).show();
                location = String.valueOf(data.getParcelableExtra("point"));
                location = location.replace("lat/lng: (", "");
                location = location.replace(")", "");
            } else {
                location = DEFAULT_LOCATION;
            }
        }
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
