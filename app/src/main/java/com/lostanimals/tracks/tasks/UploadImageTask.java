package com.lostanimals.tracks.tasks;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.lostanimals.tracks.FeedActivity;
import com.lostanimals.tracks.utils.ConnectionManager;
import com.lostanimals.tracks.utils.NotificationUtility;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UploadImageTask extends AsyncTask<String, Void, JSONObject> {
	private Bitmap mImage;

	public UploadImageTask(Bitmap image) {
		mImage = image;
	}

	@Override
	protected JSONObject doInBackground(String... parameters) {
		// Try encode and send the NEW_POST request.
		JSONObject json = null;

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		mImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
		String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
		parameters[2] = encodedImage;

		try {
			String postData = ConnectionManager.postEncoder("upload-image", parameters);
			json = ConnectionManager.processRequest("image.php", postData);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}

		return json;
	}
}
