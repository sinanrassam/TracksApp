package com.lostanimals.tracks;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private Context context;

    private AlertDialog alertDialog;

    BackgroundWorker(Context context) {
        setContext(context);
    }

    Context getContext() {
        return context;
    }

    private void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        // Pull values out of params.
        String type = params[0];
        String username = params[1];
        String password = params[2];

        if (type.equals("login")) {
            try {
                // Create a connection to the server/login.php file
                final String LOGIN_SCRIPT = "http://bosh.live:7536/phpmyadmin/tracks_api/login.php";
                URL url = new URL(LOGIN_SCRIPT);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                // Send a request
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // A return will be sent, so create an input stream to capture this and read it.
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                String result = "";
                String line;

                // Read in the received message
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                // Close Streams and disconnect the HTTP connection
                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

                return result;
            } catch (IOException e) {
                // TODO: Maybe Log these?
                e.printStackTrace();
            }
        }
        return null;
    }

    // This method runs before the button is click
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Login Status");
    }

    // When the login button is clicked, this method fires.
    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
