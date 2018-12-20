package com.example.kyungyoungheo.stamper.VideoView;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.kyungyoungheo.stamper.MapsActivity;
import com.example.kyungyoungheo.stamper.R;

import java.util.ArrayList;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager mViewPager;
    ArrayList<CardData> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        items = new ArrayList<CardData>();

        //items.add(new CardData("android.resource://" +this.getPackageName()+ "/"+ R.raw.my_videp));
        items.add(new CardData("https://scontent-icn1-1.cdninstagram.com/vp/e19c40110e3aa8cb175e021efce3bfc3/5BF412F7/t50.2886-16/45947265_285820362055724_6903763313860018176_n.mp4"));
        items.add(new CardData("https://scontent-icn1-1.cdninstagram.com/vp/5d486d60ca99e6194c75208abad16fe2/5BF49769/t50.2886-16/46429903_252984475396688_6899192424980021248_n.mp4"));
        items.add(new CardData("https://scontent-icn1-1.cdninstagram.com/vp/876fdbd7591faa9c15bfcf48ff1d8c4d/5BF3CC8A/t50.2886-16/45691507_435938910269168_4659424133245829120_n.mp4"));

        CardViewAdapter madapter = new CardViewAdapter(getLayoutInflater(), items);

        final TabAnimIndicator tabindicator = (TabAnimIndicator) findViewById(R.id.vp_indigator);

        CircleImageView circleProfile = (CircleImageView) findViewById(R.id.circle_image_profile);
        Glide.with(circleProfile).load("https://scontent-icn1-1.cdninstagram.com/vp/f7dc98b587418b6305914dbd63695fa6/5C7C25BD/t51.2885-15/e35/30909631_2012825295638994_8253872410613252096_n.jpg")
                .into(circleProfile);

        ViewPager vp= (ViewPager) findViewById(R.id.main_viewpager);
        vp.setAdapter(madapter);
        tabindicator.setAnimDuration(200);
        tabindicator.createDotPanel(items.size(),R.drawable.indicator_non, R.drawable.indicator_on);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabindicator.selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ImageView gotoMap = (ImageView) findViewById(R.id.video_view_map_icon);
        gotoMap.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.video_view_map_icon :
                finish();
                break;
        }

    }
}
