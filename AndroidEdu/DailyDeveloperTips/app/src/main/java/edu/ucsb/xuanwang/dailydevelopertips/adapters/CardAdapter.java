/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.xuanwang.dailydevelopertips.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.ucsb.xuanwang.dailydevelopertips.R;
import edu.ucsb.xuanwang.dailydevelopertips.inmemojects.Cards;

/**
 * Created by xuanwang on 2/18/17.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private Context mContext;

    List<Cards.Card> cards = null;

    public CardAdapter(Context context, List<Cards.Card> cards){

        mContext = context;
        this.cards = cards;
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.topic_card, viewGroup, false);
        CardViewHolder pvh = new CardViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder albumViewHolder, int i) {

        albumViewHolder.icon.setImageDrawable(getDrawable(cards.get(i).drawableID));
        albumViewHolder.icon.setBackgroundResource(cards.get(i).color.mBody);
        albumViewHolder.title.setText(cards.get(i).title);
        albumViewHolder.title.setBackgroundResource(cards.get(i).color.mBody);
        albumViewHolder.level.setText(cards.get(i).level);
        albumViewHolder.level.setBackgroundResource(cards.get(i).color.mBody);

        albumViewHolder.settings.setBackgroundResource(cards.get(i).color.mBody);

        albumViewHolder.button.setBackgroundResource(cards.get(i).color.mButton);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView icon;
        TextView title;
        TextView level;
        Button button;
        ImageButton settings;

        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            icon = (ImageView) itemView.findViewById(R.id.cc);
            title = (TextView) itemView.findViewById(R.id.card_title);
            level = (TextView) itemView.findViewById(R.id.card_description);
            button = (Button) itemView.findViewById(R.id.card_button);
            settings = (ImageButton) itemView.findViewById(R.id.card_settings);
        }
    }

    public Context getContext() {
        return mContext;
    }

    protected Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }
}
