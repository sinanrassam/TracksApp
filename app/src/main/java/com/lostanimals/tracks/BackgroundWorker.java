package com.lostanimals.tracks;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private AlertDialog alertDialog;
    private final String scriptURL = "http://bosh.live:7536/phpmyadmin/tracks_api/";

    BackgroundWorker(Context context) {
        setContext(context);
    }

    @Override
    protected String doInBackground(String... params) {
        // Pull values out of params.
        String type = params[0];
        if (type.equals("login")) {
            String username = params[1];
            String password = params[2];
            try {
                // Create a connection to the server/login.php file
                HttpURLConnection connection = openConnection(scriptURL + "login.php");

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
        } else if (type.equals("register")) {
            Log.d("BackgroundWorker", "Entered register state");

            String name = params[1];
            String username = params[2];
            String password = params[3];
            try {
                // Create a connection to the server/register.php file
                String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&";
                post_data += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&";
                post_data += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                HttpURLConnection conn = openConnection(scriptURL + "register.php");

                String result = processRequest(conn, post_data);

                //disconnect the HTTP connection
                conn.disconnect();
                return result;
            } catch (IOException e) {
                // TODO: Maybe Log these?
                e.printStackTrace();
            }
        }
        return null;
    }

    protected HttpURLConnection openConnection(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        return connection;
    }

    protected String processRequest(HttpURLConnection connection, String data) throws IOException {
        // Send a request
        OutputStream outputStream = connection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        bufferedWriter.write(data);
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

        // Close Streams
        bufferedReader.close();
        inputStream.close();
        return result;
    }

    // This method runs before the button is clicked
    @Override
    protected void onPreExecute() {
        setAlertDialog(new AlertDialog.Builder(getContext()).create());
        getAlertDialog().setTitle("Login Status");
    }

    // When the login button is clicked, this method fires.
    @Override
    protected void onPostExecute(String result) {
        getAlertDialog().setMessage(result);
        getAlertDialog().show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private Context getContext() {
        return context;
    }

    private void setContext(Context context) {
        this.context = context;
    }

    private AlertDialog getAlertDialog() {
        return alertDialog;
    }

    private void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }
}
