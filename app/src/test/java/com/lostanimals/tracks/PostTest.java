package com.lostanimals.tracks;


import android.content.Context;
import com.lostanimals.tracks.tasks.NewCommentTask;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.concurrent.ExecutionException;

/**
 * Unit tests for the EmailValidator logic.
 */
public class PostTest {
    private static final String TEST_USER_USERNAME = "automatedTester";
    private static final String TEST_POST_ID = "4";
    private static final String TEST_POST_MESSAGE = "Automated Test CommentEntry for post" + TEST_POST_ID;

    @Mock
    Context mockContext;

    @Before
    public void initMocks() {
        mockContext = null;
    }

    @Test
    public void NewComment_test() throws ExecutionException, InterruptedException, JSONException {
        NewCommentTask newCommentTask = new NewCommentTask(null);
        newCommentTask.execute(TEST_POST_ID, TEST_USER_USERNAME, TEST_POST_MESSAGE).get();
        //        assertEquals("success", result.get("response"));
    }
}