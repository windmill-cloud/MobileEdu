package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.almeros.android.multitouch.MoveGestureDetector;
import com.almeros.android.multitouch.RotateGestureDetector;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComicFragment extends SavableFragment implements View.OnTouchListener {

  ImageView mImageView;
  Bitmap mBitMap;
  Matrix mMatrix;

  private float mScaleFactor = 1.0f;
  private float mRotationDegrees = 0.f;
  private float mFocusX = 0.f;
  private float mFocusY = 0.f;

  private ScaleGestureDetector mScaleDetector;
  private RotateGestureDetector mRotateDetector;
  private MoveGestureDetector mMoveDetector;

  public ComicFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_comic, container, false);
    mMatrix = new Matrix();

    mImageView = (ImageView) rootView.findViewById(R.id.imageView);
    mImageView.setOnTouchListener(this);
    // Setup Gesture Detectors
    mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    mRotateDetector = new RotateGestureDetector(getContext(), new RotateListener());
    mMoveDetector = new MoveGestureDetector(getContext(), new MoveListener());
    return rootView;
  }

  @Override
  public void saveState(Bundle bundle) {

  }

  @Override
  public void restoreState(Bundle bundle) {

  }

  @Override
  public boolean onTouch(View view, MotionEvent motionEvent) {
    Drawable d = mImageView.getDrawable();
    if(mBitMap == null){
      mBitMap = drawableToBitmap(d);
    }

    mScaleDetector.onTouchEvent(motionEvent);
    mRotateDetector.onTouchEvent(motionEvent);
    mMoveDetector.onTouchEvent(motionEvent);

    mImageView.setScaleType(ImageView.ScaleType.MATRIX);

    RectF imageRectF = new RectF(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
    RectF viewRectF = new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight());
    mMatrix.setRectToRect(imageRectF, viewRectF, Matrix.ScaleToFit.CENTER);
    mMatrix.preScale(mScaleFactor, mScaleFactor, mBitMap.getWidth()/2, mBitMap.getHeight()/2);

    mMatrix.preRotate(mRotationDegrees, mBitMap.getWidth() / 2, mBitMap.getHeight() / 2);
    mMatrix.postTranslate(mFocusX, mFocusY);

    mImageView.setImageMatrix(mMatrix);

    return true;
  }

  private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
      mScaleFactor *= detector.getScaleFactor(); // average distance between fingers
      return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
      return super.onScaleBegin(detector);
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
      super.onScaleEnd(detector);
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

  public static Bitmap drawableToBitmap (Drawable drawable) {
    Bitmap bitmap = null;

    if (drawable instanceof BitmapDrawable) {
      BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
      if(bitmapDrawable.getBitmap() != null) {
        return bitmapDrawable.getBitmap();
      }
    }

    if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
      bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
    } else {
      bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);
    return bitmap;
  }

}
