package edu.ucsb.cs.cs185.xuanwangxmlcolorme;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    Toast toast = null;

    // map a button to its color id
    Map<Button, Integer> colorMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);

        // set behaviours of buttons
        buttonFactory(R.id.red_button,
                getResources().getString(R.string.toast_red), Color.RED);
        buttonFactory(R.id.green_button,
                getResources().getString(R.string.toast_green), Color.GREEN);
        buttonFactory(R.id.blue_button,
                getResources().getString(R.string.toast_blue), Color.BLUE);

    }

    protected void buttonFactory(int btnId, final CharSequence toastText, final int colorId) {
        final Button btn = (Button) findViewById(btnId);

        // set its color in onClickListener
        btn.setOnClickListener(new View.OnClickListener() {
            private void updateColor(){
                if(colorMap.containsKey(btn) && colorMap.get(btn) == colorId){
                    btn.setBackgroundColor(Color.WHITE);
                    colorMap.put(btn, Color.WHITE);
                } else {
                    btn.setBackgroundColor(colorId);
                    colorMap.put(btn, colorId);
                }
            }

            @Override
            public void onClick(View v) {
                // update button color
                updateColor();

                // make a toast
                toast.cancel();
                Context context = getApplicationContext();
                toast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
