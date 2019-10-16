package com.lostanimals.tracks;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lostanimals.tracks.utils.BundleManager;
import com.lostanimals.tracks.utils.PermissionManager;
import com.lostanimals.tracks.utils.PostsUtility;

import java.util.Objects;

public class SetLocationActivity extends AppCompatActivity implements OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    private MarkerOptions lastSeenPin = null;

    private LatLng pinLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Long press to set a point");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMapLongClickListener(this);

        enableUserLocation();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng((getUserLocation())));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionManager.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Long press on the map to add a pin", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionManager.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableUserLocation();
        } else {
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            showPermissionError();
            mPermissionDenied = false;
        }
    }

    private LatLng getUserLocation() {
        LocationManager locationMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        // TODO: Fix this
        @SuppressLint("MissingPermission") Location location = locationMan.getLastKnownLocation(locationMan.getBestProvider(criteria, false));
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        return new LatLng(lat, lng);
    }

    private void showPermissionError() {
        PermissionManager.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    /**
     * Listener method for long presses on the map.
     * There is only allowed to be one pin on the map at a time.
     * When a pin is added, the location is stored in a LatLng.
     * @param point the point on the map where the user long presses
     */
    @Override
    public void onMapLongClick(LatLng point) {
        Toast.makeText(this, point.latitude+" "+point.longitude, Toast.LENGTH_SHORT).show();

        if (lastSeenPin != null) {
            mMap.clear();
        }

        lastSeenPin = new MarkerOptions()
                .position(point)
                .title("Last Seen")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        mMap.addMarker(lastSeenPin);

        pinLocation = lastSeenPin.getPosition();
    }

    /**
     * On activity paused update the LatLng in the post entry.
     */
    @Override
    protected void onPause() {
        super.onPause();
//        if (PostsUtility.getPostEntry(BundleManager.getPostID()) != null) {
//            PostsUtility.getPostEntry(BundleManager.getPostID()).setLocation(pinLocation);
//        }
        if (pinLocation != null) {
            Toast.makeText(this, "Paused! " + pinLocation.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}