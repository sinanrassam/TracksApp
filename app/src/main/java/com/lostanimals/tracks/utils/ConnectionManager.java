package com.lostanimals.tracks.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ConnectionManager {
    private static final String URL = "http://bosh.live:7536/phpmyadmin/tracks_api/";

    public static String postEncoder(String type, String[] parameters) throws UnsupportedEncodingException {
        String postData = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&";

        if (type.equals("get")) {
            postData += URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8");
        } else if (type.equals("login")) {
            postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
            postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8");
        } else if (type.equals("register")) {
            postData += URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
            postData += URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8") + "&";
            postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8") + "&";
            postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(parameters[3], "UTF-8");
        } else if (type.equals("reset")) {
            // TODO: Implement reset password POST request
        } else if (type.equals("new")) {
            postData += URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
            postData += URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8") + "&";
            postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8");
        }
        return postData;
    }

    private static HttpURLConnection openConnection(String fullURL) throws IOException {
        URL url = new URL(fullURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        return connection;
    }

    public static JSONObject processRequest(String script, String data) throws IOException, JSONException {
        HttpURLConnection conn = openConnection(URL + script);

        // Send the request to the server
        OutputStream outputStream = conn.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();

        // Read the server response and return it as JSON
        InputStream inputStream = conn.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
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
}