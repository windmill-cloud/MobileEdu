/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation.editentry;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import edu.ucsb.cs.cs185.foliostation.CardViewHolder;
import edu.ucsb.cs.cs185.foliostation.GridCardAdapter;
import edu.ucsb.cs.cs185.foliostation.ItemCards;
import edu.ucsb.cs.cs185.foliostation.R;

/**
 * Created by xuanwang on 2/24/17.
 */

public class EditTabAdapter extends RecyclerView.Adapter<CardViewHolder>
    implements View.OnClickListener {

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    List<ItemCards.CardImage> mCardImages;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    static Context mContext = null;

    public EditTabAdapter(List<ItemCards.CardImage> thumbnails) {
        mCardImages = thumbnails;
    }

    static void setContext(Context context) {
        mContext = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_card, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(v);

        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int i) {

        if (mContext == null) {
            Log.e("mContext", "null");
        }

        Picasso.with(mContext)
                .load(new File(mCardImages.get(i).mUrl))
                .resize(1500, 1500)
                .centerCrop()
                .into(holder.imageView);

        //holder.imageView.setImageBitmap(mCardImages.get(i));

        holder.imageView.setTag(i);
        //holder.imageView.setBackgroundResource(R.drawable.placeholder);

        holder.imageView.setOnClickListener(this);

        /*
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
        return mCardImages.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            // get tag
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    public void setOnItemClickListener(EditTabAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}