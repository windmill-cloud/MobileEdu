/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

/**
 * Created by xuanwang on 2/25/17.
 */

public class ImageUtilities {
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

    public static int convertDpToPixel(Context context, int dp){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)px;
    }

    public static int getScreenXYmaxDimension(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        int maxXYScreenSize = Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels);
        return maxXYScreenSize;
    }

    public static int getThumbnailPixelSize(Context context, int dp){
       return convertDpToPixel(context, dp);
    }

    public static Drawable scale(Context context, Drawable image, int size) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, size, size, false);
        return new BitmapDrawable(context.getResources(), bitmapResized);
    }
}
