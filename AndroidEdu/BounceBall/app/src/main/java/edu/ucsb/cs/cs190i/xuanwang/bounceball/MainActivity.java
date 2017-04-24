/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.bounceball;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    View bouncingBallView = new BouncingBallView(this);
    bouncingBallView.setBackgroundColor(Color.BLACK);

    setContentView(bouncingBallView);

    DrawingThread drawingThread = new DrawingThread(bouncingBallView, 50);
    drawingThread.start();
  }

}
