/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwangreminder;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import static edu.ucsb.cs.cs185.xuanwangreminder.ReminderContent.notifyAdapter;

public class ReminderDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    public static final String IS_ACTIVITY = "activity";
    private ReminderContent.Reminder mItem;
    private boolean isActivity;
    private OnCloseListener listener;
    private FragmentManager fragmentManager;


    public interface OnCloseListener {
        void OnClose();
    }

    public ReminderDetailFragment() {
    }

    public void setOnCloseListener(OnCloseListener listener) {
        this.listener = listener;
    }

    public void setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;

    }

    private void close() {
        if (listener != null) {
            listener.OnClose();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = ReminderContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.title);
            }
        }
        if (getArguments().containsKey(IS_ACTIVITY)) {
            isActivity = getArguments().getBoolean(IS_ACTIVITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.reminder_detail, container, false);
        if (mItem != null) {
            inflateViews(rootView, mItem);
            Button editButton = (Button) rootView.findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    final EditDialogFragment editDialogFragment = new EditDialogFragment();
                    editDialogFragment.setId(mItem.id);
                    editDialogFragment.setReminderListener(new EditDialogFragment.ReminderListener() {
                        @Override
                        public void setReminder(ReminderContent.Reminder reminder) {
                            notifyAdapter();
                            Activity activity = getActivity();
                            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                            if (appBarLayout != null) {
                                appBarLayout.setTitle(reminder.title);
                            }
                            inflateViews(rootView, reminder);
                            editDialogFragment.dismiss();
                        }
                    });
                    editDialogFragment.show(fragmentManager, "tag");
                }
            });

            Button deleteButton = (Button) rootView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReminderContent.removeItem(mItem);
                    getActivity().finish();
                }
            });

        }
        return rootView;
    }

    protected void inflateViews(View contentView, ReminderContent.Reminder mItem){
        TextView titleView = (TextView) contentView.findViewById(R.id.titleView);
        titleView.setText(mItem.title);

        boolean check;

        check = ((mItem.days & ReminderContent.MONDAY) != 0);
        CheckBox mon = (CheckBox) contentView.findViewById(R.id.mon);
        mon.setChecked(check);

        check = ((mItem.days & ReminderContent.TUESDAY) != 0);
        CheckBox tue = (CheckBox) contentView.findViewById(R.id.tue);
        tue.setChecked(check);

        check = ((mItem.days & ReminderContent.WEDNESDAY) != 0);
        CheckBox wed = (CheckBox) contentView.findViewById(R.id.wed);
        wed.setChecked(check);

        check = ((mItem.days & ReminderContent.THURSDAY) != 0);
        CheckBox thu = (CheckBox) contentView.findViewById(R.id.thu);
        thu.setChecked(check);

        check = ((mItem.days & ReminderContent.FRIDAY) != 0);
        CheckBox fri = (CheckBox) contentView.findViewById(R.id.fri);
        fri.setChecked(check);

        check = ((mItem.days & ReminderContent.SATURDAY) != 0);
        CheckBox sat = (CheckBox) contentView.findViewById(R.id.sat);
        sat.setChecked(check);

        check = ((mItem.days & ReminderContent.SUNDAY) != 0);
        CheckBox sun = (CheckBox) contentView.findViewById(R.id.sun);
        sun.setChecked(check);

        TextView timeView = (TextView) contentView.findViewById(R.id.timeView);
        Integer hour = mItem.hour;
        Integer minute = mItem.minute;
        String amPM = hour >= 12 ? "PM" :"AM";
        StringBuilder timeBuilder = new StringBuilder();
        timeBuilder.append(String.format("%02d", hour % 12)).append(':')
                .append(String.format("%02d ", minute)).append(amPM);

        timeView.setText(timeBuilder.toString());

        TextView detailView = (TextView) contentView.findViewById(R.id.detailView);
        detailView.setText(mItem.details);
    }
}
