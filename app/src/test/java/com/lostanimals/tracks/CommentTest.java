package com.lostanimals.tracks;


import android.content.Context;
import android.util.Log;
import com.lostanimals.tracks.utils.NewCommentTask;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.xml.transform.Result;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the EmailValidator logic.
 */
public class CommentTest {
    private static final String TEST_USER_USERNAME = "automatedTester";
    private static final String TEST_POST_ID = "4";
    private static final String TEST_POST_MESSAGE = "Automated Test Comment for post" + TEST_POST_ID;

    @Mock
    Context mockContext;

    @Before
    public void initMocks() {
        mockContext = null;
    }

    @Test
    public void NewComment_test() throws ExecutionException, InterruptedException, JSONException {
        NewCommentTask newCommentTask = new NewCommentTask(mockContext);
        JSONObject result = newCommentTask.execute(TEST_POST_ID, TEST_USER_USERNAME, TEST_POST_MESSAGE).get();
        System.out.println(result.get("response"));
        assertEquals("success", result.get("response"));
    }
}