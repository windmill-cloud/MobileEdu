/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwangprogtapcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int counter = 0;
    private TextView mTapCounterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            counter = savedInstanceState.getInt("COUNT", 0);
        }

        //the layout on which we are working
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(0, 0, 0, 0);

        mTapCounterText = new TextView(this);
        mTapCounterText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0f);
        mTapCounterText.setLayoutParams(layoutParams);
        mTapCounterText.setGravity(Gravity.CENTER_HORIZONTAL);
        updateText();
        layout.addView(mTapCounterText);

        Button tapButton = new Button(this);
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0f);
        buttonLayoutParams.gravity = Gravity.CENTER;
        tapButton.setLayoutParams(buttonLayoutParams);
        tapButton.setText(R.string.button_text);
        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                updateText();
            }
        });
        layout.addView(tapButton);

        setContentView(layout);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("COUNT", counter);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter = savedInstanceState.getInt("COUNT");
    }

    protected void updateText(){
        mTapCounterText.setText(
                getString(R.string.tap_counter, counter, counter > 1 ? "s" :  "")
        );
    }
}
