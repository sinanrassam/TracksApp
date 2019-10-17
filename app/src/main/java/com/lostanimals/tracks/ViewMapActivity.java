package com.lostanimals.tracks;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

/**
 * For viewing a map of the last seen location. Started from PostActivity.
 */
public class ViewMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    // The position of the marker on the map
    private LatLng postLocation;

    /**
     * Create the Activity and setup the map marker.
     * @param savedInstanceState ignored
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);
        SupportMapFragment mapFragment;
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Animal last seen");

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Get the location of the post to add a marker to the map.
        String[] latLong = getIntent().getStringExtra("LOCATION").split(",");
        postLocation = new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]));
    }

    /**
     * ActionBar Back button
     * @return true to go back.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Make a map, display a marker where the post creator set and move the camera there.
     * @param googleMap the GoogleMaps instance we use to display a marker
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MarkerOptions lastSeenPin = new MarkerOptions()
                .title("Last Seen")
                .position(postLocation)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.addMarker(lastSeenPin);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(postLocation));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }
}
