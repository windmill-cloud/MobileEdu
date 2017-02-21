package edu.ucsb.cs.cs185.xuanwang.phototouch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 9876;
    private static final String TAG_FRAGMENT = "ImageFragment";

    private enum FragmentType {
        LIST, GRID
    }

    private FragmentType fragmentType;

    private ImageFragment mImageFragment;
// identifier for my intent results (result request code)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("clicked", "camera");
                // open activity to pick an image from device storage (e.g. Android Gallery)
                Intent imgPickingIntent = new Intent();
                imgPickingIntent.setType("image/*");
                imgPickingIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(imgPickingIntent, PICK_IMAGE_REQUEST);
            }
        });

        buildListFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void buildListFragment(){
        ImageListFragment fragment = new ImageListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, TAG_FRAGMENT)
                .commit();

        fragmentType = FragmentType.LIST;
        mImageFragment = fragment;
    }

    protected void destroyFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();

    }

    protected void buildGridFragment(){
        ImageGridFragment fragment = new ImageGridFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, "ImageFragment")
                .commit();

        fragmentType = FragmentType.GRID;
        mImageFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.i("ACTION", "You have clicked the clear setting");

            ImageManager.clear();
            mImageFragment.updateViews();
            Log.i("ACTION", String.valueOf(ImageManager.getCount()));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case PICK_IMAGE_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    try {
                        Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ImageManager.insert(getApplicationContext(), image);

                        mImageFragment.updateViews();
                    } catch (IOException e){
                        Log.e("Exception", e.toString());
                    }

                }
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list) {
            // Handle the camera action
            if(fragmentType != FragmentType.LIST) {
                destroyFragment();
                buildListFragment();
            }
        } else if (id == R.id.nav_grid) {
            if(fragmentType != FragmentType.GRID) {
                destroyFragment();
                buildGridFragment();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
