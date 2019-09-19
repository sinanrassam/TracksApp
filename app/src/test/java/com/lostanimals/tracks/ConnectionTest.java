package com.lostanimals.tracks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    private static final String URL = "http://bosh.live:7536/phpmyadmin/tracks_api/connTest.php";

    @Mock
    ConnectionManager mockConnMan;

    @Before
    public void initMocks() {
        mockConnMan = new ConnectionManager();
    }

    // TODO: Fix this
    @Test
    public void ConnectionManager_OpenConnection() throws IOException {
        // assertTrue(mockConnMan.openConnection(URL+"Hi").getResponseMessage());
        mockConnMan.openConnection(URL+"blaj");
    }

    @Test
    public void ConnectionManager_ProcessRequest() throws IOException {

    }
}