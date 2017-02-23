/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuanwang on 2/19/17.
 */

public class ItemCards {
    public static List<Card> cards = new ArrayList<>();

    public static class Card{
        String mURL = "";
        Bitmap mBitmap = null;
        String mTitle;
        String mDescription;

        public Card(String url, String title, String description){
            mURL = url;
            mTitle = title;
            mDescription =  description;
        }

        public Card(Bitmap bitmap, String title, String description){
            mBitmap = bitmap;
            mTitle = title;
            mDescription =  description;
        }
    }

    public static void inflateDummyContent(){
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/413e3mR4cSL._SX346_BO1,204,203,200_.jpg",
                "騎士団長殺し 第1部 顕れるイデア編", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/51raGEQSo2L._SX344_BO1,204,203,200_.jpg",
                "色彩を持たない多崎つくると、彼の巡礼の年", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/41jAK3VHZ2L._SX350_BO1,204,203,200_.jpg",
                "海辺のカフカ", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/51pdnZBq-aL._SX349_BO1,204,203,200_.jpg",
                "1Q84 BOOK1〈4月‐6月〉", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/41oYBNer4pL._SX349_BO1,204,203,200_.jpg",
                "職業としての小説家", "村上 春樹"));
        cards.add(new Card("https://images-na.ssl-images-amazon.com/images/I/61cb7KhrKKL.jpg", "ねじまき鳥クロニクル",
                "村上 春樹"));
    }
}
