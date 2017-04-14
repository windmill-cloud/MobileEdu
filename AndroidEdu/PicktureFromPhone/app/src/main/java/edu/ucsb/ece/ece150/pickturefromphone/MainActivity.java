package edu.ucsb.ece.ece150.pickturefromphone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/*
 * Whatever you do, remember: whatever gets the job done is a good first solution.
 * Then start to redo it, keeping the job done, but the solutions more and more elegant.
 *
 * Don't satisfy yourself with the first thing that comes out.
 * Dig through the documentation, read your error logs.
 */

public class MainActivity extends AppCompatActivity {
    static final String PREFS_NAME = "MyPrefsFile";
    static final int REQUEST_IMAGE_PICK = 1234;

    // The default image uri
    Uri imageUri = Uri.parse("android.resource://edu.ucsb.ece.ece150.pickturefromphone/drawable/kafka");

    // Reference to the ImageView
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: read saved image information from shared references
        // if there is saved uri, use the saved uri
        // else use the default one
        SharedPreferences userDetails = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Uri savedUri = Uri.parse(userDetails.getString("imageUri", ""));
        if (!savedUri.toString().equals("")){
            imageUri = savedUri;
        }

        profileImage = (ImageView) findViewById(R.id.profile_image);
        profileImage.setImageURI(imageUri);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: the click event should lead us to the gallery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent, ""), REQUEST_IMAGE_PICK);
            }
        });
    }

    // depending on how you are going to pass information back and forth, you might need this
    // uncommented and filled out:

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: Getting the result from image picking activity
        // check if requestCode is the same as PICK_IMAGE_REQUEST
        // check if resultCode is RESULT_OK
        // if both conditions satisfied, get uri from data, set profileImage
        if(requestCode == REQUEST_IMAGE_PICK){
            if(resultCode == RESULT_OK){
                imageUri = data.getData();
                profileImage.setImageURI(imageUri);
            }
        }
    }

    // You'll need these methods to remember the chosen image
    // Study activity life cycle
    // https://developer.android.com/guide/components/activities/activity-lifecycle.html
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // TODO: save image uri to savedInstanceState
        savedInstanceState.putString("URI_save", imageUri.toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imageUri = Uri.parse(savedInstanceState.getString("URI_save"));
        // TODO: get saved uri information and set profileImage
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO: save image uri to shared references
        // https://developer.android.com/training/basics/data-storage/shared-preferences.html
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putString("imageUri", imageUri.toString());

        editor.apply();
    }
}