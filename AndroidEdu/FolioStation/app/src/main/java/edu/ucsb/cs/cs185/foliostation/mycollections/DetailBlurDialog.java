/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation.mycollections;


import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;

import edu.ucsb.cs.cs185.foliostation.ItemCards;
import edu.ucsb.cs.cs185.foliostation.R;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogEngine;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailBlurDialog extends DialogFragment {

    public static void sendViewToBack(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (parent != null) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    public DetailBlurDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        ImageView background = (ImageView) getActivity().findViewById(R.id.activity_background);
        background.setImageResource(android.R.color.transparent);
        sendViewToBack(background);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_blur_dialog, container, false);
        int idx = getArguments().getInt("CARD_INDEX");

        ImageView imageView = (ImageView) rootView.findViewById(R.id.dialog_photo);
        TextView title = (TextView) rootView.findViewById(R.id.dialog_title);
        TextView description = (TextView) rootView.findViewById(R.id.dialog_description);

        String from = getArguments().getString("FROM");

        if(from != null ){
            if(from.equals("GRID")){
                // TODO: refactor picture loading
                ItemCards.Card card = ItemCards.getInstance(getContext()).cards.get(idx);

                if(card.getCoverImage().isFromPath()) {
                    Picasso.with(getContext())
                            .load(new File(card.getCoverImage().mUrl))
                            .resize(300, 450)
                            .centerCrop()
                            .into(imageView);
                } else {
                    Picasso.with(getContext())
                            .load(card.getCoverImage().mUrl)
                            .resize(300, 450)
                            .centerCrop()
                            .into(imageView);
                }

                //imageView.setImageDrawable(card.mImages.get(0).mDrawable);

                title.setText(card.getTitle());

                description.setText(card.getDescription());

            } else if(from.equals("DETAILS")){
                // TODO: refactor picture loading
                ItemCards.Card card = ItemCards.getInstance(getContext()).cards.get(idx);
                int imageIndex = getArguments().getInt("IMAGE_INDEX");

                if(card.getImages().get(imageIndex).isFromPath()) {
                    Picasso.with(getContext())
                            .load(new File(card.getImages().get(imageIndex).mUrl))
                            .resize(300, 450)
                            .centerCrop()
                            .into(imageView);
                } else {
                    Picasso.with(getContext())
                            .load(card.getImages().get(imageIndex).mUrl)
                            .resize(300, 450)
                            .centerCrop()
                            .into(imageView);
                }

                title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                description.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));

            }
        }

        return rootView;
    }

}
