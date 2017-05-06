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
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.Card;
import com.facebook.litho.widget.Text;
import com.facebook.litho.widget.VerticalGravity;

public class NewTownActivity extends AppCompatActivity {
  String title = "nonono";
  String description = "whawa";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_new_town);
    //Toolbar toolbar = new Toolbar(this);
    //(Toolbar) findViewById(R.id.toolbar);
    //setSupportActionBar(toolbar);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    final ComponentContext context = new ComponentContext(this);

    Component<NewTown> newTownComponent = NewTown.create(context)
        .cardTitle(title)
        .cardDescription(description)
        .build();

    final LithoView lithoView = LithoView.create(
        this,
        newTownComponent);

    final ComponentTree componentTree = ComponentTree.create(context, newTownComponent).build();
    lithoView.setComponentTree(componentTree);
    setContentView(lithoView);
  }
}
