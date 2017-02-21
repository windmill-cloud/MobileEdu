/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwang.phototouch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureGridFragment extends PictureFragment {


    public PictureGridFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_picture_grid, container, false);
        mView = rootView.findViewById(R.id.picture_grid);

        GridView mListView = (GridView) mView;
        mAdapter = new PictureGridAdapter(getContext());
        mListView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void updateViews() {
        mAdapter.notifyDataSetChanged();
        GridView mListView = (GridView) mView;
        mListView.invalidateViews();
    }
}
