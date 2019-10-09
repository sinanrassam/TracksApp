package com.lostanimals.tracks;

import android.widget.ProgressBar;
import androidx.fragment.app.ListFragment;
import com.lostanimals.tracks.tasks.UpdatePostsTask;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ExecutionException;
import java.util.logging.Filter;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for filtering posts
 */
public class FiltersTest {
    public static final String TEST_USERNAME = "tester";
    public static final String TEST_FILTER = "";

    @Mock
    UpdatePostsTask updatePostsTask;

    @Before
    public void initiateMocks() {
        ListFragment listFragment = new ListFragment();
        ProgressBar progressBar = null;

        updatePostsTask = new UpdatePostsTask(listFragment, progressBar);
    }

    @Test
    public void filterTest() {
        try {
            updatePostsTask.execute(TEST_USERNAME, TEST_FILTER).get();
            assertEquals("success", updatePostsTask.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
