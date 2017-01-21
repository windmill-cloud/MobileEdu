package edu.ucsb.ece150.maskme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.widget.ImageView;
import android.util.Log;

public class MaskedImageView extends ImageView {
    FaceDetector.Face[] faces = null;
    int imageWidth;
    int imageHeight;
    private Paint paint = new Paint();

    public MaskedImageView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final float scaleX = (float) canvas.getWidth() / (float) imageWidth;
        final float scaleY = (float) canvas.getHeight() / (float) imageHeight;
        final float transX = ((float) canvas.getWidth() - scaleX * imageWidth) / 2f;
        final float transY = ((float) canvas.getHeight() - scaleY * imageHeight) / 2f;

        if (faces != null) {
            final PointF midpoint = new PointF();
            Log.i("Number of face", Integer.toString(faces.length));
            for(int i = 0; i < faces.length; i++) {
                //Log.i("Number of face", Integer.toString(i));
                faces[i].getMidPoint(midpoint);

                final float x = scaleX * midpoint.x + transX;
                final float y = scaleY * midpoint.y + transY;
                final float faceSize = scaleX * faces[i].eyesDistance();

                drawMask(x, y, faceSize, canvas);
            }
        } else {
            Log.d("maskme", "no faces");
        }
    }

    private void drawMask(final float x, final float y, final float faceSize, final Canvas canvas) {
        /* your code here: draw a mask over the image */
        Log.i("x value", Float.toString(x));
        Log.i("y value", Float.toString(y));
        Log.i("face size", Float.toString(faceSize));
        paint.setColor(Color.WHITE);
        paint.setAlpha(150);

        //float scaledFaceSize = faceSize * 2f;

        RectF rectF0 = new RectF((x - faceSize),
                (float)(y - 1.35 * faceSize),
                (x + faceSize),
                (float)(y + 1.65 * faceSize));

        canvas.drawOval(rectF0, paint);

        paint.setColor(Color.BLACK);
        paint.setAlpha(150);

        float rightEye = x - faceSize / 2f;
        float leftEye = x + faceSize / 2f;
        float halfEyeSize = faceSize / 3.5f;

        Log.i("drawMask", "drawing right eye");

        RectF rectF1 = new RectF(rightEye - halfEyeSize,
                (float)(y - 0.5 * halfEyeSize),
                rightEye + halfEyeSize,
                (float)(y + 0.5 * halfEyeSize));
        canvas.drawOval(rectF1, paint);


        Log.i("drawMask", "drawing left eye");


        RectF rectF2 = new RectF(leftEye - halfEyeSize,
                (float)(y - 0.5 * halfEyeSize),
                leftEye + halfEyeSize,
                (float)(y + 0.5 * halfEyeSize));
        canvas.drawOval(rectF2,paint);
        //canvas.drawCircle(x, y, faceSize, paint);

        /*
        mask.setImageBitmap(cvsbitmap);
        if(masked) {
            //remove mask
            preview.removeView(mask);
            masked = false;
        }

        preview.addView(mask);
        masked = true;
*/
    }

    public void maskFaces(FaceDetector.Face[] faces, int count, int width, int height) {
        imageWidth = width;
        imageHeight = height;

        this.faces = new FaceDetector.Face[count];

        System.arraycopy(faces, 0, this.faces, 0, count);
        Log.i("inside", "maskFaces");

    }

    public void noFaces() {

        faces = null;
    }

    public void reset() {
        faces = null;
        setImageBitmap(null);
    }
}
