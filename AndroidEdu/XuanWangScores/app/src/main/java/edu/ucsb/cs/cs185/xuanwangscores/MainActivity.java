package edu.ucsb.cs.cs185.xuanwangscores;

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
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements TeamScoreFragment.OnFragmentInteractionListener, View.OnClickListener {

    protected void linkAdapters(){
        String[] teamNames = getResources().getStringArray(R.array.Teams);

        TeamScoreFragment teamScoreFragment1 =
                (TeamScoreFragment)
                        getSupportFragmentManager().findFragmentByTag("fragment_one");

        teamScoreFragment1.setContext(this, teamNames);

        TeamScoreFragment teamScoreFragment2 =
                (TeamScoreFragment)
                        getSupportFragmentManager().findFragmentByTag("fragment_two");

        teamScoreFragment2.setContext(this, teamNames);
    }

    protected void setDefaultDate(){
        DatePicker mDataPicker = (DatePicker) findViewById(R.id.datePicker);
        mDataPicker.updateDate(2014, 7, 16);
    }

    protected void setDatePickerBounds(){
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // If we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            Button btn = (Button)findViewById(R.id.button);
            btn.setOnClickListener(this);
            this.linkAdapters();
            this.setDatePickerBounds();
            return;
        }

        this.setDatePickerBounds();
        this.setDefaultDate();

        if (findViewById(R.id.fragment_container) != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction ft = fragmentManager.beginTransaction();
            // Create a new Fragment to be placed in the activity layout
            TeamScoreFragment mFirstTeamFragment = TeamScoreFragment.newInstance("Team 1 Name");

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            mFirstTeamFragment.setArguments(getIntent().getExtras());

            ft.add(R.id.fragment_container, mFirstTeamFragment, "fragment_one");

            // Create a new Fragment to be placed in the activity layout
            TeamScoreFragment mSecondTeamFragment = TeamScoreFragment.newInstance("Team 2 Name");

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            mSecondTeamFragment.setArguments(getIntent().getExtras());
            ft.add(R.id.fragment_container, mSecondTeamFragment, "fragment_two");

            ft.commit();
            fragmentManager.executePendingTransactions();

        }
        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(this);
        this.linkAdapters();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View view) {
        setDefaultDate();
        TeamScoreFragment teamScoreFragment1 =
                (TeamScoreFragment)
                        getSupportFragmentManager().findFragmentByTag("fragment_one");
        teamScoreFragment1.changeTextProperties();
        TeamScoreFragment teamScoreFragment2 =
                (TeamScoreFragment)
                        getSupportFragmentManager().findFragmentByTag("fragment_two");
        teamScoreFragment2.changeTextProperties();
    }
}
