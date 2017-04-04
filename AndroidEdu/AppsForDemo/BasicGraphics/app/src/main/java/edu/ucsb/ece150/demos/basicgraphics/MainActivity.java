package edu.ucsb.ece150.demos.basicgraphics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GraphicsImageView myImageView = (GraphicsImageView) findViewById(R.id.my_image_view);
        final Button drawButton = (Button) findViewById(R.id.button_draw);
        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myImageView.draw();
            }
        });

        final Button clearButton = (Button) findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myImageView.clear();
            }
        });
    }
}
