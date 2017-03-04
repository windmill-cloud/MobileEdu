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

import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.ucsb.cs.cs185.foliostation.mycollections.CardViewHolder;
import edu.ucsb.cs.cs185.foliostation.utilities.ImageUtilities;

/**
 * Created by xuanwang on 2/19/17.
 */

public class ItemCards {
    public static ItemCards mInstance;
    public LinkedList<Card> cards = new LinkedList<>();
    private static Context mContext = null;
    private static RecyclerView.Adapter<CardViewHolder> mAdapter;
    private static int mScreenSize;
    private static int mThumbnailSize;
    final private static int URL = 0;
    final private static int PATH = 1;

    public void setAdapter(RecyclerView.Adapter<CardViewHolder> adapter) {
        mAdapter = adapter;
        //mAdapter.notifyDataSetChanged();
    }

    private ItemCards(Context context) {
        mContext = context;

        mScreenSize = ImageUtilities.getScreenXYmaxDimension(context);
        mThumbnailSize = ImageUtilities.getThumbnailPixelSize(context, 120);
        int i = 0;
    }

    public static synchronized ItemCards getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ItemCards(context);
        }
        return mInstance;
    }

    public static void deleteIthCard(int i){
        if (mInstance != null){
            mInstance.cards.remove(i);
        }
    }

    public class CardImage {
        public String mUrl = "";
        public int mType = PATH;
        public Drawable mDrawable = null;
        public Drawable mThumbnail = null;

        CardImage(String url, int type){
            mUrl = url;
            mType = type;

        }

        public boolean isFromPath(){
            return mType == PATH;
        }

        public boolean isFromUrl(){
            return mType == URL;
        }

        public CardImage() {
        }

        public CardImage(Drawable drawable){
            mDrawable = drawable;
            mThumbnail = ImageUtilities.scale(mContext, drawable, mThumbnailSize);
        }

        public CardImage(Drawable drawable, Drawable thumbnail){
            mDrawable = drawable;
            mThumbnail = thumbnail;
        }

        public CardImage(Bitmap bitmap, Bitmap thumbnail){
            mDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
            mThumbnail = new BitmapDrawable(mContext.getResources(), thumbnail);
        }

        public CardImage(Bitmap bitmap){
            mDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
            mThumbnail = new BitmapDrawable(mContext.getResources(),
                    ImageUtilities.scale(bitmap, mThumbnailSize, false));
        }
    }

    public class Card{
        List<CardImage> mImages = new ArrayList<>();
        public List<Bitmap> mThumbnails = new ArrayList<>();

        public List<String> getTags() {
            return tags;
        }

        public String getTagsString() {
            StringBuilder sb = new StringBuilder();
            for(String tag: tags){
                sb.append(tag).append(", ");
            }
            if(sb.length() >= 2 ){
                sb.setLength(sb.length()-2);
            }
            return sb.toString();
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        List<String> tags = new ArrayList<>();
        public int coverIndex = 0;
        String mTitle = "";
        String mDescription = "";

        public Card(String url, int type,  String title, String description){
            mImages.add(new CardImage(url, type));
            mTitle = title;
            mDescription =  description;
        }

        public Card(){

        }

        public CardImage getCoverImage(){
            int i = 0;
            return mImages.get(coverIndex);
        }

        public void setCoverIndex(int index){
            int i = 0;
            coverIndex = index;

        }

        public boolean hasMaxNumOfImages(){
            return mImages.size() >= 24;
        }

        public void addImages(List<ImageItem> images){
            for(ImageItem imageItem: images) {
                mImages.add(new CardImage(imageItem.path, PATH));
            }
        }

        public boolean hasMultiPics(){
            return mImages.size() > 1;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String mDescription) {
            this.mDescription = mDescription;
        }

        public List<CardImage> getImages() {
            return mImages;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String mTitle) {
            this.mTitle = mTitle;
        }
    }

    public void inflateDummyContent(){
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/413e3mR4cSL.jpg", URL,
                "騎士団長殺し 第1部 顕れるイデア編", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/51raGEQSo2L.jpg", URL,
                "色彩を持たない多崎つくると、彼の巡礼の年", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/41jAK3VHZ2L.jpg", URL,
                "海辺のカフカ", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/51pdnZBq-aL.jpg", URL,
                "1Q84 BOOK1〈4月‐6月〉", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/41oYBNer4pL.jpg", URL,
                "職業としての小説家", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/41PGlYT6DgL._SX332_BO1,204,203,200_.jpg", URL,
                "ねじまき鳥クロニクル", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/51FYrDp2WEL._SX346_BO1,204,203,200_.jpg", URL,
                "恋しくて - TEN SELECTED LOVE STORIES", "村上 春樹  (編集)"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/51cNUdZY69L._SX341_BO1,204,203,200_.jpg", URL,
                "女のいない男たち", "村上 春樹"));
    }

    public void addNewCardFromImages(List<ImageItem> imageItemList){

        Card newCard = new Card();

        for(ImageItem imageItem: imageItemList){
            newCard.mImages.add(new CardImage(imageItem.path, PATH));
        }
        cards.addFirst(newCard);
    }
}
