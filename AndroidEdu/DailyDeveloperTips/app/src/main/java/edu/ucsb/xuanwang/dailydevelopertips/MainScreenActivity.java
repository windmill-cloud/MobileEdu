/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.xuanwang.dailydevelopertips;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.ucsb.xuanwang.dailydevelopertips.fragments.TipCardsSelectFragment;
import edu.ucsb.xuanwang.dailydevelopertips.inmemojects.Cards;


public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        final TipCardsSelectFragment fragment = new TipCardsSelectFragment();

        Cards.inflateDummies();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }
}
