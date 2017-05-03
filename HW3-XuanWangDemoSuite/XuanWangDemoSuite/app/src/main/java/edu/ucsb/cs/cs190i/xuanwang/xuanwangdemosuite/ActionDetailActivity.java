package edu.ucsb.cs.cs190i.xuanwang.xuanwangdemosuite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * An activity representing a single Action detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ActionListActivity}.
 */
public class ActionDetailActivity extends AppCompatActivity {
    private String fragmentName;
    private SavableFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            fragmentName = getIntent().getStringExtra(SavableFragment.NameExtra);
            actionBar.setTitle(fragmentName);
            Bundle fragmentBundle = getIntent().getBundleExtra(SavableFragment.BundleExtra);
            fragment = FragmentFactory.createFragment(fragmentName);
            fragment.restoreState(fragmentBundle);
            getSupportFragmentManager().beginTransaction().add(R.id.action_detail_container, fragment).commit();
        } else {
            String fragmentName = savedInstanceState.getString(SavableFragment.NameExtra);
            Bundle fragmentBundle = savedInstanceState.getBundle(SavableFragment.BundleExtra);
            returnToListActivity(fragmentName, fragmentBundle);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SavableFragment.NameExtra, fragmentName);
        Bundle fragmentBundle = new Bundle();
        fragment.saveState(fragmentBundle);
        outState.putBundle(SavableFragment.BundleExtra, fragmentBundle);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            returnToListActivity(null, null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnToListActivity(String fragmentName, Bundle fragmentBundle) {
        Intent returnIntent = new Intent(this, ActionListActivity.class);
        returnIntent.putExtra(SavableFragment.NameExtra, fragmentName);
        returnIntent.putExtra(SavableFragment.BundleExtra, fragmentBundle);
        navigateUpTo(returnIntent);
    }
}