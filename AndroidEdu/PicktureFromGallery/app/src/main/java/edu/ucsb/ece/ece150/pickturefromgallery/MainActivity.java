package edu.ucsb.ece.ece150.pickturefromgallery;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private ImageView profileImage;
    private ImageAdapter imageAdapter;
    static final String PREFS_NAME = "MyPrefsFile";
    static final int REQUEST_IMAGE_PICK = 1234;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().hasExtra("id")) {
            Intent i = getIntent();
            position = i.getExtras().getInt("id");
        }

        SharedPreferences userDetails = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        position = userDetails.getInt("SAVED_POSITION", 3);

        profileImage = (ImageView) findViewById(R.id.profile_image);
        imageAdapter = new ImageAdapter(this);
        showImage();
        profileImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, Gallery.class);
                startActivityForResult(myIntent, REQUEST_IMAGE_PICK);
            }
        });
    }

    // depending on how you are going to pass information back and forth, you might need this
    // uncommented and filled out:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: I bring news from the nether!
        if(requestCode == REQUEST_IMAGE_PICK){
            if(resultCode == RESULT_OK){
                position = data.getIntExtra("id", 0);
                showImage();
            }
        }
    }

    protected void showImage() {
        profileImage.setImageResource(imageAdapter.mThumbIds[position]);
    }

    // You'll need these methods to remember the chosen image
    // Study activity life cycle
    // https://developer.android.com/guide/components/activities/activity-lifecycle.html
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // TODO: save image uri to savedInstanceState
        savedInstanceState.putInt("POSITION", position);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("POSITION");
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
        editor.putInt("SAVED_POSITION", position);

        // Commit the edits!
        editor.apply();
    }

}
