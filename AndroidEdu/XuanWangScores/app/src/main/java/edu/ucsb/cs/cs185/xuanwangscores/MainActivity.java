/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwangscores;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements TeamScoreFragment.OnFragmentInteractionListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // set the bounds for the date picker
        this.setDatePickerBounds();

        // If we're being restored from a previous state,
        // we only need to rebind listeners to the button and relink adapters
        // and preserve date entered and states of the fragments.
        if (savedInstanceState != null) {
            this.setButtonListener();
            this.linkAdapters();
            return;
        }

        this.setDefaultDate();
        this.setFragments();
        this.setButtonListener();
        this.linkAdapters();
    }

    protected void setDatePickerBounds() {
        DatePicker mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        String startDate = "Aug 16 2014";
        String endDate = "May 25 2015";
        DateFormat df = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);

        try {
            Date mStartDate = df.parse(startDate);
            Date mEndDate = df.parse(endDate);
            mDatePicker.setMinDate(mStartDate.getTime());
            mDatePicker.setMaxDate(mEndDate.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Linking fragments with team names for AutoCompleteTextView
     **/
    protected void linkAdapters() {
        String[] teamNames = getResources().getStringArray(R.array.Teams);

        TeamScoreFragment teamScoreFragment1 =
                (TeamScoreFragment)
                        getSupportFragmentManager().findFragmentByTag(getTag(R.string.team1));
        if (teamScoreFragment1 != null) {
            teamScoreFragment1.setContext(this, teamNames);
        }

        TeamScoreFragment teamScoreFragment2 =
                (TeamScoreFragment)
                        getSupportFragmentManager().findFragmentByTag(getTag(R.string.team2));
        if (teamScoreFragment2 != null) {
            teamScoreFragment2.setContext(this, teamNames);
        }
    }

    protected void setDefaultDate() {
        DatePicker mDataPicker = (DatePicker) findViewById(R.id.datePicker);
        mDataPicker.updateDate(2014, 7, 16);
    }

    protected void setButtonListener(){
        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    protected String getTag(int id){
        return getResources().getString(id);
    }

    protected void setFragments() {
        if (findViewById(R.id.fragment_container) != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction ft = fragmentManager.beginTransaction();
            // Create a new Fragment to be placed in the activity layout
            TeamScoreFragment mFirstTeamFragment =
                    TeamScoreFragment.newInstance(getTag(R.string.team1));

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            mFirstTeamFragment.setArguments(getIntent().getExtras());

            ft.add(R.id.fragment_container, mFirstTeamFragment, getTag(R.string.team1));

            // Create a new Fragment to be placed in the activity layout
            TeamScoreFragment mSecondTeamFragment =
                    TeamScoreFragment.newInstance(getTag(R.string.team2));

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            mSecondTeamFragment.setArguments(getIntent().getExtras());
            ft.add(R.id.fragment_container, mSecondTeamFragment, getTag(R.string.team2));

            ft.commit();
            fragmentManager.executePendingTransactions();

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View view) {
        // Getting the database handler
        MatchesDBHelper dbOperator = DatabaseOperatorSingleton.getInstance(this).getDBOperator();

        // sqlsb: a new sql statement

        // insert into matches (Id, Date, HomeTeam, HomeScore, AwayTeam, AwayScore) values
        // ('05ad776b-7f5c-4f21-9c85-0626080b7888',
        // 'Sat Aug 16 18:06:10 PDT 2014',
        // 'Arsenal', 1,
        // 'Manchester United', 2)
        StringBuilder sqlsb = new StringBuilder();
        sqlsb.append("insert into ");
        sqlsb.append(dbOperator.getTableName());
        sqlsb.append(" (Id, Date, HomeTeam, HomeScore, AwayTeam, AwayScore) values ('");

        // Generating UUID for a match
        String uniqueId = UUID.randomUUID().toString();
        sqlsb.append(uniqueId).append("', '");

        // Add date
        sqlsb.append(getDate()).append("', ");

        // Setting default date
        setDefaultDate();

        // shouldWriteDB: whether we should insert this entry into db
        // if the user missed any input, we should not insert the entry.
        boolean shouldWriteDB = true;

        shouldWriteDB &= this.resetFragmentAndAppendSql(sqlsb, getTag(R.string.team1));
        sqlsb.append(", ");
        shouldWriteDB &= this.resetFragmentAndAppendSql(sqlsb, getTag(R.string.team2));
        sqlsb.append(")");

        // if we should insert to db, do the transaction.
        if(shouldWriteDB) {
            SQLiteDatabase db = dbOperator.getWritableDatabase();
            db.beginTransaction();
            db.execSQL(sqlsb.toString());
            db.endTransaction();
        }
    }

    protected boolean resetFragmentAndAppendSql(StringBuilder sqlsb, String tag){
        boolean shouldWrite = true;

        // getting the fragment handler
        TeamScoreFragment teamScoreFragment =
                (TeamScoreFragment)
                        getSupportFragmentManager().findFragmentByTag(tag);
        sqlsb.append("'");

        String teamName = teamScoreFragment.getTeam();
        if(teamName.equals("")){
            shouldWrite = false;
        }
        sqlsb.append(teamName).append("', ");

        String teamScore = teamScoreFragment.getScore();
        if(teamScore.equals("")){
            shouldWrite = false;
        }
        sqlsb.append(teamScore);

        // resetting Text and Score in the Fragment
        teamScoreFragment.changeTextProperties(tag);
        return shouldWrite;
    }

    protected String getDate(){
        DatePicker mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth();
        int year =  mDatePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime().toString();
    }
}
