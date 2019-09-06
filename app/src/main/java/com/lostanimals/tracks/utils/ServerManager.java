package com.lostanimals.tracks.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;
import com.lostanimals.tracks.FeedActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ServerManager extends AsyncTask<String, Void, JSONObject> {
    private final String SCRIPT_URL = "http://bosh.live:7536/phpmyadmin/tracks_api/";
    public ArrayList<PostEntry> postList;
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
        } else if (type.equals("new")) {
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
        } else if (type.equals("get")) {
            // int postNumber = Integer.parseInt(params[1]);
            try {
                postData += URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                Log.d("POST", postData);
                json = processRequest(SCRIPT_URL + "post.php", postData);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
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
                        msg = "Login Successful";
                        Intent feedIntent = new Intent(context, FeedActivity.class);
                        feedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        ActivityCompat.finishAffinity((Activity) context);
                        context.startActivity(feedIntent);
                    } else if (data.get("purpose").equals("register")) {
                        msg = "Login Successful";
                        Intent feedIntent = new Intent(context, FeedActivity.class);
                        feedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        ActivityCompat.finishAffinity((Activity) context);
                        context.startActivity(feedIntent);
                    } else if (data.get("purpose").equals("new post")) {
                        msg = "Post created";
                    } else if (data.get("purpose").equals("get posts")) {
                        postList = new ArrayList<>();
                        JSONArray postsArray = (JSONArray) data.get("posts");
                        Log.d("ARRAY", postsArray.toString());
                        for (int i = 0; i < postsArray.length(); i++) {
                            JSONObject temp = (JSONObject) postsArray.get(i);
                            String title = (String) temp.get("title");
                            String desc = (String) temp.get("description");
                            String username = (String) temp.get("username");
                            postList.add(new PostEntry(username, title, desc, null));
                        }
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

    public HttpURLConnection openConnection(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        return connection;
    }

    public JSONObject processRequest(String link, String data) throws IOException, JSONException {
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
