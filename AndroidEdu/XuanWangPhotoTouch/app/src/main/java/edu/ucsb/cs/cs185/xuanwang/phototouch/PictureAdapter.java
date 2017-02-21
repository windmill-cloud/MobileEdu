/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwang.phototouch;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by xuanwang on 2/20/17.
 */

public class PictureAdapter extends BaseAdapter{

    private Context mContext;

    public PictureAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return Pictures.pictures.size();
    }

    @Override
    public Object getItem(int i) {
        return Pictures.pictures.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(360, 360));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2,2,2,2);
        } else {
            imageView = (ImageView) view;
        }

        imageView.setImageBitmap(Pictures.pictures.get(i));
        return imageView;
    }
}
