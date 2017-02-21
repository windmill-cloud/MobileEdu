/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwang.phototouch;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by xuanwang on 2/20/17.
 */

public abstract class ImageFragment extends Fragment {

    protected ImageAdapter mAdapter;
    protected View mView;

    public abstract void updateViews();
}
