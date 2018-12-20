package com.example.kyungyoungheo.stamper.VideoRecord;


import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.kyungyoungheo.stamper.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    ShowCamera showCamera;
    Drawable drawable;
    Circle circle;
    CircleAngleAnimation animation;

    long duration= 8000 ;
    long now ;
    long temp;
    long pause = 0;

    int index = 0;
    int count = 0;
    int angle;
    private Camera camera;
    private MediaRecorder mediaRecorder;
    private Button takeButton;

    boolean recording = false;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);

        takeButton = (Button)findViewById(R.id.button);
        int circleHeight = takeButton.getBackground().getIntrinsicHeight();
        circle = (Circle) findViewById(R.id.circle);


        animation = new CircleAngleAnimation(circle, 360);
        animation.setDuration(duration);


//        if(PackageManager.PERMISSION_GRANTED!= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
//        {
//            // 최초 권한 요청인지, 혹은 사용자에 의한 재요청인지 확인
//            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//
//                // 사용자가 임의로 권한을 취소한 경우
//                // 권한 재요청
//                requestPermissions(new String[]{Manifest.permission.CAMERA,
//                        Manifest.permission.RECORD_AUDIO,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                }, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//
//
//            } else {
//                // 최초로 권한을 요청하는 경우(첫실행)
//                requestPermissions(new String[]{Manifest.permission.CAMERA,
//                        Manifest.permission.RECORD_AUDIO,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                }, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//            }
//
//        }
//        else{ // 접근권한이 있을때
//
//            camera = Camera.open();
//            showCamera = new ShowCamera(this, camera);
//            frameLayout.addView(showCamera);
//
//        }


        camera = Camera.open();
        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);


        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{

                        camera.autoFocus(myAutofocusCallback);
                    }
                }
                return false;
            }
        });
        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;


                if(count%2 == 0){
                    now = SystemClock.uptimeMillis();
                    count++;
                }

                Log.d("Hello2",now+"");
                if (index % 2 == 0) { // 정지


                    temp = pause;

                    pause = SystemClock.uptimeMillis();
                    Log.d("Hello",(pause-now)+"");

                    mediaRecorder.pause();
                }
                if(index != 1 && index % 2 == 1){
                    mediaRecorder.resume(); // 중지부분을 재 시작.
                    index = 0;
                }
                else if(temp - now == 8000){ // 중간중간 끊어서 진행하다가 8초되면 저장.
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    camera.lock();
                    recording = false;
                    count = 0;
                }

                else if (index  == 1) { //첫 실행시 촬영 , 8초 저장한다음 촬영
                    circle.startAnimation(animation);
                    camera.unlock();
                    mediaRecorder = new MediaRecorder();

                    mediaRecorder.setCamera(camera);
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
                    mediaRecorder.setProfile(camcorderProfile);

                    mediaRecorder.setOrientationHint(90);
                    mediaRecorder.setMaxDuration(8000);
                    mediaRecorder.setOutputFile("/sdcard/DCIM/Camera/Test"+new Date().getTime()+".mp4");
                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        recording = true;
                    }catch (IOException e){
                        mediaRecorder.reset();

                    }


                }

            }
        });
    }
    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
        Log.e("error",mediaStorageDir.getPath());
        return mediaFile;
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                // 권한 허용
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //frameLayout = (FrameLayout) findViewById(R.id.framelayout);
                    camera = Camera.open();
                    showCamera = new ShowCamera(this, camera);
                    frameLayout.addView(showCamera);
                } else { //권한 허용 불가
                }
                return;
        }
    }

    // 오토 포커싱 설정.
    Camera.AutoFocusCallback myAutofocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            takeButton.setEnabled(true);
        }};
    Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {

        }};

}