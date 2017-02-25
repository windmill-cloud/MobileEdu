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

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xuanwang on 2/19/17.
 */

public class GridCardAdapter extends RecyclerView.Adapter<CardViewHolder>
        implements View.OnClickListener{

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    List<ItemCards.Card> mCards;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
    }

    static Context mContext = null;

    public GridCardAdapter(List<ItemCards.Card> cards){
        mCards = cards;
    }

    static void setContext(Context context){
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

        if(mContext == null){
            Log.e("mContext", "null");
        }
        ItemCards.Card card = ItemCards.getInstance(mContext).cards.get(i);
        /*
        Picasso.with(mContext)
                .load(card.mURL)
                .resize(200, 300)
                .centerCrop()
                .into(holder.imageView);
        */
        holder.imageView.setImageDrawable(card.mImages.get(0).mDrawable);

        holder.imageView.setTag(i);
        //holder.imageView.setBackgroundResource(R.drawable.placeholder);

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
        return mCards.size();
    }

    @Override public long getItemId(int position) { return position; }

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
}


