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
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuanwang on 2/20/17.
 */

public class ImageManager {
    public static List<Bitmap> images = new ArrayList<>();

    public static void insert(Context context, Bitmap picture) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float maxDimension = Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels);


        images.add(scale(picture, maxDimension, true));
    }

    public static Bitmap scale(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                 maxImageSize / realImage.getWidth(),
                 maxImageSize / realImage.getHeight());
        int width = Math.round( ratio * realImage.getWidth());
        int height = Math.round( ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

}
