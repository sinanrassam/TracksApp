package com.lostanimals.tracks.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import com.lostanimals.tracks.utils.ConnectionManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
