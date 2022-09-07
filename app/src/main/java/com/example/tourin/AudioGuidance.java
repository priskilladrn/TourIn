package com.example.tourin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class AudioGuidance extends AppCompatActivity {
    private ImageView ivFotoLokasiAudio,ivRewindAudio,ivPauseAudio,ivFastAudio;
    private SeekBar seekBarAudio;
    private TextView tvNamaTempatAudio,tvDetikMulaiAudio,tvDetikAkhirAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_guidance);

        init();

    }

    private void init() {
        ivFotoLokasiAudio = findViewById(R.id.ivFotoLokasiAudio);
        ivRewindAudio = findViewById(R.id.ivRewindAudio);
        ivPauseAudio = findViewById(R.id.ivPauseAudio);
        ivFastAudio = findViewById(R.id.ivFastAudio);
        seekBarAudio = findViewById(R.id.seekBarAudio);
        tvNamaTempatAudio = findViewById(R.id.tvNamaTempatAudio);
        tvDetikMulaiAudio = findViewById(R.id.tvDetikMulaiAudio);
        tvDetikAkhirAudio = findViewById(R.id.tvDetikAkhirAudio);
    }
}