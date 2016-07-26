package com.trainingandroidpart1.physicianregistration;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class SplashScreenActivity extends AppCompatActivity {

    /** Duration of wait **/
    //private final int SPLASH_DISPLAY_LENGTH = 4000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);

        startActivity(intent);
        SplashScreenActivity.this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();

        //setContentView(R.layout.activity_splash_screen);

//        /* New Handler to start the Menu-Activity
//         * and close this Splash-Screen after some seconds.*/
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(SplashScreenActivity.this,MainActivity.class);
//                SplashScreenActivity.this.startActivity(mainIntent);
//                SplashScreenActivity.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
    }

}