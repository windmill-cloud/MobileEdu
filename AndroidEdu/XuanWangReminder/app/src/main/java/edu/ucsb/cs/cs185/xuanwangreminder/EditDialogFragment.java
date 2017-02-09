/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwangreminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.sql.Time;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditDialogFragment extends DialogFragment {

    private ReminderListener listener;
    private String id;
    public static String ADD = "ADD";

    public void setReminderListener(ReminderListener listener){
        this.listener = listener;
    }

    public EditDialogFragment() {
        // Required empty public constructor
    }

    public void setId(String type){
        this.id = type;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View contentView = getActivity().getLayoutInflater().
                inflate(R.layout.fragment_edit_dialog, null);

        final Button addButton = (Button) contentView.findViewById(R.id.addButton);

        if(!id.equals(ADD)){
            ReminderContent.Reminder reminder = ReminderContent.getItem(id);
            inflateViews(contentView, reminder);
            addButton.setText("Submit Changes");
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderContent.Reminder reminder = ReminderContent.getItem(id);
                if(reminder == null){
                    reminder = buildReminder(contentView, null);
                } else {
                    reminder = buildReminder(contentView, reminder);
                }

                listener.setReminder(reminder);
            }
        });

        builder.setView(contentView);
        return builder.create();
    }

    public ReminderContent.Reminder buildReminder(View contentView, ReminderContent.Reminder reminder){
        if(reminder == null){
            reminder = new ReminderContent.Reminder(
                    "", 0, 0, 0, ""
            );
        }
        TextView titleView = (TextView) contentView.findViewById(R.id.editTitle);
        reminder.title = titleView.getText().toString();

        int days = 0;
        CheckBox mon = (CheckBox) contentView.findViewById(R.id.mon);
        days |= mon.isChecked() ? ReminderContent.MONDAY : 0;

        CheckBox tue = (CheckBox) contentView.findViewById(R.id.tue);
        days |= tue.isChecked() ? ReminderContent.TUESDAY : 0;

        CheckBox wed = (CheckBox) contentView.findViewById(R.id.wed);
        days |= wed.isChecked() ? ReminderContent.WEDNESDAY : 0;

        CheckBox thu = (CheckBox) contentView.findViewById(R.id.thu);
        days |= thu.isChecked() ? ReminderContent.THURSDAY : 0;

        CheckBox fri = (CheckBox) contentView.findViewById(R.id.fri);
        days |= fri.isChecked() ? ReminderContent.FRIDAY : 0;

        CheckBox sat = (CheckBox) contentView.findViewById(R.id.sat);
        days |= sat.isChecked() ? ReminderContent.SATURDAY : 0;

        CheckBox sun = (CheckBox) contentView.findViewById(R.id.sun);
        days |= sun.isChecked() ? ReminderContent.SUNDAY : 0;

        reminder.days = days;

        TimePicker timePicker = (TimePicker) contentView.findViewById(R.id.timePicker);
        Integer hour = timePicker.getCurrentHour();
        Integer minute = timePicker.getCurrentMinute();
        reminder.hour = hour;
        reminder.minute = minute;

        TextView detailView = (TextView) contentView.findViewById(R.id.editDetails);
        reminder.details = detailView.getText().toString();

        return reminder;
    }

    protected void inflateViews(View contentView, ReminderContent.Reminder mItem){
        TextView titleView = (TextView) contentView.findViewById(R.id.editTitle);
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

        TimePicker timeView = (TimePicker) contentView.findViewById(R.id.timePicker);
        Integer hour = mItem.hour;
        Integer minute = mItem.minute;
        timeView.setCurrentHour(hour);
        timeView.setCurrentMinute(minute);

        TextView detailView = (TextView) contentView.findViewById(R.id.editDetails);
        detailView.setText(mItem.details);
    }

    public interface ReminderListener {
        void setReminder(ReminderContent.Reminder reminder);
    }

}
