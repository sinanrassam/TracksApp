package com.lostanimals.tracks;

import android.view.View;
import android.widget.AdapterView;

public class SortingSpinnerHandler implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
