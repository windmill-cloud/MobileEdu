/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.lithoplay;


import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;

import static com.facebook.yoga.YogaEdge.ALL;

/**
 * Created by xuanwang on 4/18/17.
 */

@LayoutSpec
public class ListItemSpec {

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
            ComponentContext c,
            @Prop int color,
            @Prop String title,
            @Prop String subtitle) {

        return Column.create(c)
                .paddingDip(ALL, 16)
                .backgroundColor(color)
                .child(
                        Text.create(c)
                                .text(title)
                                .textSizeSp(40))
                .child(
                        Text.create(c)
                                .text(subtitle)
                                .textSizeSp(20))
                .build();
    }
}
