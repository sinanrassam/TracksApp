package com.lostanimals.tracks;

import android.util.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Test;

import java.io.IOException;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    private static final String URL = "http://bosh.live:7536/phpmyadmin/tracks_api/connTest.php";

    @Mock
    ConnectionManager mockConnMan;

    @Before
    public void initMocks() {
        mockConnMan = new ConnectionManager();
    }

    @Test
    public void ConnectionManager_OpenConnection() throws IOException {
        assertEquals(500, mockConnMan.openConnection(URL).getResponseCode());

    }

    @Test
    public void ConnectionManager_ProcessRequest() throws IOException {

    }
}