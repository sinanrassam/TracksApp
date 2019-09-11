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
    ServerManager mockServerManager;
    @Mock
    Context mockContext;

    @Before
    public void initMocks() {
        mockServerManager = new ServerManager(mockContext);
    }

    @Test
    public void ServerManager_OpenConnectionTest() throws IOException {
        mockServerManager.openConnection(TEST_URL);
    }
}