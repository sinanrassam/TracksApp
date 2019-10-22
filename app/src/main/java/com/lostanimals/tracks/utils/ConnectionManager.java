package com.lostanimals.tracks.utils;

import com.lostanimals.tracks.entries.PreferenceEntry;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ConnectionManager {
	public static final String URL = "http://bosh.live:7536/phpmyadmin/tracks_api/";
	
	public static String postEncoder(String type, String[] parameters) throws UnsupportedEncodingException {
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = EmojiRemover.processString(parameters[i]);
		}
		
		String postData = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&";
		
		switch (type) {
			case "get-posts":
				postData += URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
				postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8") + "&";
				postData += URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8") + "&";
				postData += URLEncoder.encode("myPosts", "UTF-8") + "=" + URLEncoder.encode(parameters[3], "UTF-8") + "&";
                postData += URLEncoder.encode("filters", "UTF-8") + "=" + URLEncoder.encode(parameters[4], "UTF-8");
				break;
			case "new-comment":
				postData += URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
				postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8") + "&";
				postData += URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8") + "&";
				break;
			case "get-comments":
				postData += URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8");
				break;
			case "login":
				postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
				postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8");
				break;
			case "register":
				postData += URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
				postData += URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8") + "&";
				postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8") + "&";
				postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(parameters[3], "UTF-8");
				break;
			case "new-post":
				postData += URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
				postData += URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8") + "&";
				postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8") + "&";
                postData += URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(parameters[3], "UTF-8");
				break;
			case "edit-post":
				postData += URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
				postData += URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8") + "&";
				postData += URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8") + "&";
				postData += URLEncoder.encode("found", "UTF-8") + "=" + URLEncoder.encode(parameters[3], "UTF-8") + "&";
                postData += URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(parameters[4], "UTF-8");
				break;
			case "delete-post":
				postData += URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8");
				break;
			case "unfollow-post": //fall through
			case "follow-post":
				postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
				postData += URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8");
				break;
			case "get-followed-posts":
				postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8");
				break;
            case "upload-image":
                postData += URLEncoder.encode("image_type", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
                postData += URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8") + "&";
                postData += URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8");
                break;
            case "get-image":
                postData += URLEncoder.encode("url", "UTF-8") + "=" + URLEncoder.encode(URL + parameters[0], "UTF-8");
                break;
            case "update":
                postData += URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(parameters[0], "UTF-8") + "&";
                postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(parameters[1], "UTF-8") + "&";
                postData += URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(parameters[2], "UTF-8") + "&";
                postData += URLEncoder.encode("old_password", "UTF-8") + "=" + URLEncoder.encode(parameters[3], "UTF-8") + "&";
                postData += URLEncoder.encode("new_password", "UTF-8") + "=" + URLEncoder.encode(parameters[4], "UTF-8");
                break;
		}
		return postData;
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
	
	private static HttpURLConnection openConnection(String fullURL) throws IOException {
		URL url = new URL(fullURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		return connection;
	}
	
	public static PreferenceEntry login(JSONObject details) throws JSONException {
		return new PreferenceEntry(details.getString("name"), details.getString("username"), details.getString("email"));
	}
}