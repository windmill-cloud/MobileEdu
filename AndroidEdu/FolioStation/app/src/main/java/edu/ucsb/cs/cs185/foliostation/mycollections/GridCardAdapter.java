/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation.mycollections;

import android.content.Context;
import android.content.Intent;
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

import edu.ucsb.cs.cs185.foliostation.ItemCards;
import edu.ucsb.cs.cs185.foliostation.R;
import edu.ucsb.cs.cs185.foliostation.editentry.EditTabsActivity;

/**
 * Created by xuanwang on 2/19/17.
 */

public class GridCardAdapter extends RecyclerView.Adapter<CardViewHolder>
        implements View.OnClickListener {

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_grid, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(v);

        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int i) {

        final int position = i;

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
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                startEditActivity(view, position);
                return true;
            }
        });

        holder.title.setText(card.getTitle());
        holder.description.setText(card.getDescription());

        holder.toolbar.getMenu().clear();

        holder.toolbar.inflateMenu(R.menu.card_toolbar);

        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.card_toolbar_edit:
                        Log.i("selected", "edit");
                        startEditActivity(holder.imageView, position);
                        break;
                    case R.id.card_toolbar_delete:
                        Log.i("selected", "delete");
                        ItemCards.deleteIthCard(position);
                        GridCardAdapter.this.notifyDataSetChanged();
                        break;
                }
                return true;
            }
        });

        if (card.hasMultiPics()){
            holder.hasMultiPics.setVisibility(View.VISIBLE);
        } else {
            holder.hasMultiPics.setVisibility(View.GONE);
        }
    }

    public void startEditActivity(View view, int position){
        Intent intent = new Intent(view.getContext(), EditTabsActivity.class);
        intent.putExtra("CARD_INDEX", position);
        intent.putExtra("EDIT", true);
        view.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    @Override public long getItemId(int position) { return position; }

    /**
    * Handlers for click listeners
     */


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
