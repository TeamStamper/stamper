package com.example.kyungyoungheo.stamper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        OnMarkerClickListener,
        ClusterManager.OnClusterClickListener<StamperMarker>,
        ClusterManager.OnClusterInfoWindowClickListener<StamperMarker>,
        ClusterManager.OnClusterItemClickListener<StamperMarker>,
        ClusterManager.OnClusterItemInfoWindowClickListener<StamperMarker> {


    private GoogleMap mMap;

    private ClusterManager<StamperMarker> mClusterManager;

    private static final  LatLng CSE = new LatLng(37.494567, 126.959715);
    private static final  LatLng SNU = new LatLng(37.481209, 126.952843);
    private static final  LatLng Sangdo = new LatLng(37.502984, 126.947762);
    private static final  LatLng Namsung = new LatLng(37.484814, 126.971175);
    private static final  LatLng Naeksungdae = new LatLng(37.477064, 126.963548);

    private Marker mMarkerCSE;
    private Marker mMarkerSNU;
    private Marker mMarkerSangdo;
    private Marker mMarkerNamsung;
    private Marker mMarkerNaeksungdae;

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
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
//        addMarkersToMap();
        // Add a marker in Sydney and move the camera

        Log.d("check test", "before setup");
        setUpCluster();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CSE,13));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        Context context = getApplicationContext();
        if(marker.equals(mMarkerCSE)){
            // Call video activity
            Intent intent = new Intent(this, VideoActivity.class);
            startActivity(intent);
        }
        return false;
    }

    private void addMarkersToMap(){
        mMarkerCSE =  mMap.addMarker(new MarkerOptions()
                .position(CSE)
                .title("Marker in 정보대")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ssucse",150,150))));
        mMarkerSNU = mMap.addMarker(new MarkerOptions()
                .position(SNU)
                .title("서울대입구역")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("snu",150,150))));
        mMarkerSangdo = mMap.addMarker(new MarkerOptions()
                .position(Sangdo)
                .title("상도역")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("sangdo",150,150))));
        mMarkerNamsung = mMap.addMarker(new MarkerOptions()
                .position(Namsung)
                .title("남성역")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("namsung",150,150))));
        mMarkerNaeksungdae = mMap.addMarker(new MarkerOptions()
                .position(Naeksungdae)
                .title("낙성대역")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("naeksungdae",150,150))));

    }

    private Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    private void setUpCluster(){

        mClusterManager = new ClusterManager<StamperMarker>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.

        mClusterManager.setRenderer(new StamperMarkerRenderer(this,mMap, mClusterManager));
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);

//        mClusterManager.setOnClusterClickListener(this);
//        mClusterManager.setOnClusterInfoWindowClickListener(this);
//        mClusterManager.setOnClusterItemClickListener(this);
//        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
        Log.d("check test", "before add");
        // Add cluster items (markers) to the cluster manager.
        addItemsToCluster();

        mClusterManager.cluster();

    }

    private void addItemsToCluster(){
        StamperMarker mMarkers[] = new StamperMarker[5];
        LatLng arrLatLng[] = new LatLng[5];
        String arrTitle[] = new String[5];

        arrLatLng[0] = new LatLng(37.494567, 126.959715);
        arrLatLng[1] = new LatLng(37.481209, 126.952843);
        arrLatLng[2] = new LatLng(37.502984, 126.947762);
        arrLatLng[3] = new LatLng(37.484814, 126.971175);
        arrLatLng[4] = new LatLng(37.477064, 126.963548);

        arrTitle[0] = "CSE";
        arrTitle[1] = "SNU";
        arrTitle[2] = "Sangdo";
        arrTitle[3] = "Namsung";
        arrTitle[4] = "Naeksungdae";



        for(int i=0; i<5; i++){
            mMarkers[i] = new StamperMarker(arrLatLng[i],arrTitle[i]);

            mClusterManager.addItem(mMarkers[i]);
        }
        mMarkers[0].setThumbnail(R.drawable.ssucse);
        mMarkers[1].setThumbnail(R.drawable.snu);
        mMarkers[2].setThumbnail(R.drawable.sangdo);
        mMarkers[3].setThumbnail(R.drawable.namsung);
        mMarkers[4].setThumbnail(R.drawable.naeksungdae);
//
//        StamperMarker mMarkerCSE = new LatLng(37.494567, 126.959715);
//        StamperMarker mMarkerSNU = new StamperMarker(37.481209, 126.952843);
//        StamperMarker mMarkerSangdo = new StamperMarker(37.502984, 126.947762);
//        StamperMarker mMarkerNamsung = new StamperMarker(37.484814, 126.971175);
//        StamperMarker mMarkerNaeksungdae = new StamperMarker(37.477064, 126.963548);
//
//        mClusterManager.addItem(mMarkerCSE);
//        mClusterManager.addItem(mMarkerSNU);
//        mClusterManager.addItem(mMarkerSangdo);
//        mClusterManager.addItem(mMarkerNamsung);
//        mClusterManager.addItem(mMarkerNaeksungdae);
    }

    @Override
    public boolean onClusterClick(Cluster<StamperMarker> cluster) {
        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<StamperMarker> cluster) {

    }

    @Override
    public boolean onClusterItemClick(StamperMarker stamperMarker) {
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(StamperMarker stamperMarker) {

    }
}
