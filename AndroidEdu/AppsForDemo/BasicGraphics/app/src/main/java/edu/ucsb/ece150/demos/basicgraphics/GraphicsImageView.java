package edu.ucsb.ece150.demos.basicgraphics;

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
            radius = Math.min(x, y) / 4;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);
            paint.setColor(Color.BLUE);
            canvas.drawCircle(x / 2, y / 2, radius, paint);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(20);
            canvas.drawLine(x / 8, y / 8, x / 8, y * 7 /8, paint);
        } else {
            canvas.drawColor(Color.WHITE);
        }
    }

    public void draw(){
        drawSomething = true;
        this.invalidate();
    }

    public void clear(){
        drawSomething = false;
        this.invalidate();
    }
}
