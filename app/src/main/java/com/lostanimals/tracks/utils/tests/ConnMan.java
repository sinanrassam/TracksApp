package com.lostanimals.tracks.utils.tests;

import com.lostanimals.tracks.utils.PreferenceEntry;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Class ConnMan
 * Duplicate of Connection Manager that is non static and can be instantiated. Used for Unit testing only.
 */
public class ConnMan {
    private static final String URL = "http://bosh.live:7536/phpmyadmin/tracks_api/";

    public ConnMan() {

    }

    public String postEncoder(String type, String[] parameters) throws UnsupportedEncodingException {
        String postData = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type,
                "UTF-8") + "&";

        switch (type) {
            case "get":
                postData += URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(parameters[0],
                        "UTF-8");
                break;
            case "new-comment":
                postData += URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(parameters[0],
                        "UTF-8") + "&";
                postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[1],
                        "UTF-8") + "&";
                postData += URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(parameters[2],
                        "UTF-8") + "&";
                break;
            case "login":
                postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[0],
                        "UTF-8") + "&";
                postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(parameters[1],
                        "UTF-8");
                break;
            case "register":
                postData += URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(parameters[0],
                        "UTF-8") + "&";
                postData += URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(parameters[1],
                        "UTF-8") + "&";
                postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[2],
                        "UTF-8") + "&";
                postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(parameters[3],
                        "UTF-8");
                break;
            case "reset":
                // TODO: Implement reset password POST request
                break;
            case "new-post":
                // TODO: Can found be encoded as a bool?
                postData += URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(parameters[0],
                        "UTF-8") + "&";
                postData += URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(parameters[1],
                        "UTF-8") + "&";
                postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[2],
                        "UTF-8");
                break;
            case "edit":
                // TODO: Can found be encoded as a bool?
                postData += URLEncoder.encode("found", "UTF-8") + "=" + URLEncoder.encode(parameters[0],
                        "UTF-8") + "&";
                break;
        }
        return postData;
    }

    public HttpURLConnection openConnection(String fullURL) throws IOException {
        URL url = new URL(fullURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        return connection;
    }

    public JSONObject processRequest(String script, String data) throws IOException, JSONException {
        HttpURLConnection conn = openConnection(URL + script);

        // Send the request to the server
        OutputStream outputStream = conn.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                StandardCharsets.UTF_8));
        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();

        // Read the server response and return it as JSON
        InputStream inputStream = conn.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
                StandardCharsets.ISO_8859_1));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = new JSONObject(sb.toString());
        bufferedReader.close();
        inputStream.close();
        conn.disconnect();
        return json;
    }

    public static PreferenceEntry login(JSONObject details) throws JSONException {
        return new PreferenceEntry(details.getString("name"),
                details.getString("username"), details.getString("email"));
    }
}