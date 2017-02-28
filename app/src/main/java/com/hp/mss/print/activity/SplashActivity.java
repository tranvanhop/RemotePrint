package com.hp.mss.print.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.hp.mss.print.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer = new Thread() {

            public void run() {
                try {
                    sleep(1000);// 4 second wait
                } catch (InterruptedException e) {
                    Log.d("ERROR",e.toString());
                    Thread.currentThread().interrupt();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}