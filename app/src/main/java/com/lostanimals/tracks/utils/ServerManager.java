package com.lostanimals.tracks.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.lostanimals.tracks.FeedActivity;
import com.lostanimals.tracks.utils.PreferenceEntry;
import com.lostanimals.tracks.utils.PreferencesUtility;
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
public class ServerManager extends AsyncTask<String, Void, JSONObject> {
    private final String SCRIPT_URL = "http://bosh.live:7536/phpmyadmin/tracks_api/";
    @SuppressLint("StaticFieldLeak")
    private
    Context context;
    private AlertDialog alertDialog;
    private Toast toast;
    private PreferencesUtility mPreferencesUtility;

    public ServerManager(Context context) {
        this.context = context;
    }

    public void setPreferencesUtility(PreferencesUtility preferencesUtility) {
        this.mPreferencesUtility = preferencesUtility;
    }

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
                e.printStackTrace();
            }
        } else if (type.equals("reset password")) {
            // TODO: create code
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
                e.printStackTrace();
            }
        } else if (type.equals("get post")) {
            // TODO: write this logic
        }
        return json;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        toast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onPostExecute(JSONObject data) {
        if (data == null) {
            alertDialog.setMessage("Server Error");
            alertDialog.show();
        } else {
            String msg = null;
            try {
                if (data.get("response").equals("successful")) {
                    Log.d("onPostExecute", "success");
                    if (data.get("purpose").equals("login")) {
                        JSONObject details = (JSONObject) data.get("details");

                        // TODO: Properly test shared prefs:
                        PreferenceEntry preferenceEntry = new PreferenceEntry(details.getString("name"), details.getString("username"), details.getString("email"), true);
                        boolean userLogin = mPreferencesUtility.setUserInfo(preferenceEntry);
                        if (userLogin) {
                            Toast.makeText(this.context, "Login Successful", Toast.LENGTH_LONG).show();
                        }
                        //SaveSharedPreference.setLoggedIn(context, true, details.getString("username"), details.getString("name"), details.getString("email"));
                        msg = "Login Successful";
                        Intent feedIntent = new Intent(context, FeedActivity.class);
                        feedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(feedIntent);
                    } else if (data.get("purpose").equals("register")) {
                        msg = "Login Successful";
                        Intent feedIntent = new Intent(context, FeedActivity.class);
                        feedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(feedIntent);
                    } else if (data.get("purpose").equals("new post")) {
                        msg = "Post created";
                    }
                } else {
                    msg = data.get("reason").toString();
                }
                toast.setText(msg);
                toast.show();
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
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = new JSONObject(sb.toString());

        Log.d("JSON Holds:", json.toString());

        bufferedReader.close();
        inputStream.close();
        conn.disconnect();
        return json;
    }
}