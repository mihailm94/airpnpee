package com.appspot.airpeepee.airpeepee;

import android.app.ActionBar;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private VideoView mvideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mvideoView = (VideoView) findViewById(R.id.videoView);

        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bg_video);
        mvideoView.setVideoURI(uri);
        mvideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                mp.setLooping(true);
            }
        });
        
        mvideoView.start();



    }
}
