/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.xuanwang.dailydevelopertips.inmemojects;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsb.xuanwang.dailydevelopertips.R;

/**
 * Created by xuanwang on 2/18/17.
 */

public class Cards {
    public static List<Card> cards = new ArrayList<>();
    public static Map<String, Integer> drawableMap = new HashMap<String, Integer>()
    {{
        put("Android", R.drawable.android);
        put("ReactNative", R.drawable.react);
        put("Objective-C", R.drawable.objectivec);
        put("Flutter", R.drawable.flutter);

    }};

    public static Map<String, CardColor> colorMap = new HashMap<String, CardColor>()
    {{
        put("Android", new CardColor(R.color.card_red, R.color.button_red));
        put("ReactNative", new CardColor(R.color.card_green, R.color.button_green));
        put("Objective-C", new CardColor(R.color.card_blue, R.color.button_blue));
        put("Flutter", new CardColor(R.color.card_rust, R.color.button_rust));

    }};


    public static class CardColor{
        public int mBody;
        public int mButton;

        public CardColor(int body, int button){
            mBody = body;
            mButton = button;
        }
    }

    public static class Card{

        public Integer drawableID;
        public CardColor color;
        public String title;
        public String level;

        public Card(String drawableName, String title, String level){
            this.drawableID = drawableMap.get(drawableName);
            this.color = colorMap.get(drawableName);
            this.title = title;
            this.level = level;
        }
    }

    public static void inflateDummies(){
        cards.clear();
        cards.add(new Card("Android", "Android", "Write Android apps in Java"));
        cards.add(new Card("ReactNative", "React Native", "Create a cross-platform app"));
        cards.add(new Card("Objective-C", "Objective-C", "Make an iOS app with Objective-C"));
        cards.add(new Card("Flutter", "Flutter", "Make a cross-platform app with Flutter and Dart"));

    }
}
