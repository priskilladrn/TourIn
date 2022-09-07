package com.example.tourin;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class AudioGuidance extends AppCompatActivity {
    private ImageView ivFotoLokasiAudio,ivRewindAudio,ivPauseAudio,ivFastAudio, ivPlayAudio;
    private SeekBar seekBarAudio;
    private TextView tvNamaTempatAudio,tvDetikMulaiAudio,tvDetikAkhirAudio;

    private String imageUrl, audio, name;
    private MediaPlayer mp;
    private Integer length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_guidance);

        name = getIntent().getStringExtra("name");
        audio = getIntent().getStringExtra("audio");
        imageUrl = getIntent().getStringExtra("imageUrl");

        init();
        setData();

        //audio paused
        ivPauseAudio.setOnClickListener(v -> {
            ivPauseAudio.setVisibility(View.GONE);
            ivPlayAudio.setVisibility(View.VISIBLE);
            length = mp.getCurrentPosition();
            mp.pause();
        });

        //audio resume
        ivPlayAudio.setOnClickListener(v -> {
            ivPauseAudio.setVisibility(View.VISIBLE);
            ivPlayAudio.setVisibility(View.GONE);
            mp.seekTo(length);
            mp.start();
        });

    }

    private void init() {
        ivFotoLokasiAudio = findViewById(R.id.ivFotoLokasiAudio);
        ivRewindAudio = findViewById(R.id.ivRewindAudio);
        ivPauseAudio = findViewById(R.id.ivPauseAudio);
        ivPlayAudio = findViewById(R.id.ivPlayAudio);
        ivFastAudio = findViewById(R.id.ivFastAudio);
        seekBarAudio = findViewById(R.id.seekBarAudio);
        tvNamaTempatAudio = findViewById(R.id.tvNamaTempatAudio);
        tvDetikMulaiAudio = findViewById(R.id.tvDetikMulaiAudio);
        tvDetikAkhirAudio = findViewById(R.id.tvDetikAkhirAudio);
    }

    private void setData() {
        int duration;

        tvNamaTempatAudio.setText(name);
        Glide.with(this).load(imageUrl).into(ivFotoLokasiAudio);
        mp = new MediaPlayer();
        try {
            mp.setDataSource("https://firebasestorage.googleapis.com/v0/b/tourin-839e2.appspot.com/o/kete%20kesu%2Faudio%2Fureeka_ketekesu_01.mp3?alt=media&token=042a3b27-c004-49ab-b406-2b3ded427232");
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mp.start();
                }
            });
            mp.prepare();
        } catch (IOException e) {
            Log.wtf("audio test", e);
        }
        if(mp != null){
            duration = mp.getDuration();
            Log.wtf("durasi audio", duration + "");
        }
    }

    public void forwardSong() {
        if (mp != null) {
            int currentPosition = mp.getCurrentPosition();
            if (currentPosition + 10 <= mp.getDuration()) {
                mp.seekTo(currentPosition + 10);
            } else {
                mp.seekTo(mp.getDuration());
            }
        }
    }

    public void rewindSong() {
        if (mp != null) {
            int currentPosition = mp.getCurrentPosition();
            if (currentPosition - 10 >= 0) {
                mp.seekTo(currentPosition - 10);
            } else {
                mp.seekTo(0);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp != null) mp.release();
    }

}