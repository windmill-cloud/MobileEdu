/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite.bouncingball;

/**
 * Created by xuanwang on 4/23/17.
 */

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

class Ball {
  private float radius = 50;      // Ball's radius
  float x = radius + 20;  // Ball's center (x,y)
  float y = radius + 40;
  float speedX = 10;       // Ball's speed (x,y)
  float speedY = 20;
  private RectF bounds;   // Needed for Canvas.drawOval
  private Paint paint;    // The paint style, color used for drawing

  // Constructor
  Ball(int color) {
    bounds = new RectF();
    paint = new Paint();
    paint.setColor(color);
  }

  void moveWithCollisionDetection(BoundaryBox box) {
    x += speedX;
    y += speedY;

    if (x + radius > box.xMax) {
      speedX = -speedX;
      x = box.xMax-radius;
    } else if (x - radius < box.xMin) {
      speedX = -speedX;
      x = box.xMin+radius;
    }
    if (y + radius > box.yMax) {
      speedY = -speedY;
      y = box.yMax - radius;
    } else if (y - radius < box.yMin) {
      speedY = -speedY;
      y = box.yMin + radius;
    }
  }

  void draw(Canvas canvas) {
    bounds.set(x-radius, y-radius, x+radius, y+radius);
    canvas.drawOval(bounds, paint);
  }
}