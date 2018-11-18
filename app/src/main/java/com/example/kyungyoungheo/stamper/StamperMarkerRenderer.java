package com.example.kyungyoungheo.stamper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;
import java.util.List;

import com.google.maps.android.ui.IconGenerator;

public class StamperMarkerRenderer extends DefaultClusterRenderer<StamperMarker> {
    private ClusterManager<StamperMarker> mClusterManager;
    private final IconGenerator mIconGenerator;
    private final IconGenerator mClusterIconGenerator;
    private final ImageView mImageView;
    private final ImageView mClusterImageView;
    private final int mDimension;

    public StamperMarkerRenderer(Context context, GoogleMap map, ClusterManager<StamperMarker> clusterManager) {
        super(context, map, clusterManager);
        Log.d("test11","after marker render");
        mIconGenerator = new IconGenerator(context);
        mClusterIconGenerator = new IconGenerator(context);
        mClusterManager = clusterManager;

        LayoutInflater inflater = LayoutInflater.from(context);

        View multiProfile = inflater.inflate(R.layout.multi_profile, null);
        mClusterIconGenerator.setContentView(multiProfile);
        mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);

        mImageView = new ImageView(context);
//        mDimension = (int) context.getResources().getDimension(R.dimen.custom_profile_image);
        mDimension = 150;
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));

//        int padding = (int) context.getResources().getDimension(R.dimen.custom_profile_padding);
//        mImageView.setPadding(padding, padding, padding, padding);
        mIconGenerator.setContentView(mImageView);

    }


    @Override
    protected void onBeforeClusterItemRendered(StamperMarker marker, MarkerOptions markerOptions) {
        // Draw a single person.
        // Set the info window to show their name.
        mImageView.setImageResource(marker.getThumbnail());
        Bitmap icon = mIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(marker.getTitle());
    }


//    @Override
//    protected void onBeforeClusterRendered(Cluster<StamperMarker> cluster, MarkerOptions markerOptions) {
//        List<Drawable> MarkerPhotos = new ArrayList<Drawable>(5);
//
//        for(StamperMarker sm : cluster.getItems()){
//            Drawable drawable =
//            MarkerPhotos.add()
//        }
//    }


}


/*
@Override
        protected void onBeforeClusterRendered(Cluster<Person> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
            List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
            int width = mDimension;
            int height = mDimension;

            for (Person p : cluster.getItems()) {
                // Draw 4 at most.
                if (profilePhotos.size() == 4) break;
                Drawable drawable = getResources().getDrawable(p.profilePhoto);
                drawable.setBounds(0, 0, width, height);
                profilePhotos.add(drawable);
            }
            MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
            multiDrawable.setBounds(0, 0, width, height);

            mClusterImageView.setImageDrawable(multiDrawable);
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }
 */