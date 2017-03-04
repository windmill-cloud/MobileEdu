/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation.collectiondetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import edu.ucsb.cs.cs185.foliostation.ItemCards;
import edu.ucsb.cs.cs185.foliostation.R;
import edu.ucsb.cs.cs185.foliostation.mycollections.CardViewHolder;
import edu.ucsb.cs.cs185.foliostation.mycollections.GridCardAdapter;

/**
 * Created by xuanwang on 3/3/17.
 */

public class CollectionDetailsAdapter extends RecyclerView.Adapter<CardViewHolder>
        implements View.OnClickListener{

    List<ItemCards.CardImage> mImages;

    Context mContext = null;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public CollectionDetailsAdapter(Context context, List<ItemCards.CardImage> images){
        mContext = context;
        mImages = images;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(v);

        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

        if(mContext == null){
            Log.e("mContext", "null");
        }

        // TODO: refactor picture loading
        if(mImages.get(position).isFromPath()) {
            Picasso.with(mContext)
                    .load(new File(mImages.get(position).mUrl))
                    .resize(300, 450)
                    .centerCrop()
                    .noFade()
                    .into(holder.imageView);
        } else {
            Picasso.with(mContext)
                    .load(mImages.get(position).mUrl)
                    .resize(300, 450)
                    .centerCrop()
                    .noFade()
                    .into(holder.imageView);
        }

        holder.imageView.setOnClickListener(this);
        holder.imageView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Item click
     */

    //define Item click interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
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
}
