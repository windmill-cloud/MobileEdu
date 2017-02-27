/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation.editentry;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import edu.ucsb.cs.cs185.foliostation.ItemCards;
import edu.ucsb.cs.cs185.foliostation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCoverFragment extends Fragment {
    RecyclerView mRecyclerView;
    SelectCoverAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ItemCards.Card mItemCard;
    ImageView mCoverImage;

    public SelectCoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_edit_tab, container, false);

        //getCardImages();
        EditTabsActivity activity = (EditTabsActivity) getActivity();
        activity.getSupportActionBar().setTitle("Select a Cover Picture");

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cards_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        SelectCoverAdapter.setContext(getContext());
        mItemCard = ItemCards.getInstance(getContext()).cards.get(activity.cardIndex);
        mAdapter = new SelectCoverAdapter(mItemCard);
        mAdapter.setHasStableIds(true);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager.setItemPrefetchEnabled(true);

        mRecyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCoverImage = (ImageView) rootView.findViewById(R.id.cover_image);

        if(mItemCard.getImages().get(0).isFromPath()) {
            Picasso.with(getContext())
                    .load(new File(mItemCard.getImages().get(mItemCard.coverIndex).mUrl))
                    .resize(1500, 1500)
                    .centerCrop()
                    .noFade()
                    .into(mCoverImage);
        } else {
            Picasso.with(getContext())
                    .load(mItemCard.getImages().get(mItemCard.coverIndex).mUrl)
                    .resize(1500, 1500)
                    .centerCrop()
                    .noFade()
                    .into(mCoverImage);
        }

        mAdapter.setOnItemClickListener(new SelectCoverAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                if(mItemCard.getImages().get(position).isFromPath()) {
                    Picasso.with(getContext())
                            .load(new File(mItemCard.getImages().get(position).mUrl)).noFade()
                            .resize(1500, 1500)
                            .centerCrop()
                            .into(mCoverImage);
                } else {
                    Picasso.with(getContext())
                            .load(mItemCard.getImages().get(position).mUrl).noFade()
                            .resize(1500, 1500)
                            .centerCrop()
                            .into(mCoverImage);
                }

                mItemCard.setCoverIndex(position);
                mAdapter.notifyDataSetChanged();

            }
        });

        ItemCards itemCards = ItemCards.getInstance(getContext());
        itemCards.setAdapter(mAdapter);

        if(itemCards.cards.size() == 0){
            itemCards.inflateDummyContent();
        }

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

}
