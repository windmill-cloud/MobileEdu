/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation.mycollections;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.ucsb.cs.cs185.foliostation.R;

/**
 * Created by xuanwang on 2/24/17.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {
    public CardView cv;
    public ImageView imageView;
    public TextView title;
    public TextView description;
    public ImageView checked;
    public ImageView mask;
    public ImageView hasMultiPics;
    public Toolbar toolbar;
    public MenuItem editMenuItem;
    public MenuItem deleteMenuItem;


    public CardViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cv);
        imageView = (ImageView) itemView.findViewById(R.id.card_photo);
        title = (TextView) itemView.findViewById(R.id.card_title);
        description = (TextView) itemView.findViewById(R.id.card_description);
        checked = (ImageView) itemView.findViewById(R.id.cover_checked);
        mask = (ImageView) itemView.findViewById(R.id.card_mask);
        toolbar = (Toolbar) itemView.findViewById(R.id.card_toolbar);
        editMenuItem = (MenuItem) itemView.findViewById(R.id.card_toolbar_edit);
        deleteMenuItem = (MenuItem) itemView.findViewById(R.id.card_toolbar_delete);
        hasMultiPics = (ImageView) itemView.findViewById(R.id.card_is_multiimages);
    }
}