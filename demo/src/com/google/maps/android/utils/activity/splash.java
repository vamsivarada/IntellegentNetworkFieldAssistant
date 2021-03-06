package com.google.maps.android.utils.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;

import com.google.maps.android.utils.demo.R;

public class splash extends Activity {

    private static int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(splash.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}