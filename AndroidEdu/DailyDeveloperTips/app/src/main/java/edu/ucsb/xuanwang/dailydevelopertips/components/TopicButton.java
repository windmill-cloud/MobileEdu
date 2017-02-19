/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.xuanwang.dailydevelopertips.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.ucsb.xuanwang.dailydevelopertips.R;

/**
 * Created by xuanwang on 2/18/17.
 */

public class TopicButton  extends LinearLayout {

    private ImageView imgView;
    private TextView titleView;
    private TextView levelView;

    public TopicButton(Context context) {
        super(context,null);
    }

    public TopicButton(Context context,AttributeSet attributeSet) {
        super(context, attributeSet);

        LayoutInflater.from(context).inflate(R.layout.button_topic, this, true);

        this.imgView = (ImageView)findViewById(R.id.topicButtonImage);
        this.titleView = (TextView)findViewById(R.id.topicButtonTitle);
        this.levelView = (TextView)findViewById(R.id.topicButtonLevel);

        this.setClickable(true);
        this.setFocusable(true);

    }

    public void setDimension() {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (150 * scale + 0.5f);
        this.getLayoutParams().height = pixels;

    }

    public void setImgResource(int resourceID) {
        this.imgView.setImageResource(resourceID);
    }

    public void setTitle(String text) {
        this.titleView.setText(text);
    }

    public void setLevel(String text){
        this.levelView.setText(text);
    }

    public void setTextColor(int color) {
        this.titleView.setTextColor(color);
        this.levelView.setTextColor(color);
    }

    public void setTitleTextSize(float size) {
        this.titleView.setTextSize(size);
    }

    public void setLevelTextSize(float size) {
        this.levelView.setTextSize(size);
    }

}

