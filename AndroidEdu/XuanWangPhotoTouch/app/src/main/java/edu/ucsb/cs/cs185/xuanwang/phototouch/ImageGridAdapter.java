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
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by xuanwang on 2/20/17.
 */

public class ImageGridAdapter extends ImageAdapter {

    ImageGridAdapter(Context c) {
        super(c);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {  // if it's not recycled, initialize some attributes
            imageView = new SquareImageView(mContext);
            imageView.setLayoutParams(
                    new GridView.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    )
            );
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(2,2,2,2);
        } else {
            imageView = (ImageView) view;
        }

        imageView.setImageBitmap(ImageManager.images.get(i));
        return imageView;
    }
}
