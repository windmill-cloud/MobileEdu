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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.File;

import edu.ucsb.cs.cs185.foliostation.mycollections.CardViewHolder;
import edu.ucsb.cs.cs185.foliostation.ItemCards;
import edu.ucsb.cs.cs185.foliostation.R;

/**
 * Created by xuanwang on 2/24/17.
 */

public class SelectCoverAdapter extends RecyclerView.Adapter<CardViewHolder>
    implements View.OnClickListener {

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    ItemCards.Card mCard;
    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    static Context mContext = null;

    public SelectCoverAdapter(ItemCards.Card Card) {
        mCard = Card;
    }

    static void setContext(Context context) {
        mContext = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_edit, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(v);

        return cardViewHolder;
    }

    public static void sendViewToBack(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (parent != null) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int i) {

        if (mContext == null) {
            Log.e("mContext", "null");
        }

        ItemCards.CardImage image =  mCard.getImages().get(i);
        if(i == mCard.coverIndex){
            holder.checked.setImageResource(R.mipmap.checkbox_checked);
            sendViewToBack(holder.mask);

            holder.mask.setColorFilter(android.R.color.transparent);
        } else {
            holder.checked.setImageResource(android.R.color.transparent);
            holder.mask.setBackgroundColor(mContext.getResources().getColor(R.color.colorGrayTransparent));
            holder.mask.bringToFront();
        }

        if(image.isFromPath()) {
            Picasso.with(mContext)
                    .load(new File(image.mUrl))
                    .resize(1500, 1500)
                    .centerCrop()
                    .into(holder.imageView);
        } else {
            Picasso.with(mContext)
                    .load(image.mUrl)
                    .resize(1500, 1500)
                    .centerCrop()
                    .into(holder.imageView);
        }

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
        return mCard.getImages().size();
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

    public void setOnItemClickListener(SelectCoverAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}