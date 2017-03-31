/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.xuanwang.appforpresentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

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
