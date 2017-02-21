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
import android.widget.ListView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PictureListFragment extends Fragment {

    private PictureAdapter mAdapter;
    private ListView mListView;

    public PictureListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_picture_list, container, false);
        mListView = (ListView) rootView.findViewById(R.id.picture_list);
        mAdapter = new PictureAdapter(getContext());
        mListView.setAdapter(mAdapter);
        return rootView;
    }

    public void updateViews(){
        mAdapter.notifyDataSetChanged();
        mListView.invalidateViews();
    }
}
