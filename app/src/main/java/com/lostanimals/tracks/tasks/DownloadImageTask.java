package com.lostanimals.tracks.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.lostanimals.tracks.utils.ConnectionManager;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView mImageView;

    public DownloadImageTask(ImageView imageView) {
        mImageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... parameters) {
        String urldisplay = ConnectionManager.URL + "img/" + parameters[0] + "/pic_" + parameters[1] + ".jpg";
        Log.d("url", urldisplay);
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            mImageView.setImageBitmap(result);
            mImageView.setVisibility(View.VISIBLE);
        }
    }
}