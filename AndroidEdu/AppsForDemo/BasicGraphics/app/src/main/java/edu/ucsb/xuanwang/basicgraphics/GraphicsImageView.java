/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.xuanwang.basicgraphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by xuanwang on 3/31/17.
 */

public class GraphicsImageView extends AppCompatImageView {

    private Paint paint = new Paint();
    private boolean drawSomething = false;

    public GraphicsImageView(Context context) {
        super(context);
    }

    public GraphicsImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphicsImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (drawSomething) {
            int x = getWidth();
            int y = getHeight();
            int radius;
            radius = x / 4;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);
            paint.setColor(Color.BLUE);
            canvas.drawCircle(x / 2, y / 2, radius, paint);
        } else {
            canvas.drawColor(Color.WHITE);
        }
    }

    public void clear(){
        drawSomething = false;
        this.invalidate();
    }

    public void draw(){
        drawSomething = true;
        this.invalidate();
    }
}
