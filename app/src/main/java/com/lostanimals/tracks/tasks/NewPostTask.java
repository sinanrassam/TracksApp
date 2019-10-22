package com.lostanimals.tracks.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;
import com.lostanimals.tracks.FeedActivity;
import com.lostanimals.tracks.utils.ConnectionManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class NewPostTask extends AsyncTask<String, Void, JSONObject> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private Bitmap mImage;
    private boolean mStray;

    public NewPostTask(Context context, Bitmap image, boolean stray) {
        mContext = context;
        mImage = image;
        mStray = stray;
    }

    @Override
    protected JSONObject doInBackground(String... parameters) {
        // Try encode and send the NEW_POST request.
        JSONObject json = null;
        try {
            String postData = ConnectionManager.postEncoder("new-post", parameters);

            if (mImage != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                mImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                postData += "&" + URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(encodedImage, "UTF-8");
            }

            if (mStray) {
                postData += "&" + URLEncoder.encode("stray", "UTF-8") + "=" + URLEncoder.encode("'1'", "UTF-8");
            }

            json = ConnectionManager.processRequest("post.php", postData);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            if (jsonObject.get("response").equals("successful")) {
                Intent feedIntent = new Intent(mContext, FeedActivity.class);
                feedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(feedIntent);
            }
        } catch (JSONException e) {
            Toast.makeText(mContext, "Error Creating Post", Toast.LENGTH_LONG).show();
        }

        super.onPostExecute(jsonObject);
    }
}
