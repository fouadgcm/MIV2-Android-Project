package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private GameObject gameView;
    private Handler handler = new Handler();
    private final long interval=30;
    protected static final double MAX_UPS = 30.0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
//        int width = size.x;

        gameView = new GameObject(
                this,
                Math.random()*(size.x-168),
                0.0
        );
        setContentView(gameView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        gameView.invalidate();
                    }
                });
            }
        }, 0, interval);
    }


}