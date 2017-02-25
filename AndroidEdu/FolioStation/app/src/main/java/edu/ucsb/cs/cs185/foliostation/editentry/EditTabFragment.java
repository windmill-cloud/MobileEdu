/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation.editentry;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import edu.ucsb.cs.cs185.foliostation.CardsFragment;
import edu.ucsb.cs.cs185.foliostation.DetailBlurDialog;
import edu.ucsb.cs.cs185.foliostation.GridCardAdapter;
import edu.ucsb.cs.cs185.foliostation.ItemCards;
import edu.ucsb.cs.cs185.foliostation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTabFragment extends Fragment {
    RecyclerView mRecyclerView;
    EditTabAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    public EditTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_edit_tab, container, false);

        //getCardImages();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cards_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        EditTabAdapter.setContext(getContext());
        EditTabsActivity activity = (EditTabsActivity) getActivity();
        mAdapter = new EditTabAdapter(ItemCards.getInstance(getContext()).cards.get(activity.cardIndex).getImages());
        mAdapter.setHasStableIds(true);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager.setItemPrefetchEnabled(true);

        mRecyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter.setOnItemClickListener(new EditTabAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                /*
                Bundle arguments = new Bundle();
                arguments.putInt("POSITION", position);
                DetailBlurDialog fragment = new DetailBlurDialog();

                fragment.setArguments(arguments);
                FragmentManager ft = getActivity().getSupportFragmentManager();

                fragment.show(ft, "dialog");

                Bitmap map=takeScreenShot(getActivity());
                Bitmap fast= CardsFragment.BlurBuilder.blur(getContext(), map);
                final Drawable draw=new BitmapDrawable(getResources(),fast);

                ImageView background = (ImageView) getActivity().findViewById(R.id.activity_background);
                background.bringToFront();
                background.setScaleType(ImageView.ScaleType.FIT_XY);
                background.setImageDrawable(draw);
                */
            }
        });

        /*
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(mRecyclerView);*/
        ItemCards itemCards = ItemCards.getInstance(getContext());
        itemCards.setAdapter(mAdapter);

        if(itemCards.cards.size() == 0){
            itemCards.inflateDummyContent();
        }

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

}
