package com.example.tourin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    private  static  int SPLASH_SCREEN =  5000;

    Animation topAnim , bottomAnim;
    ImageView imageLogo, textLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageLogo = findViewById(R.id.logoTourIn);
        textLogo = findViewById(R.id.textSplash);

        imageLogo.setAnimation(topAnim);
        textLogo.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent move = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(move);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}