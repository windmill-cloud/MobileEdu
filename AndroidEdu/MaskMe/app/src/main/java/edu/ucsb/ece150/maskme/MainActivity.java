package edu.ucsb.ece150.maskme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public enum Mode {
        PREVIEW,
        CAPTURE,
        MASK
    }

    public static final int MAX_FACES = 5;
    //private static final double CONFIDENCE_THRESHOLD = 0.52;

    private MaskCameraSurfaceView mCameraSurface;
    private MaskedImageView mImageView;
    private FrameLayout mCameraFrame;
    private Button mCameraButton;
    private Bitmap mImage;

    private Mode mMode = Mode.PREVIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("start", "onCreate");
        setContentView(R.layout.activity_main);

        setupCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("start", "onStop");
        mCameraSurface.stopCamera();
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("start", "onStart");
        setContentView(R.layout.activity_main);

        setupCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("start", "onResume");
    }

    private void setupCamera() {
        /* your code here:
            - create the new masked camera surface view with application context
            - create the new masked image view with application context
            - set masked image view scale type to FIT_XY
            - get the camera frame from the resource R.id.cameraDisplay
            - get the camera button from R.id.cameraButton
            - set the button on click method to be our cameraButtonOnClick() method
            - add the new masked camera surface and masked image view to camera frame
            - bring the camera surface view to front
        */
        mCameraSurface = new MaskCameraSurfaceView(getApplicationContext());
        mImageView = new MaskedImageView(getApplicationContext());
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mCameraFrame = (FrameLayout) findViewById(R.id.cameraDisplay);
        mCameraButton = (Button) findViewById(R.id.cameraButton);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraButtonOnClick(v);
            }
        });

        mCameraFrame.addView(mCameraSurface);
        mCameraFrame.bringChildToFront(mCameraSurface);


    }

    private Bitmap rotateImage(Bitmap image) {
        final Matrix matrix = new Matrix();

        matrix.postTranslate(0f - image.getWidth()/2, 0f - image.getHeight()/2);
        matrix.postRotate(mCameraSurface.getCurrentRotation());
        matrix.postTranslate(image.getWidth() / 2, image.getHeight() / 2);
        return Bitmap.createBitmap(image,0, 0, image.getWidth(), image.getHeight(), matrix, false);
    }

    private void takePicture() {
        mCameraSurface.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                final Bitmap snapShot = rotateImage(BitmapFactory.decodeByteArray(data, 0, data.length));
                mImage = snapShot.copy(Bitmap.Config.RGB_565, false);
                mImageView.setImageBitmap(mImage);
                mImageView.invalidate();
                mCameraFrame.bringChildToFront(mImageView);
            }
        });
    }

    private void addMask() {
        /* your code here:
            - make a new detector passing it mImage's width and height and the MAX_FACES variable
            - initialize an array of FaceDetector.Face objects the size of MAX_FACES
            - run facial detection with the "find faces" method
            - if there are some faces, call the maskFaces() method of the surface view
            - if not, call noFaces()
            - invalidate the surface view to refresh the drawing
        */
        if(mImage == null){
//            takePicture();
            Log.i("mImage","null");
        }

        FaceDetector faceDetector = new FaceDetector(mImage.getWidth(), mImage.getHeight(), MAX_FACES);
        FaceDetector.Face[] faces = new FaceDetector.Face[MAX_FACES];
        int numOfFaces = faceDetector.findFaces(mImage, faces);


        if (numOfFaces > 0 ){
            mImageView.maskFaces(faces, numOfFaces, mImage.getWidth(), mImage.getHeight());

            if (mImageView != null) {
                ViewGroup parent = (ViewGroup) mImageView.getParent();
                if (parent != null) {
                    parent.removeView(mImageView);
                }
            }

            try {
                mCameraFrame.addView(mImageView);
                mCameraFrame.bringChildToFront(mImageView);
                Log.i("number of faces", Integer.toString(numOfFaces));
            } catch (Exception e) {
                Log.e("add mask", "failed");
            }
        }
        else{
            TextView errorText = new TextView(getApplicationContext());
            errorText.setText("No face detected,\n please try again!");
            errorText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            errorText.setTextSize(30);
            errorText.setTextColor(Color.WHITE);


            mCameraFrame.addView(errorText);
            mCameraFrame.bringChildToFront(errorText);
            mImageView.noFaces();
        }
        mCameraSurface.invalidate();
    }

    private void resetCamera() {
        mCameraFrame.bringChildToFront(mCameraSurface);
        mImageView.reset();
        mCameraSurface.startPreview();
    }


    public void cameraButtonOnClick(View v) {

        switch (mMode) {
            case PREVIEW:
                takePicture();
                mCameraButton.setText(getString(R.string.add_mask));
                mMode = Mode.CAPTURE;
                break;
            case CAPTURE:
                addMask();
                mCameraButton.setText(getString(R.string.show_preview));
                mMode = Mode.MASK;
                break;
            case MASK:
                resetCamera();
                mCameraButton.setText(getString(R.string.take_picture));
                mMode = Mode.PREVIEW;
                break;
            default:
                break;
        }
    }
}
