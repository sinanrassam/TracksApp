package com.lostanimals.tracks.utils;

import android.os.Bundle;
import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

public class BundleManager {
    public static Bundle mPostBundle = new Bundle();

    public static String getPostID() {
        return mPostBundle.getString("id");
    }

    public static void setPostID(String newPostID) {
        mPostBundle.putString("id", newPostID);
    }

    public static LatLng getPostLocation() {
        return new LatLng(Double.parseDouble
                (Objects.requireNonNull(mPostBundle.getString("lat"))), Double.parseDouble
                (Objects.requireNonNull(mPostBundle.getString("lng"))));
    }

    public static void setPostLocation(LatLng newPostLocation) {
        mPostBundle.putString("lat", String.valueOf(newPostLocation.latitude));
        mPostBundle.putString("lng", String.valueOf(newPostLocation.longitude));
    }
}
