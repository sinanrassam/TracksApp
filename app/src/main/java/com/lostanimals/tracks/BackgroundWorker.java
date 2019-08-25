package com.lostanimals.tracks;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    private final String login_URL = "http://192.168.1.3:8080/phpmyadmin/tracks_api/login.php";
    AlertDialog alertDialog;
    private Context context;

    public BackgroundWorker(Context context) {
        setContext(context);
    }

    public Context getContext() {
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

        // Moved to class variable
        // String login_URL = "http://192.168.1.3:8080/phpmyadmin/tracks_api/login.php";

        if (type.equals("login")) {
            try {
                // Create a connection to the server/login.php file
                URL url = new URL(login_URL);
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
                String result = null;
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    Log.d("STATE", line);
                    result += line;
                    Log.d("STATE", result);
                }
                bufferedReader.close();
                inputStream.close();

                // Disconnect the HTTP connection
                connection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                // TODO: Maybe Log these?
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Login Status");
    }

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
