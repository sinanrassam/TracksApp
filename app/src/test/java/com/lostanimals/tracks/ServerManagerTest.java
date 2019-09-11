package com.lostanimals.tracks;

import android.content.Context;
import com.lostanimals.tracks.utils.ServerManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Unit tests for the {@link ServerManager}
 */
@RunWith(MockitoJUnitRunner.class)
public class ServerManagerTest {
    private static final String TEST_USER_EMAIL = "test@test.com";
    private static final String TEST_USER_USERNAME = "test";
    private static final String TEST_USER_NAME = "Test";
    private static final String TEST_USER_PASSWORD = "test";

    private static final String TEST_POST_TITLE = "Test Post";
    private static final String TEST_POST_DESC = "Test Desc";
    private static final String TEST_URL = "http://bosh.live:7536/phpmyadmin/tracks_api/";
    private static String DATA = null;

    static {
        try {
            DATA = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_NAME, "UTF-8") + "&" +
                    URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_USERNAME, "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(TEST_POST_TITLE, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Mock
    ServerManager mMockAttemptLogin;
    @Mock
    Context mMockcontext;
    @Mock
    JSONObject json;

    @Before
    public void initMocks() {
        mMockAttemptLogin = new ServerManager(mMockcontext);
    }

    @Test
    public void AttemptLoginHelperTest_openConnection() throws IOException {
        mMockAttemptLogin.openConnection(TEST_URL);
    }

    @Test
    public void AttemptLoginHelperTest_processLoginRequest() throws IOException, JSONException {
        DATA = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("login", "UTF-8") + "&" +
                URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_USERNAME, "UTF-8") + "&" +
                URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_PASSWORD, "UTF-8");
        mMockAttemptLogin.processRequest(TEST_URL + "user.php", DATA);
    }

    @Test
    public void AttemptLoginHelperTest_processRegisterRequest() throws IOException, JSONException {
        DATA = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("register", "UTF-8") + "&" +
                URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_NAME, "UTF-8") + "&" +
                URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(TEST_USER_USERNAME, "UTF-8") + "&" +
                URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(TEST_POST_TITLE, "UTF-8");
        mMockAttemptLogin.processRequest(TEST_URL + "user.php", DATA);
    }

    @Test
    public void AttemptLoginHelperTest_processCreatePostRequest() throws IOException, JSONException {
        DATA = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("new post", "UTF-8") + "&" +
                URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(TEST_POST_TITLE, "UTF-8") + "&" +
                URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(TEST_POST_DESC, "UTF-8");
        mMockAttemptLogin.processRequest(TEST_URL + "post.php", DATA);
    }
}