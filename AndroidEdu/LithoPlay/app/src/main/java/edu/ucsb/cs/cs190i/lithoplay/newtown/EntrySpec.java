/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.lithoplay.newtown;

import android.content.Intent;
import android.view.View;

import com.facebook.litho.ClickEvent;
import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.FromEvent;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateInitialState;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.OnUpdateState;
import com.facebook.litho.annotations.Param;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.State;
import com.facebook.litho.widget.Card;
import com.facebook.litho.widget.Text;

import edu.ucsb.cs.cs190i.lithoplay.FragActivity;

/**
 * Created by xuanwang on 5/5/17.
 */

@LayoutSpec
public class EntrySpec {

  @OnCreateInitialState
  static void createInitialState(
      ComponentContext c,
      StateValue<String> title,
      @Prop String initTitle) {

    title.set(initTitle);
  }

  @OnCreateLayout
  static ComponentLayout onCreateLayout(
      ComponentContext context,
      @State String title,
      @Prop String description) {

    return  Column.create(context)
        .child(
            Text.create(context)
                .text(title)
                .textSizeSp(22)
        )
        .child(
            Text.create(context)
                .text(description)
                .textSizeSp(12)
        )
        .clickHandler(Entry.onClick(context))
        .build();
  }

  @OnEvent(ClickEvent.class)
  static void onClick(
      ComponentContext c,
      @FromEvent View view ) {

    Entry.updateEntryState(c, "new title");
  }

  @OnUpdateState
  static void updateEntryState(StateValue<String> title,
                               @Param String newTitle) {
    title.set(newTitle);
  }
}
