/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuanwang on 2/19/17.
 */

public class ItemCards {
    public static ItemCards mInstance;
    public List<Card> cards = new ArrayList<>();
    private static Context mContext = null;
    private static RecyclerView.Adapter<CardViewHolder> mAdapter;

    public void setAdapter(RecyclerView.Adapter<CardViewHolder> adapter) {
        mAdapter = adapter;
        mAdapter.notifyDataSetChanged();
    }

    private ItemCards(Context context) {
        mContext = context;
    }

    public static synchronized ItemCards getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ItemCards(context);
        }
        return mInstance;
    }

    public class CardImage {
        public String mUrl = "";
        public Drawable mDrawable = null;

        CardImage(String url){
            mUrl = url;
            if(mContext != null) {
                mDrawable = mContext.getResources().getDrawable(R.drawable.placeholder);
            }
            ImageRequest imageRequest = new ImageRequest(mUrl,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            Drawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);
                            mDrawable = drawable;
                            Log.i("got", "got");
                            if(mAdapter != null){
                                Log.i("note", "note");

                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 0, 0, ImageView.ScaleType.CENTER, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Log.e("error", error.toString());
                            //mImageView.setImageResource(R.drawable.image_load_error);
                        }
                    });
            // Access the RequestQueue through your singleton class.
            SingletonRequestQueue.getInstance(mContext).getRequestQueue().add(imageRequest);

        }

        public CardImage(Drawable drawable){
            mDrawable = drawable;
        }

        public CardImage(Bitmap bitmap){
            mDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
        }
    }

    public class Card{
        List<CardImage> mImages = new ArrayList<>();
        Drawable mDrawable = null;
        String mTitle;
        String mDescription;

        public Card(String url, String title, String description){
            mImages.add(new CardImage(url));
            mTitle = title;
            mDescription =  description;
        }

        public Card(Drawable drawable, String title, String description){
            mDrawable = drawable;
            mTitle = title;
            mDescription =  description;
        }
    }

    public void inflateDummyContent(){
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/413e3mR4cSL.jpg",
                "騎士団長殺し 第1部 顕れるイデア編", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/51raGEQSo2L.jpg",
                "色彩を持たない多崎つくると、彼の巡礼の年", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/41jAK3VHZ2L.jpg",
                "海辺のカフカ", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/51pdnZBq-aL.jpg",
                "1Q84 BOOK1〈4月‐6月〉", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/41oYBNer4pL.jpg",
                "職業としての小説家", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/41PGlYT6DgL._SX332_BO1,204,203,200_.jpg",
                "ねじまき鳥クロニクル", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/51FYrDp2WEL._SX346_BO1,204,203,200_.jpg",
                "恋しくて - TEN SELECTED LOVE STORIES", "村上 春樹  (編集)"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/51cNUdZY69L._SX341_BO1,204,203,200_.jpg",
                "女のいない男たち", "村上 春樹"));
    }
}
