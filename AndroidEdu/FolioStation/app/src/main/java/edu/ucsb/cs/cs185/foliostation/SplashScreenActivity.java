/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation;

import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView background;
    int idx = 0;
    final Handler myHandler = new Handler();
    List<Integer> backgroundImgList = new ArrayList<>();
    private final int SHIMMER_DURATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        inflateList();

        background = (ImageView) findViewById(R.id.splash_image);

        ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.setDuration(SHIMMER_DURATION);
        container.startShimmerAnimation();


        Timer changeBackgroundTimer = new Timer();
        changeBackgroundTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateBackground();
            }
        }, 0, 3000);

        Button signUpButton = (Button) findViewById(R.id.sign_up);
        signUpButton.getBackground().setColorFilter(
                ContextCompat.getColor(getApplicationContext(), R.color.splashGreenTransparent),
                PorterDuff.Mode.MULTIPLY);
        Button logInButton = (Button) findViewById(R.id.log_in);
        logInButton.getBackground().setColorFilter(
                ContextCompat.getColor(getApplicationContext(), R.color.splashWhiteTransparent),
                PorterDuff.Mode.MULTIPLY);
    }

    private void inflateList() {
        backgroundImgList.add(R.drawable.back1);
        backgroundImgList.add(R.drawable.back2);
        backgroundImgList.add(R.drawable.back3);
        backgroundImgList.add(R.drawable.back4);
    }

    private void updateBackground() {
        idx++;
        if (idx >= backgroundImgList.size()) {
            idx = 0;
        }
        myHandler.post(myRunnable);
    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            background.setImageResource(backgroundImgList.get(idx));
        }
    };

}


