package com.example.kyungyoungheo.stamper;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class StamperMarker implements ClusterItem {

    private final LatLng mPosition;
    private int mThumbnail;
    private final String mTitle;


    StamperMarker(LatLng latlng, String title, int thumbnail){
        mPosition = latlng;
        mTitle = title;
        mThumbnail= thumbnail;
    }
    StamperMarker(LatLng latlng,  String title){
        mPosition = latlng;
        mTitle = title;
        mThumbnail= R.drawable.arrow;
    }
    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    public int getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(int res) {
        mThumbnail = res;
    }
}
