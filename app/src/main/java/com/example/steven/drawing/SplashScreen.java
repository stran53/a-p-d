package com.example.steven.drawing;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;

/**
 * Created by steven on 3/17/15.
 */

//TIMED SPLASH
public class SplashScreen extends Activity {

    private static int TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Intent j = new Intent(SplashScreen.this, SpotifyActivity.class);
                startActivity(j);

                // close this activity
                finish();
            }
        }, TIME);
    }

    }

//NETWORKED SPLASH
