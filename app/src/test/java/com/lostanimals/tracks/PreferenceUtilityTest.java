package com.lostanimals.tracks;

import android.content.Context;
import com.lostanimals.tracks.utils.ConnectionManager;
import com.lostanimals.tracks.utils.PreferenceEntry;
import com.lostanimals.tracks.utils.PreferencesUtility;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Assert.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PreferenceUtilityTest {
	
	// The class to be tested.
	private PreferencesUtility preferencesUtility;
	
	@Mock
	Context mockContext;
	private Context context = mock(Context.class);
	
	@Before
	public void setup() {
		//PreferencesUtility.setSharedPreferences(context);
	}
	
	@Test
	public void PreferenceUtility_FindExistingCustomer() {
		PreferencesUtility.setSharedPreferences(context);
		assertTrue(PreferencesUtility.setUserInfo(new PreferenceEntry("admin", "admin", "admin")));
	}
}
