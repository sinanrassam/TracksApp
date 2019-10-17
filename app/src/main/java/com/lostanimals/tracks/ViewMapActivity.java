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

public class ViewMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    LatLng postLocation;

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

        // TODO: Add code to viewing. (reminder if no location set, what do?)
        String[] latLong = getIntent().getStringExtra("LOCATION").split(",");
        double longitude = Double.parseDouble(latLong[0]);
        double latitude = Double.parseDouble(latLong[1]);

        postLocation = new LatLng(longitude, latitude);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
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
