/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.lithoplay.newtown;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;

/**
 * Created by xuanwang on 5/5/17.
 */

@LayoutSpec
public class NewTownSpec {

  @OnCreateLayout
  static ComponentLayout onCreateLayout(
          ComponentContext context,
          @Prop String cardTitle,
          @Prop String cardDescription) {

    return Column.create(context)
        .child(
            ToolBar
                .create(context)
                .title("Hello")
        )
        .child(
            Entry.create(context)
                //.title(cardTitle)
                .initTitle(cardTitle)
                .description(cardDescription)
                .build()
        )
        .build();
  }
}
