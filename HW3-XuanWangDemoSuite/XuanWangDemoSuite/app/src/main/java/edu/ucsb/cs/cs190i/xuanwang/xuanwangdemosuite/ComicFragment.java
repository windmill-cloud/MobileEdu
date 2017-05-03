package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.almeros.android.multitouch.MoveGestureDetector;
import com.almeros.android.multitouch.RotateGestureDetector;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicFragment extends SavableFragment implements View.OnTouchListener {

  private static final String MATRIX = "Matrix";
  private static final String COMIC = "Comic";

  String savedUrl = "";

  ImageView mImageView;
  Bitmap mBitMap;
  Matrix mMatrix = new Matrix();
  Button getComicButton;
  EditText editText;

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

    mImageView = (ImageView) rootView.findViewById(R.id.imageView);
    mImageView.setOnTouchListener(this);

    if(savedUrl != null && !savedUrl.equals("")){
      Picasso.with(getActivity()).load(savedUrl).resize(800, 600)
          .centerInside().into(mImageView);
    }

    mImageView.setImageMatrix(mMatrix);

    Drawable d = mImageView.getDrawable();
    if(mBitMap == null && d != null){
      mBitMap = drawableToBitmap(d);
    }
    // Setup Gesture Detectors
    mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    mRotateDetector = new RotateGestureDetector(getContext(), new RotateListener());
    mMoveDetector = new MoveGestureDetector(getContext(), new MoveListener());

    editText = (EditText) rootView.findViewById(R.id.editTextNumber);

    getComicButton = (Button) rootView.findViewById(R.id.buttonGetComic);
    getComicButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        String num = editText.getText().toString();
        if(!num.matches("[0-9]{1,4}")){
          Toast.makeText(
              getActivity(),
              "Wrong input, please enter a number between 1 and 1827!",
              Toast.LENGTH_SHORT).show();
        }

        int i = Integer.parseInt(num);
        if(i == 0){
          i = 1;
        } else {
          i = i % 1828;
        }

        final ReqQueueSingleton queue= ReqQueueSingleton.getInstance(getActivity().getApplicationContext());

        String url ="https://xkcd.com/" + i;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                savedUrl = getImageUrl(response);

                Picasso.with(getActivity()).load(savedUrl).resize(800, 600)
                    .centerInside().into(mImageView);

                //resetFactors();
                mImageView.setImageMatrix(mMatrix);

                Log.d("html", savedUrl);
              }
            }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
        });

        queue.addToRequestQueue(stringRequest);

      }
    });

    return rootView;
  }


  private void resetFactors(){
      mScaleFactor = 1.0f;
      mRotationDegrees = 0.f;
      mFocusX = 0.f;
      mFocusY = 0.f;
  }

  @Override
  public void saveState(Bundle bundle) {

    bundle.putString(COMIC, savedUrl);

    float[] values = new float[9];
    mMatrix.getValues(values);
    bundle.putFloatArray(MATRIX, values);

  }

  @Override
  public void restoreState(Bundle bundle) {
    if(bundle != null) {
      savedUrl = bundle.getString(COMIC);

      float[] values = bundle.getFloatArray(MATRIX);
      mMatrix.setValues(values);

    }
  }

  @Override
  public void onStart() {
    super.onStart();
    if(mImageView != null){
      mImageView.setImageMatrix(mMatrix);
    }
  }

  @Override
  public boolean onTouch(View view, MotionEvent motionEvent) {
    Drawable d = mImageView.getDrawable();

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

  public static String getImageUrl(String html){
    String[] lines = html.split("\\r?\\n");
    for(String line:lines) {
      if(line.contains("for hotlinking/embedding")) {
        String[] tokens =  line.split(":\\s");
        return tokens[1];
      }
    }
    return "";
  }
}
