package com.lostanimals.tracks;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * Test class for rewriting the BGworker class to JSONObjects
 * In the AsyncTask extension, a JSONObject is specified as the 3rd argument.
 * This allows doInBackground to return a JSONObject to onPostExecute.
 */
public class AttemptLogin extends AsyncTask<String, Void, JSONObject> {
    private final String SCRIPT_URL = "http://bosh.live:7536/phpmyadmin/tracks_api/";
    Context context;
    private AlertDialog alertDialog;


    AttemptLogin(Context context) {
        this.context = context;
    }

    // for testing JSON.
    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject json = null;
        String type = params[0];
        String postData = null;
        try {
            postData = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (type.equalsIgnoreCase("login")) {
            String username = params[1];
            String password = params[2];
            try {
                // Create a connection to the server/register.php file
                postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&";
                postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                json = processRequest(SCRIPT_URL + "user.php", postData);
            } catch (IOException | JSONException e) {
                // TODO: Maybe Log these?
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("register")) {
            String name = params[1];
            String username = params[2];
            String password = params[3];
            try {
                // Create a connection to the server/register.php file
                postData += URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&";
                postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&";
                postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                json = processRequest(SCRIPT_URL + "user.php", postData);
            } catch (IOException | JSONException e) {
                // TODO: Maybe Log these?
                e.printStackTrace();
            }
        } else if (type.equals("reset password")) {
        } else if (type.equals("new post")) {
            String postTitle = params[1];
            String postDesc = params[2];
            String username = params[3];

            try {
                // Create a connection to the server/register.php file
                postData += URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(postTitle, "UTF-8") + "&";
                postData += URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(postDesc, "UTF-8") + "&";
                postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");

                json = processRequest(SCRIPT_URL + "post.php", postData);
            } catch (IOException | JSONException e) {
                // TODO: Maybe Log these?
                e.printStackTrace();
            }
        } else if (type.equals("get post")) {
        }
        return json;
    }

    // This method runs before the button is clicked
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
    }

    /**
     * data.get("xyz") is just used for explanation purposes.
     * This is what I think the method may look like, but
     * I really don't know yet.
     *
     * @param data
     */
    @Override
    protected void onPostExecute(JSONObject data) {
        if (data != null) {
            try {
                if (data.get("response").equals("successful")) {
                    Log.d("onPostExecute", "success");
                    if (data.get("purpose").equals("login")) {
                        alertDialog.setTitle("Login Status");
                        JSONObject details = (JSONObject) data.get("details");
                        Log.d("details: ", (String) details.get("name"));
                        Log.d("details: ", (String) details.get("username"));
                        Log.d("details: ", (String) details.get("email"));
                        alertDialog.setMessage("Login successful");
                        alertDialog.show();

                        // TODO: Properly test shared prefs:
                        SaveSharedPreference.setLoggedIn(context, true, details.getString("username"), details.getString("name"), details.getString("email"));

                    } else if (data.get("purpose").equals("register")) {
                        alertDialog.setTitle("Register Status");
                        JSONObject details = (JSONObject) data.get("details");
                        Log.d("details: ", (String) details.get("name"));
                        Log.d("details: ", (String) details.get("username"));
                        Log.d("details: ", (String) details.get("email"));
                        alertDialog.setMessage("Register successful");
                        alertDialog.show();
                    } else if (data.get("purpose").equals("new post")) {
                        alertDialog.setTitle("New Post Status");
                        alertDialog.setMessage("New post successful");
                        alertDialog.show();
                    }
                } else {
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Operation Failed");
                    alertDialog.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected HttpURLConnection openConnection(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        return connection;
    }

    protected JSONObject processRequest(String link, String data) throws IOException, JSONException {
        HttpURLConnection conn = openConnection(link);
        // Send a request
        OutputStream outputStream = conn.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();

        // A return will be sent, so create an input stream to capture this and read it.
        InputStream inputStream = conn.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
        String line;

        StringBuilder sb = new StringBuilder();
        // Read in the received message
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = new JSONObject(sb.toString());

        Log.d("JSON Holds:", json.toString());

        // Close Streams
        bufferedReader.close();
        inputStream.close();
        //disconnect the HTTP connection
        conn.disconnect();
        return json;
    }
}
