/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwangreminder;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

public class ReminderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(ReminderDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ReminderDetailFragment.ARG_ITEM_ID));
            arguments.putBoolean(ReminderDetailFragment.IS_ACTIVITY, true);
            ReminderDetailFragment fragment = new ReminderDetailFragment();
            fragment.setFragmentManager(getSupportFragmentManager());
            fragment.setOnCloseListener(new ReminderDetailFragment.OnCloseListener() {
                @Override
                public void OnClose() {
                    onBackPressed();
                }
            });
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.reminder_detail_container, fragment, "reminder_detail_frag")
                    .commit();
        } else {
            ReminderDetailFragment fragment = (ReminderDetailFragment) getSupportFragmentManager()
                    .findFragmentByTag("reminder_detail_frag");
            /*
            Bundle arguments = new Bundle();
            arguments.putString(ReminderDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ReminderDetailFragment.ARG_ITEM_ID));
            arguments.putBoolean(ReminderDetailFragment.IS_ACTIVITY, true);
            fragment.setFragmentManager(getSupportFragmentManager());
            fragment.setOnCloseListener(new ReminderDetailFragment.OnCloseListener() {
                @Override
                public void OnClose() {
                    onBackPressed();
                }
            });
            fragment.setArguments(arguments);
            */

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                navigateUpTo(new Intent(this, ReminderListActivity.class));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
