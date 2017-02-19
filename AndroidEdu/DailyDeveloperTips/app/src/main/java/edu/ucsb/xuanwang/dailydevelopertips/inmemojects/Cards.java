/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.xuanwang.dailydevelopertips.inmemojects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuanwang on 2/18/17.
 */

public class Cards {
    public static List<Card> cards = new ArrayList<>();

    public static class Card{

        public String title;
        public String level;

        public Card(String title, String level){
            this.title = title;
            this.level = level;
        }
    }

    public static void inflateDummies(){
        cards.clear();
        cards.add(new Card("Android", "beginner"));
        cards.add(new Card("React Native", "Intermediate"));
    }
}
