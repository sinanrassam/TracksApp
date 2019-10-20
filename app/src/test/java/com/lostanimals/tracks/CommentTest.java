package com.lostanimals.tracks;


import com.lostanimals.tracks.tasks.NewCommentTask;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the EmailValidator logic.
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentTest {
    private static final String TEST_USER_USERNAME = "automatedTester";
    private static final String TEST_POST_ID = "4";
    private static final String TEST_POST_MESSAGE = "Automated Test CommentEntry for post" + TEST_POST_ID;


    //    Context mockContext;
    @Mock
    NewCommentTask newCommentTask;

    @Before
    public void initMocks() {
        //        mockContext = null;
        newCommentTask = new NewCommentTask();

    }

    @Test
    public void NewComment_test() throws ExecutionException, InterruptedException, JSONException {
        //newCommentTask = new NewCommentTask();
        JSONObject result = newCommentTask.execute(TEST_POST_ID, TEST_USER_USERNAME, TEST_POST_MESSAGE).get();
        System.out.println(result.get("response"));
        assertEquals("success", result.get("response"));
    }
}