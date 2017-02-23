/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

/**
 * Created by xuanwang on 2/19/17.
 */

public class GridCardAdapter extends RecyclerView.Adapter<GridCardAdapter.CardViewHolder>
        implements View.OnClickListener{


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_card, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(v);

        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int i) {
        int width = 200, height = 200;
        Uri imageUri = Uri.parse(ItemCards.cards.get(i).mURL);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(holder.simpleDraweeView.getController())
                .setImageRequest(request)
                .build();

        holder.simpleDraweeView.setController(controller);
        holder.title.setText(ItemCards.cards.get(i).mTitle);
        holder.description.setText(ItemCards.cards.get(i).mDescription);
        /*
        Button buyBtn = holder.buyAlbum;
        buyBtn.setText(String.format("See %s on ALLMUSIC", albums.get(i).albumName));
        holder.buyAlbum.setOnClickListener(this);
        buyBtn.setTag(albums.get(i).amazonUrl);
        */
    }

    @Override
    public int getItemCount() {
        return ItemCards.cards.size();
    }

    @Override
    public void onClick(View view) {

    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        SimpleDraweeView simpleDraweeView;
        TextView title;
        TextView description;

        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.card_photo);
            title = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_description);
        }
    }
}


