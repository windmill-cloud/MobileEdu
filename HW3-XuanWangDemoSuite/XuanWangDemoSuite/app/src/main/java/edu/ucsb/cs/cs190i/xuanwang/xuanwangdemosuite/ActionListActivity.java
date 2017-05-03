package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * An activity representing a list of Actions. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ActionDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ActionListActivity extends AppCompatActivity {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private String fragmentName;
    private SavableFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ListView actionList = (ListView) findViewById(R.id.action_list);
        actionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                displayFragment(item, null);
            }
        });

        if (findViewById(R.id.action_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        fragmentName = savedInstanceState == null ? getIntent().getStringExtra(SavableFragment.NameExtra) : savedInstanceState.getString(SavableFragment.NameExtra);
        Bundle fragmentBundle = savedInstanceState == null ? getIntent().getBundleExtra(SavableFragment.BundleExtra) : savedInstanceState.getBundle(SavableFragment.BundleExtra);
        if (fragmentName != null) {
            displayFragment(fragmentName, fragmentBundle);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SavableFragment.NameExtra, fragmentName);
        if (fragment != null) {
            Bundle fragmentBundle = new Bundle();
            fragment.saveState(fragmentBundle);
            outState.putBundle(SavableFragment.BundleExtra, fragmentBundle);
        }
        super.onSaveInstanceState(outState);
    }

    private void displayFragment(String fragmentName, Bundle fragmentBundle) {
        this.fragmentName = fragmentName;
        if (mTwoPane) {
            fragment = FragmentFactory.createFragment(fragmentName);
            fragment.restoreState(fragmentBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.action_detail_container, fragment).commit();
        } else {
            Intent activityIntent = new Intent(this, ActionDetailActivity.class);
            activityIntent.putExtra(SavableFragment.NameExtra, fragmentName);
            activityIntent.putExtra(SavableFragment.BundleExtra, fragmentBundle);
            startActivity(activityIntent);
        }
    }

    @Override
    public void onBackPressed() {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            fragmentName = null;
            fragment = null;
        } else {
            super.onBackPressed();
        }
    }
}
