package com.lostanimals.tracks;

        import android.content.Context;
        import com.lostanimals.tracks.utils.ConnectionManager;
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
//    ServerManager_Backup mockServerManager;
    ConnectionManager mockConnectionManager;
    @Mock
    Context mockContext;

    @Before
    public void initMocks() {
//        mockServerManager = new ServerManager_Backup(mockContext);
    }

    // TODO: Fix this
    @Test
    public void ServerManager_OpenConnectionTest() throws IOException {

    }
}