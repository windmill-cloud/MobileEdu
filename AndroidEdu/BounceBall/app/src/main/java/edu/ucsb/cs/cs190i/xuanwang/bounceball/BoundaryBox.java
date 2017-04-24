/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.bounceball;

/**
 * Created by xuanwang on 4/23/17.
 */
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class BoundaryBox {

  int xMin, xMax, yMin, yMax;
  private Paint paint;  // paint style and color
  private Rect bounds;

  public BoundaryBox(int color) {
    paint = new Paint();
    paint.setColor(color);
    bounds = new Rect();
  }

  public void set(int x, int y, int width, int height) {
    xMin = x;
    xMax = x + width - 1;
    yMin = y;
    yMax = y + height - 1;
    // The box's bounds do not change unless the view's size changes
    bounds.set(xMin, yMin, xMax, yMax);
  }

  public void draw(Canvas canvas) {
    canvas.drawRect(bounds, paint);
  }
}