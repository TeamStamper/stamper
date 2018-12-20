package com.example.kyungyoungheo.stamper.VideoView;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kyungyoungheo.stamper.R;

import java.util.List;

public class CardViewAdapter extends PagerAdapter {

    LayoutInflater inflater;
    List<CardData> mPageList;

    String VIDEO_TEMP;

    SimpleVideoView videoView;

    View.OnClickListener onItemClick;


    public CardViewAdapter(LayoutInflater inflater, List<CardData> mPageList){
        this.inflater = inflater;
        this.mPageList = mPageList;
    }

    @Override
    public int getCount() {
        return mPageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.cardview_adapter, container, false);
        CardData item = mPageList.get(position);

        VIDEO_TEMP = item.videoUrl;

        videoView = (SimpleVideoView) view.findViewById(R.id.video_view);
        videoView.setErrorTracker(new SimpleVideoView.VideoPlaybackErrorTracker() {
            @Override
            public void onPlaybackError(Exception e) {
                e.printStackTrace();
                Snackbar.make(videoView, "Video Error!", Snackbar.LENGTH_INDEFINITE).show();
            }
        });
        videoView.start(VIDEO_TEMP);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoView.isPlaying())
                    videoView.pause();
                else
                    videoView.play();
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setOnItemClickListener(View.OnClickListener l){
        onItemClick = l;
    }
}
