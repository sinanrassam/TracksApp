package com.lostanimals.tracks;

import android.content.Context;
import com.lostanimals.tracks.utils.ServerManager;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URLEncoder;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {

    private static final String TEST_URL = "http://bosh.live:7536/phpmyadmin/tracks_api/";
    private static final String TEST_USER_EMAIL = "test@test.com";
    private static final String TEST_USER_USERNAME = "test";
    private static final String TEST_USER_PASSWORD = "test";
    private static String postData = null;

    @Mock
    ServerManager mServerManager;
    @Mock
    Context mMockContext;

    @Before
    public void initMocks() {
        mServerManager = new ServerManager(mMockContext);
    }

    @Test
    public void ServerManagerLogin_UsernameLogin() throws IOException, JSONException {
        postData = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("login", "UTF-8") + "&";
        postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_USERNAME, "UTF-8") + "&";
        postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_PASSWORD, "UTF-8");
        mServerManager.processRequest(TEST_URL + "user.php", postData);
    }

    @Test
    public void ServerManagerLogin_EmailLogin() throws IOException, JSONException {
        postData = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("login", "UTF-8") + "&";
        postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_EMAIL, "UTF-8") + "&";
        postData += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_PASSWORD, "UTF-8");
        mServerManager.processRequest(TEST_URL + "user.php", postData);
    }
}
