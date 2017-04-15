/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.ece150.cs190i.xuanwang.exampledrawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuanwang on 4/14/17.
 */


public class DrawingView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Map<Integer, PointF> pointers;
    private int[] colors;
    private Canvas paintingCanvas;
    private Bitmap painting;
    private boolean initialized = false;

    public void setRadius(int radius) {
        this.radius = radius;
    }

    private int radius = 1;


    public DrawingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Log.d("SurfaceView", "init");

        pointers = new HashMap<>();
        colors = new int[]{
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.BLACK,
                Color.YELLOW,
                Color.MAGENTA,
                Color.CYAN,
                Color.LTGRAY
        };
        holder = getHolder();
        holder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        if(!initialized){
            Display display = ((MainActivity) getContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int w = size.x;
            int h = size.y;
            int dimension = Math.max(w, h);
            painting = Bitmap.createBitmap(dimension, dimension, Bitmap.Config.ARGB_8888);
            paintingCanvas = new Canvas();
            paintingCanvas.setBitmap(painting);

            initialized = true;

        }
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(painting, 0, 0, null);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int id = event.getPointerId(index);
        PointF point = new PointF();

        point.x = event.getX(index);
        point.y = event.getY(index);
        switch(action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                pointers.put(id, point);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                pointers.remove(id);
                break;
            case MotionEvent.ACTION_MOVE:
                for(int i = 0; i < event.getPointerCount();i++){
                    int currentId = event.getPointerId(i);
                    PointF currentPoint = new PointF();
                    currentPoint.x = event.getX(i);
                    currentPoint.y = event.getY(i);
                    if(holder != null) {
                        PointF oldPoint = pointers.get(currentId);
                        Paint paint = new Paint();
                        paint.setColor(colors[currentId % colors.length]);
                        paint.setStrokeWidth(2*radius);
                        paintingCanvas.drawLine(oldPoint.x, oldPoint.y, currentPoint.x, currentPoint.y,  paint);

                        Canvas canvas = holder.lockCanvas();
                        canvas.drawColor(Color.WHITE);
                        canvas.drawBitmap(painting, 0, 0, null);
                        holder.unlockCanvasAndPost(canvas);
                    }
                    pointers.put(currentId, currentPoint);
                }
        }
        drawPointers();
        //return super.onTouchEvent(event);
        return true;
    }

    private void drawPointers(){
        if(holder != null) {
            for(int id: pointers.keySet()){
                PointF point = pointers.get(id);
                Paint paint = new Paint();
                paint.setColor(colors[id % colors.length]);
                paintingCanvas.drawCircle(point.x, point.y, radius, paint);
            }
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(painting, 0, 0, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    protected void clear(){
        if(holder != null) {
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            paintingCanvas.drawColor(Color.WHITE);
            //canvas.drawBitmap(painting, 0, 0, null);
            holder.unlockCanvasAndPost(canvas);
            canvas.setBitmap(painting);
        }
    }
}
