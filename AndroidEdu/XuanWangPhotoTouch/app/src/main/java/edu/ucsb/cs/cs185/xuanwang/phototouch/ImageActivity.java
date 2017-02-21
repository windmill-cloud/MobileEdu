/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwang.phototouch;

import android.content.Intent;
import android.graphics.PointF;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.almeros.android.multitouch.MoveGestureDetector;
import com.almeros.android.multitouch.RotateGestureDetector;

public class ImageActivity extends AppCompatActivity implements View.OnTouchListener {
    private ScaleGestureDetector mScaleDetector;
    private float mScaleSpan = 1.0f;
    private float mRotationDegrees = 0.0f;
    private float mFocusX = 0.f;
    private float mFocusY = 0.f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mScaleDetector = new ScaleGestureDetector(getApplicationContext(), new ScaleListener());

        ImageView mImageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if (id != -1){
            mImageView.setImageBitmap(ImageManager.images.get(id));
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mScaleDetector.onTouchEvent(motionEvent);
        // ScaleDetector handled event at this point.
        // Perform your magic with mScaleSpan now!
        //
        return false;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleSpan = detector.getCurrentSpan(); // average distance between fingers
            return true;
        }
    }

    private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener {
        @Override
        public boolean onRotate(RotateGestureDetector detector) {
            mRotationDegrees -= detector.getRotationDegreesDelta();
            return true;
        }
    }

    private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
        @Override
        public boolean onMove(MoveGestureDetector detector) {
            PointF d = detector.getFocusDelta();
            mFocusX += d.x;
            mFocusY += d.y;

            // mFocusX = detector.getFocusX();
            // mFocusY = detector.getFocusY();
            return true;
        }
    }
}
