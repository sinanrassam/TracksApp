package com.lostanimals.tracks;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;

public class MapsActivityGetCurrentLocation extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivityGetCurrentLocation.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;
    private GeoDataClient mGeoDataClient;

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
