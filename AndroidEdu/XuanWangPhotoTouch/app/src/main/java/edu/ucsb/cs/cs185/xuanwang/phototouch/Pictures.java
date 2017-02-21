/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwang.phototouch;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuanwang on 2/20/17.
 */

public class Pictures {
    public static List<Bitmap> pictures = new ArrayList<>();

    public static void insert(Bitmap picture) {
        pictures.add(picture);
    }
}
