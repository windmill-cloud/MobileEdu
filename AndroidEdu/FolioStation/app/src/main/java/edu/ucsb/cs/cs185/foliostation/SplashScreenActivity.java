/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.ucsb.cs.cs185.foliostation.utilities.PicassoImageLoader;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView background;
    int idx = 0;
    final Handler myHandler = new Handler();
    List<Integer> backgroundImgList = new ArrayList<>();
    private final int SHIMMER_DURATION = 1000;
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    private final String TAG = "Splash";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        inflateBackgroundPicList();

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

        mAuth = FirebaseAuth.getInstance();

        final Button signUpButton = (Button) findViewById(R.id.sign_up);
        signUpButton.getBackground().setColorFilter(
                ContextCompat.getColor(getApplicationContext(), R.color.splashGreenTransparent),
                PorterDuff.Mode.MULTIPLY);
        final Button logInButton = (Button) findViewById(R.id.log_in);
        logInButton.getBackground().setColorFilter(
                ContextCompat.getColor(getApplicationContext(), R.color.splashWhiteTransparent),
                PorterDuff.Mode.MULTIPLY);

        signUpButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(SplashScreenActivity.this,
                        LoginSignupActivity.class);
                signUpIntent.putExtra("TYPE", "SIGN_UP");
                SplashScreenActivity.this.startActivity(signUpIntent);
                SplashScreenActivity.this.finish();
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent logInIntent = new Intent(SplashScreenActivity.this,
                        LoginSignupActivity.class);
                logInIntent.putExtra("TYPE", "LOG_IN");
                SplashScreenActivity.this.startActivity(logInIntent);
                SplashScreenActivity.this.finish();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    signUpButton.setVisibility(View.GONE);
                    signUpButton.setEnabled(false);
                    logInButton.setVisibility(View.GONE);
                    logInButton.setEnabled(false);

                     /* New Handler to start the Menu-Activity
                    * and close this Splash-Screen after some seconds.*/
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                            Intent mainIntent = new Intent(SplashScreenActivity.this,
                                    ContainerActivity.class);
                            SplashScreenActivity.this.startActivity(mainIntent);
                            SplashScreenActivity.this.finish();
                        }
                    }, SPLASH_DISPLAY_LENGTH);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    signUpButton.getBackground().setColorFilter(
                            ContextCompat.getColor(getApplicationContext(), R.color.splashGreenTransparent),
                            PorterDuff.Mode.MULTIPLY);
                    logInButton.getBackground().setColorFilter(
                            ContextCompat.getColor(getApplicationContext(), R.color.splashWhiteTransparent),
                            PorterDuff.Mode.MULTIPLY);
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void inflateBackgroundPicList() {
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


