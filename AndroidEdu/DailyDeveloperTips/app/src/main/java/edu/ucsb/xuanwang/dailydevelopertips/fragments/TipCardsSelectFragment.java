/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.xuanwang.dailydevelopertips.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ucsb.xuanwang.dailydevelopertips.R;
import edu.ucsb.xuanwang.dailydevelopertips.adapters.CardAdapter;
import edu.ucsb.xuanwang.dailydevelopertips.inmemojects.Cards;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipCardsSelectFragment extends Fragment {

    public TipCardsSelectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_tip_cards_select, container, false);

        if (savedInstanceState == null) {
            RecyclerView rv;

            rv = (RecyclerView) rootView.findViewById(R.id.subscription_cards);
            rv.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv.setLayoutManager(llm);
            CardAdapter adapter = new CardAdapter(Cards.cards);
            rv.setAdapter(adapter);

        }
        return rootView;
    }

}
