package edu.ucsb.ece.ece150.pickture;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/*
 * Whatever you do, remember: whatever gets the job done is a good first solution.
 * Then start to redo it, keeping the job done, but the solutions more and more elegant.
 *
 * Be sure you understand what you're doing, and if you don't, ask me:
 *  - mail: gavrilov.miroslav@gmail.com
 *  - twitter: @chief_gavrilov
 *
 * Don't satisfy yourself with the first thing that comes out.
 * Dig through the documentation, read your error logs.
 */
public class MainActivity extends AppCompatActivity {
    private ImageView profileImage;
    private String filename = "position";
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        final ImageView profileImage = (ImageView) this.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: the click event should lead us to the gallery here:
            }
        });
*/
        this.readFile();

        if(getIntent().hasExtra("id")) {
            Intent i = getIntent();
            position = i.getExtras().getInt("id");
        }
        this.showImage();

        this.writeFile();


        final Button button = (Button) findViewById(R.id.button_pick);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, Gallery.class);
                //myIntent.putExtra("")
                startActivity(myIntent);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.writeFile();

        // TODO: this part may need some coding
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.readFile();
        this.showImage();
        // TODO: this part may need some coding
    }

    // depending on how you are going to pass information back and forth, you might need this
    // uncommented and filled out:
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: I bring news from the nether!
    }
    */

    protected void showImage() {
        ImageAdapter imageAdapter = new ImageAdapter(this);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        profileImage.setImageResource(imageAdapter.mThumbIds[position]);
    }

    protected void writeFile(){
        String string = Integer.toString(position);
        Log.i("position value", string);
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void readFile(){
        FileInputStream inputStream;

        try{
            inputStream = openFileInput(filename);
            byte[] buffer = new byte[(int) inputStream.getChannel().size()];
            inputStream.read(buffer);
            String str= "";
            for(byte b:buffer) str+=(char)b;
            inputStream.close();
            if (str != null || str.length() != 0){
                position = Integer.parseInt(str);
                Log.i("READ POS INT", Integer.toString(position));
            }
            Log.i("READ FROM FILE", String.format("GOT: [%s]", str));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
