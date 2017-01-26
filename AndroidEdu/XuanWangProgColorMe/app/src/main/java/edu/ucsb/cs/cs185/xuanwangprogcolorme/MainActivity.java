package edu.ucsb.cs.cs185.xuanwangprogcolorme;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getApplicationContext();
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);

        //the layout on which we are working
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(0, 0, 0, 0);

        // generate buttons
        final Button btnRed =
                buttonFactory(this, Color.RED, R.string.red);
        final Button btnGreen =
                buttonFactory(this, Color.GREEN, R.string.green);
        final Button btnBlue =
                buttonFactory(this, Color.BLUE, R.string.blue);

        // add buttons to the layout
        layout.addView(btnRed);
        layout.addView(btnGreen);
        layout.addView(btnBlue);

        // set the layout as content view
        setContentView(layout);
    }

    protected final Button buttonFactory(Context context, final int colorId, int titleId) {
        final Button btn = new Button(context);
        btn.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1f
                )
        );

        // set toast message
        StringBuilder sb = new StringBuilder();
        switch (colorId){
            case Color.RED: sb.append(getResources().getString(R.string.toast_red)); break;
            case Color.GREEN: sb.append(getResources().getString(R.string.toast_green)); break;
            case Color.BLUE: sb.append(getResources().getString(R.string.toast_blue)); break;
        }
        final String toastMessage = sb.toString();

        // set properties of the button
        btn.setBackgroundColor(Color.WHITE);
        btn.setText(titleId);

        // set its color in onClickListener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) btn.getBackground();
                int curColorId = buttonColor.getColor();

                if(curColorId != colorId){
                    btn.setBackgroundColor(colorId);
                } else{
                    btn.setBackgroundColor(Color.WHITE);
                }

                toast.cancel();
                Context context = getApplicationContext();
                toast = Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return btn;
    }
}
