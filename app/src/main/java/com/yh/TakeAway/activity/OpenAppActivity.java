package com.yh.TakeAway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.yh.TakeAway.R;

public class OpenAppActivity extends AppCompatActivity {
    private static final long ANIMATION_DURATION = 1500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){

        super.onCreate(savedInstanceState);//初始化
        setContentView(R.layout.layout_openapp);
        LottieAnimationView animationView = findViewById(R.id.open_an);
        animationView.setAnimation(R.raw.open);
        animationView.playAnimation();

        new Handler(Looper.getMainLooper()).postDelayed(()->{
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        },ANIMATION_DURATION);
    }
}
