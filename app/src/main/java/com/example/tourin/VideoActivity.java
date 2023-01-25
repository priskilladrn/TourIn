package com.example.tourin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VideoActivity extends AppCompatActivity {
    private String video;
    private VideoView videoView;
    private ImageView btnCloseVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        video = getIntent().getStringExtra("video");
        videoView = findViewById(R.id.videoView);
        btnCloseVideo = findViewById(R.id.btnCloseVideo);

        videoView.setVideoPath(video);
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();

        btnCloseVideo.setOnClickListener(v -> {
            finish();
        });

    }

}