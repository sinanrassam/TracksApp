package com.lostanimals.tracks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    private static final String URL = "http://bosh.live:7536/phpmyadmin/tracks_api/connTest.php";

    @Mock
    ConnectionManagerTestClass mockConnMan;

    @Before
    public void initMocks() {
        mockConnMan = new ConnectionManagerTestClass();
    }

    @Test
    public void ConnectionManager_OpenConnection() throws IOException {
//        assertEquals(500, mockConnMan.openConnection(URL).getResponseCode());

    }

    @Test
    public void ConnectionManager_ProcessRequest() throws IOException {

    }
}