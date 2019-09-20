package com.lostanimals.tracks;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URLEncoder;

import static org.junit.Assert.assertSame;

@RunWith (MockitoJUnitRunner.class)
public class RegisterTest {
	private static final String TEST_URL = "http://bosh.live:7536/phpmyadmin/tracks_api/";
	private static final String TEST_USER_EMAIL = "test@test.com";
	private static final String TEST_USER_USERNAME = "test";
	private static final String TEST_USER_PASSWORD = "test";
	private static final String TEST_USER_NAME = "Test";
	
	@Mock
	ConnectionManagerTestClass mockConnMan;
	
	@Mock
	JSONObject mockJSONObject;
	
	@Before
	public void initMocks() {
		mockConnMan = new ConnectionManagerTestClass();
	}
	
	@Test
	public void ServerManagerRegister_RegisterTest() throws IOException, JSONException {
		String postData = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_NAME, "UTF-8") + "&";
		postData += URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_EMAIL, "UTF-8") + "&";
		postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_USERNAME, "UTF-8") + "&";
		postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_PASSWORD, "UTF-8");
		// TODO: What are you testing?
		assertSame("success", (mockJSONObject = mockConnMan.processRequest("user.php", postData)).get("result"));
	}
}