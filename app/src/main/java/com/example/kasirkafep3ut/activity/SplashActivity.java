package com.example.kasirkafep3ut.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.kasirkafep3ut.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME =3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,
                        LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_TIME);
    }
}