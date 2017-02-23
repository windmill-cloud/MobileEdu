/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
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

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
    }

    Context mContext = null;

    public GridCardAdapter(Context context){
        mContext = context;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_card, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(v);

        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int i) {

        ItemCards.Card card = ItemCards.cards.get(i);
        holder.imageView.setImageDrawable(card.mDrawable);

        holder.imageView.setTag(i);

        holder.imageView.setOnClickListener(this);
        holder.title.setText(card.mTitle);
        holder.description.setText(card.mDescription);
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
        if (mOnItemClickListener != null) {
            // get tag
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView imageView;
        TextView title;
        TextView description;

        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            imageView = (ImageView) itemView.findViewById(R.id.card_photo);
            title = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_description);
        }
    }
}


