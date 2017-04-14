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
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

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
    public DrawingView(Context context) {
        super(context);
        pointers = new HashMap<>();
        colors = new int[]{
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.BLACK,
                Color.YELLOW
        };
        getHolder().addCallback(this);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        /*
        Canvas canvas = surfaceHolder.lockCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(100, 100, 25, paint);
        surfaceHolder.unlockCanvasAndPost(canvas);
*/
        painting = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        paintingCanvas = new Canvas();
        paintingCanvas.setBitmap(painting);
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
                    pointers.put(currentId, currentPoint);
                }
        }

        return super.onTouchEvent(event);
    }

    private void drawPointers(){
        if(holder != null) {
            for(int id: pointers.keySet()){
                PointF point = pointers.get(id);
                Paint paint = new Paint();
                paint.setColor(colors[id % colors.length]);
                paintingCanvas.drawCircle(point.x, point.y, 100, paint);
            }
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(painting, 0, 0, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }
}
