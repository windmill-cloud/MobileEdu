package edu.ucsb.ece150.demos.activitiesdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        // Getting the intent passed from MainActivity
        final Intent intent = getIntent();

        // Getting the data from the intent
        final String email =  intent.getStringExtra("EMAIL");

        // Using the data from the intent
        final TextView emailText = (TextView) findViewById(R.id.email);
        emailText.setText(email);

        final TextView screenSizeText = (TextView) findViewById(R.id.screen_size_text);
        // Getting the application's context
        displayScreenSizeInTextView(getApplicationContext(), screenSizeText);

        // Setting the ImageView with a built-in image
        final ImageView imageView = (ImageView) findViewById(R.id.my_image);
        imageView.setImageResource(R.drawable.dog_image);

        // Setting the toast
        final Button toastButton = (Button) findViewById(R.id.make_toast);
        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AnotherActivity.this, "Cheers!", Toast.LENGTH_SHORT).show();
            }
        });
        /*
        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnotherActivity.this, "Cheers!", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    public void displayScreenSizeInTextView(Context context, TextView textView){
        // Getting the screen height and width in pixels
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Height: ").append(displayMetrics.heightPixels).append(", ");
        stringBuilder.append("Width: ").append(displayMetrics.widthPixels);

        textView.setText(stringBuilder.toString());
    }
}
