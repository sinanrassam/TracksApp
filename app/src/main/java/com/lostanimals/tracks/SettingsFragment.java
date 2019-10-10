package com.lostanimals.tracks;


import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class SettingsFragment  extends PreferenceFragmentCompat{

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);
    }
}
