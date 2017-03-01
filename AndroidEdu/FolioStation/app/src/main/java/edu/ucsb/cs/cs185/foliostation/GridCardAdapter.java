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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by xuanwang on 2/19/17.
 */

public class GridCardAdapter extends RecyclerView.Adapter<CardViewHolder>
        implements View.OnClickListener,
        View.OnLongClickListener,
        Toolbar.OnMenuItemClickListener {

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener = null;
    private OnToolbarItemClickListener mOnToolbarItemClickListener = null;

    List<ItemCards.Card> mCards;

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

        // TODO: refactor picture loading
        if(card.getCoverImage().isFromPath()) {
            Picasso.with(mContext)
                    .load(new File(card.getCoverImage().mUrl))
                    .resize(300, 450)
                    .centerCrop()
                    .noFade()
                    .into(holder.imageView);
        } else {
            Picasso.with(mContext)
                    .load(card.getCoverImage().mUrl)
                    .resize(300, 450)
                    .centerCrop()
                    .noFade()
                    .into(holder.imageView);
        }
        //holder.imageView.setImageDrawable(card.mImages.get(0).mDrawable);

        holder.imageView.setTag(i);
        //holder.imageView.setBackgroundResource(R.drawable.placeholder);

        holder.imageView.setOnClickListener(this);
        holder.imageView.setOnLongClickListener(this);

        holder.title.setText(card.mTitle);
        holder.description.setText(card.mDescription);

        holder.toolbar.inflateMenu(R.menu.card_toolbar);

        holder.toolbar.setOnMenuItemClickListener(this);
        //holder.deleteMenuItem.setOnMenuItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    @Override public long getItemId(int position) { return position; }

    /**
    * Handlers for click listeners
     */

    public interface OnToolbarItemClickListener {
        void onToolbarItemClick(MenuItem menuItem);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(mOnToolbarItemClickListener != null){
            mOnToolbarItemClickListener.onToolbarItemClick(menuItem);
        }
        return true;
    }

    public void setOnToolbarItemClickListener(OnToolbarItemClickListener listener) {
        this.mOnToolbarItemClickListener = listener;
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

    /**
     * Item long click
     */


    public interface OnRecyclerViewItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemLongClickListener != null) {
            // get tag
            mOnItemLongClickListener.onItemLongClick(view, (int) view.getTag());
        }
        return true;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }
}
