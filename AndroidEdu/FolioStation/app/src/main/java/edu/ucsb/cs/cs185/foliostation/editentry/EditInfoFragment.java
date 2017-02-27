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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs185.foliostation.ItemCards;
import edu.ucsb.cs.cs185.foliostation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditInfoFragment extends Fragment {
    ImageView mImageView;
    TextView mTitle;
    TextView mDescription;
    TextView mTags;
    ItemCards.Card mItemCard;

    public EditInfoFragment() {
        // Required empty public constructor
    }

    public void publishCard(){
        if (mItemCard != null){
            if(mTitle != null) {
                mItemCard.setTitle(mTitle.getText().toString());
            }
            if(mDescription != null){
                mItemCard.setDescription(mDescription.getText().toString());
            }
            if(mTags != null){
                String tagsString = mTags.getText().toString();
                String[] tags = tagsString.replaceAll("^[,\\s]+", "").split("[\\s]*,[\\s]+");
                List<String> res = new ArrayList<>();
                for(String str: tags){
                    if(!str.equals("")){
                        res.add(str);
                    }
                }
                mItemCard.setTags(res);
            }
        }
    }

    public void inflateViewsFromData() {
        if (mItemCard != null){
            if(mTitle != null && !mItemCard.getTitle().equals("")) {
                mTitle.setText(mItemCard.getTitle());
            }
            if(mDescription != null && !mItemCard.getDescription().equals("")){
                mDescription.setText(mItemCard.getDescription());
            }
            if(mTags != null && mItemCard.getTags().size() != 0){
                StringBuilder sb = new StringBuilder();
                for (String tag: mItemCard.getTags()){
                    sb.append(tag).append(", ");
                }
                sb.setLength(sb.length()-2);
                mTags.setText(sb.toString());
            }
        }
    }

    public void setUpdatedCoverImage(){
        if(mItemCard != null && mImageView != null){
            // TODO: refactor picture loading
            if(mItemCard.getCoverImage().isFromPath()) {
                Picasso.with(getContext())
                        .load(new File(mItemCard.getCoverImage().mUrl))
                        .resize(240, 240)
                        .centerCrop()
                        .noFade()
                        .into(mImageView);
            } else {
                Picasso.with(getContext())
                        .load(mItemCard.getCoverImage().mUrl)
                        .resize(240, 240)
                        .centerCrop()
                        .noFade()
                        .into(mImageView);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_info, container, false);
        mTitle = (TextView) rootView.findViewById(R.id.edit_title);
        mDescription = (TextView) rootView.findViewById(R.id.edit_description);
        mTags = (TextView) rootView.findViewById(R.id.edit_tags);

        mImageView = (ImageView) rootView.findViewById(R.id.edit_info_cover_image);
        EditTabsActivity activity = (EditTabsActivity) getActivity();
        mItemCard = ItemCards.getInstance(getContext()).cards.get(activity.cardIndex);

        inflateViewsFromData();

        setUpdatedCoverImage();

        return rootView;
    }

}
