/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.xuanwang.dailydevelopertips.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;

import edu.ucsb.xuanwang.dailydevelopertips.R;
import edu.ucsb.xuanwang.dailydevelopertips.inmemojects.Cards;

/**
 * Created by xuanwang on 2/18/17.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    List<Cards.Card> cards = null;

    public CardAdapter(List<Cards.Card> cards){
        this.cards = cards;
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_tip_card, viewGroup, false);
        CardViewHolder pvh = new CardViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder albumViewHolder, int i) {
        albumViewHolder.title.setText(cards.get(i).title);
        albumViewHolder.level.setText(cards.get(i).level);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView level;

        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);

            title = (TextView) itemView.findViewById(R.id.card_title);
            level = (TextView) itemView.findViewById(R.id.card_description);
        }
    }
}
