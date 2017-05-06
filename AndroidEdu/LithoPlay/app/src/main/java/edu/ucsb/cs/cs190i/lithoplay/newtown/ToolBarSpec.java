/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.lithoplay.newtown;

import android.graphics.Color;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;

/**
 * Created by xuanwang on 5/5/17.
 */

@LayoutSpec
public class ToolBarSpec {

  @OnCreateLayout
  static ComponentLayout onCreateLayout(
          ComponentContext context,
          @Prop String title) {

    return Column.create(context)
            .paddingDip(YogaEdge.BOTTOM, 8)
            .backgroundColor(Color.LTGRAY)
            .child(
                    Text.create(context)
                            .text(title)
                            .textSizeSp(22)
            )
            .build();
  }
}
