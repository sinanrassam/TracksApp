package com.lostanimals.tracks;

        import android.content.Context;
        import com.lostanimals.tracks.utils.ConnectionManager;
        import com.lostanimals.tracks.utils.tests.ConnMan;
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
    ConnMan mockConnMan;

    @Before
    public void initMocks() {
        mockConnMan = new ConnMan();
    }

    // TODO: Fix this
    @Test
    public void ConnectionManager_OpenConnection() throws IOException {

    }

    @Test
    public void ConnectionManager_ProcessRequest() throws IOException {

    }
}