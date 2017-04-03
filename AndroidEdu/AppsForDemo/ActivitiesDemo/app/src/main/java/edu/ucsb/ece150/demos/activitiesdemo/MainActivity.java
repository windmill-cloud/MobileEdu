package edu.ucsb.ece150.demos.activitiesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding a view, get the reference to the view
        final EditText email = (EditText) findViewById(R.id.edit_email);
        final Button seeWhatHappensButton = (Button) findViewById(R.id.button);

        // Setting a button's behavior
        seeWhatHappensButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating an intent, from the MainActivity to AnotherActivity
                Intent intent = new Intent(MainActivity.this, AnotherActivity.class);

                // Putting some data with the intent
                intent.putExtra("EMAIL", email.getText().toString());

                // Invoking the intent, start AnotherActivity
                startActivity(intent);
            }
        });

        final Button weirdButton = (Button) findViewById(R.id.some_weird_button);
        weirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Main", "You just clicked the weird button");
            }
        });
    }
}
