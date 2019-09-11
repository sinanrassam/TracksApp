package com.lostanimals.tracks;

import android.content.Context;
import com.lostanimals.tracks.utils.ServerManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    private static final String TEST_URL = "http://bosh.live:7536/phpmyadmin/tracks_api/";

    @Mock
    ServerManager mMockAttemptLogin;
    @Mock
    Context mMockContext;

    @Before
    public void initMocks() {
        mMockAttemptLogin = new ServerManager(mMockContext);
    }

    @Test
    public void ServerManager_OpenConnectionTest() throws IOException {
        mMockAttemptLogin.openConnection(TEST_URL);
    }
}