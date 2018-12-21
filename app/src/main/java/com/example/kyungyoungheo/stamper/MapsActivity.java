package com.example.kyungyoungheo.stamper;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.kyungyoungheo.stamper.VideoRecord.VideoActivity;
import com.example.kyungyoungheo.stamper.VideoView.VideoViewActivity;
import com.example.kyungyoungheo.stamper.Map.PermissionUtils;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        OnMarkerClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ClusterManager.OnClusterClickListener<StamperMarker>,
        ClusterManager.OnClusterInfoWindowClickListener<StamperMarker>,
        ClusterManager.OnClusterItemClickListener<StamperMarker>,
        ClusterManager.OnClusterItemInfoWindowClickListener<StamperMarker> {


    private GoogleMap mMap;
    private ClusterManager<StamperMarker> mClusterManager;
    private FusedLocationProviderClient mFusedLocationProviderClient;

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

    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public ImageView cameraBtn;
    public RelativeLayout slidingViewMain;

    public void init(){
        cameraBtn = (ImageView) findViewById(R.id.camera_btn);
        slidingViewMain = (RelativeLayout) findViewById(R.id.sliding_view_main);
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
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
//        addMarkersToMap();
        // Add a marker in Sydney and move the camera
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        Log.d("check test", "before setup");
        setUpCluster();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CSE,13));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        if(PackageManager.PERMISSION_GRANTED!= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            // 최초 권한 요청인지, 혹은 사용자에 의한 재요청인지 확인
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {

                // 사용자가 임의로 권한을 취소한 경우
                // 권한 재요청
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, MY_PERMISSIONS_REQUEST_READ_CONTACTS);


            } else {
                // 최초로 권한을 요청하는 경우(첫실행)
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }

        }



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        init();

        cameraBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                        Intent intent = new Intent(MapsActivity.this, VideoActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_up,R.anim.push_out_right);
                }
                return true;
            }
        });


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

//        Context context = getApplicationContext();
//        if(marker.equals(mMarkerCSE)){
//            // Call video activity
//            Intent intent = new Intent(this, VideoViewActivity.class);
//            startActivity(intent);
//        }
        Toast.makeText(this, "chekck", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, VideoViewActivity.class);
        startActivity(intent);
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
        mMap.setOnMarkerClickListener(this);
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

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
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
        Toast.makeText(this, "check", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, VideoViewActivity.class);

        // VideoViewActivity에 현재 클릭한 마커의 정보, 좌표를 전달한다.
        LatLng markerPos = stamperMarker.getPosition();
        intent.putExtra("markerPos",markerPos);

        startActivity(intent);
        return true;
    }

    @Override
    public void onClusterItemInfoWindowClick(StamperMarker stamperMarker) {

    }
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }
}
