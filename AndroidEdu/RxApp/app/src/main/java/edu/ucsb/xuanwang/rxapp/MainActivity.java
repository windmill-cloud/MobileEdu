/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.xuanwang.rxapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        btn.setBackgroundColor(Color.WHITE);

        RxView.clicks(btn)
                .subscribe(click -> toggleColor(btn));
    }

    protected void toggleColor(Button btn){
        if(btn.getText().equals("Button")){
            btn.setText("Clicked");
        }else {
            btn.setText("Button");
        }
    }
}
