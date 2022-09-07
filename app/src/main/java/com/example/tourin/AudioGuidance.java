package com.example.tourin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AudioGuidance extends AppCompatActivity {
    private ImageView ivFotoLokasiAudio,ivRewindAudio,ivPauseAudio,ivFastAudio, ivPlayAudio;
    private SeekBar seekBarAudio;
    private TextView tvNamaTempatAudio,tvDetikAkhirAudio, tvDetikMulaiAudio;

    private String imageUrl;
    private String audio;
    private String name;
    private MediaPlayer mp;
    private Integer length;
    private final Handler mHandler = new Handler();
    private boolean isPaused;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_guidance);

        isPaused = false;
        name = getIntent().getStringExtra("name");
        audio = getIntent().getStringExtra("audio");
        imageUrl = getIntent().getStringExtra("imageUrl");

        init();
        setData();
        setSeekBar();

        //paused audio
        ivPauseAudio.setOnClickListener(v -> {
            isPaused = true;
            ivPauseAudio.setVisibility(View.GONE);
            ivPlayAudio.setVisibility(View.VISIBLE);
            mp.pause();
        });

        //resume audio
        ivPlayAudio.setOnClickListener(v -> {
            isPaused = false;
            ivPauseAudio.setVisibility(View.VISIBLE);
            ivPlayAudio.setVisibility(View.GONE);
            length = mp.getCurrentPosition();
            mp.seekTo(length);
            mp.start();
        });

        //rewind
        ivRewindAudio.setOnClickListener(v -> {
            rewindSong();
            seekBarAudio.setProgress(mp.getCurrentPosition() / 1000);
        });

        //fast forward
        ivFastAudio.setOnClickListener(v -> {
            forwardSong();
            seekBarAudio.setProgress(mp.getCurrentPosition() / 1000);
        });
    }

    private void setSeekBar() {
        //update ui every second
        runnable = new Runnable() {
            @Override
            public void run() {
                if(mp != null){
                    seekBarAudio.setProgress(mp.getCurrentPosition() / 1000);
                    if(mp.getCurrentPosition() >= mp.getDuration()){
                        ivPauseAudio.setVisibility(View.GONE);
                        ivPlayAudio.setVisibility(View.VISIBLE);
                        tvDetikMulaiAudio.setText(formatDuration(mp.getDuration()));
                        Toast.makeText(AudioGuidance.this, "kelar", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        tvDetikMulaiAudio.setText(formatDuration(mp.getCurrentPosition()));
                    }
                }
                mHandler.postDelayed(this, 1000);
            }
        };
        AudioGuidance.this.runOnUiThread(runnable);

        //seekBar
        seekBarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //Notification that the progress level has changed.
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i  == 0 && mp != null && !mp.isPlaying()) {
                    AudioGuidance.this.seekBarAudio.setProgress(0);
                }
            }
            //Notification that the user has started a touch gesture.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mp != null && mp.isPlaying()) {
                    if(!isPaused){
                        mp.pause();
                    }
                    if(seekBar.getProgress() * 1000 >= mp.getDuration()){
                        mp.seekTo(mp.getDuration());
                    } else if(seekBar.getProgress() <= 0){
                        mp.seekTo(0);
                    } else {
                        mp.seekTo(seekBar.getProgress() * 1000);
                    }
                    tvDetikMulaiAudio.setText(formatDuration(mp.getCurrentPosition()));
                }
            }
            //Notification that the user has finished a touch gesture.
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mp != null) {
                    if(!isPaused){
                        mp.start();
                    }
                    if(seekBar.getProgress() * 1000 >= mp.getDuration()){
                        mp.seekTo(mp.getDuration());
                    } else if(seekBar.getProgress() <= 0){
                        mp.seekTo(0);
                    } else {
                        mp.seekTo(seekBar.getProgress() * 1000);
                    }
                    tvDetikMulaiAudio.setText(formatDuration(mp.getCurrentPosition()));
                }
            }
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
        tvDetikAkhirAudio = findViewById(R.id.tvDetikAkhirAudio);
        tvDetikMulaiAudio = findViewById(R.id.tvDetikMulaiAudio);
    }

    private void setData() {
        long duration;

        tvNamaTempatAudio.setText(name);
        Glide.with(this).load(imageUrl).into(ivFotoLokasiAudio);

        //set media player
        mp = new MediaPlayer();
        try {
            mp.setDataSource(audio);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    seekBarAudio.setMax(mp.getDuration()/1000);
                    mp.start();
                }
            });
            mp.prepare();
        } catch (IOException e) {
            Log.wtf("audio test", e);
        }

        //set audio duration
        if(mp != null){
            duration = mp.getDuration();
            String durationText = formatDuration(duration);
            Log.wtf("durasi audio", durationText);
            tvDetikAkhirAudio.setText(durationText);
        }
    }

    private String formatDuration(long duration) {
        long minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);
        long seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
                - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES);

        return String.format("%02d:%02d", minutes, seconds);
    }

    public void forwardSong() {
        if (mp != null) {
            int currentPosition = mp.getCurrentPosition();
            if (currentPosition + 10000 <= mp.getDuration()) {
                mp.seekTo(currentPosition + 10000);
            } else {
                mp.seekTo(mp.getDuration());
            }
        }
    }

    public void rewindSong() {
        if (mp != null) {
            int currentPosition = mp.getCurrentPosition();
            if (currentPosition - 10000 >= 0) {
                mp.seekTo(currentPosition - 10000);
            } else {
                mp.seekTo(0);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp != null) mp.release();
        mHandler.removeCallbacks(runnable);
    }

}