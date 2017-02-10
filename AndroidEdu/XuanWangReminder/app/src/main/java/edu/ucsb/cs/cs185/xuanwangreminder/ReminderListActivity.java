/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwangreminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ReminderListActivity extends AppCompatActivity {
    private static boolean mTwoPane;

    public static boolean isTwoPane(){
        return mTwoPane;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        //Html.fromHtml("<font color='#ff0000'>ActionBarTitle </font>")
        //toolbar.setTitle(Html.fromHtml("<font color='#000000'>ActionBarTitle </font>"));

        if (savedInstanceState != null) {
           final EditDialogFragment edf = (EditDialogFragment) getSupportFragmentManager().
                    findFragmentByTag("adding_reminder");

            if (edf != null){
                edf.setId(EditDialogFragment.ADD);
                edf.setReminderListener(new EditDialogFragment.ReminderListener() {
                    @Override
                    public void setReminder(ReminderContent.Reminder reminder) {
                        ReminderContent.addItem(reminder);
                        edf.dismiss();
                    }
                });
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show dialog and add reminder
                final EditDialogFragment editDialogFragment = new EditDialogFragment();
                Log.i("d", "first");
                editDialogFragment.setId(EditDialogFragment.ADD);
                Log.i("d", "second");

                editDialogFragment.setReminderListener(new EditDialogFragment.ReminderListener() {
                    @Override
                    public void setReminder(ReminderContent.Reminder reminder) {
                        ReminderContent.addItem(reminder);
                        editDialogFragment.dismiss();
                    }
                });
                FragmentManager ft = getSupportFragmentManager();
                editDialogFragment.show(ft, "adding_reminder");
            }
        });

        View recyclerView = findViewById(R.id.reminder_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.reminder_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> adapter = new SimpleItemRecyclerViewAdapter(ReminderContent.ITEMS);
        recyclerView.setAdapter(adapter);
        ReminderContent.adapter = adapter;
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<ReminderContent.Reminder> mValues;

        public SimpleItemRecyclerViewAdapter(List<ReminderContent.Reminder> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.reminder_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(">");

            //holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).title);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ReminderDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        final ReminderDetailFragment fragment = new ReminderDetailFragment();
                        fragment.setOnCloseListener(new ReminderDetailFragment.OnCloseListener() {
                            @Override
                            public void OnClose() {
                                getSupportFragmentManager().beginTransaction()
                                        .remove(fragment)
                                        .commit();
                            }
                        });
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.reminder_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ReminderDetailActivity.class);
                        intent.putExtra(ReminderDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public ReminderContent.Reminder mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}