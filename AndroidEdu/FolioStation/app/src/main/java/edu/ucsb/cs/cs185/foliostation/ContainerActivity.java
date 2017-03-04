/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageBaseActivity;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

import edu.ucsb.cs.cs185.foliostation.editentry.EditTabsActivity;
import edu.ucsb.cs.cs185.foliostation.mycollections.CardsFragment;
import edu.ucsb.cs.cs185.foliostation.utilities.PicassoImageLoader;


public class ContainerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment mFragment;
    private int mNavigationID;
    private String TAG_FRAGMENT = "InflatedFragment";
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 7654;
    private static final int IMAGE_PICKER = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null){
            setGridsFragment();
        }
        setImagePicker();

    }

    protected void setImagePicker(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(24);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
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
        getMenuInflater().inflate(R.menu.collect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == mNavigationID){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        mNavigationID = id;

        if (id == R.id.nav_collections) {
            if(mFragment != null) {
                destroyFragments();
            }
            setGridsFragment();

        } else if (id == R.id.nav_shared) {
            if(mFragment != null) {
                destroyFragments();
            }

        } else if (id == R.id.nav_discover) {
            if(mFragment != null) {

                destroyFragments();
            }
            setDiscoverFragment();

        } else if (id == R.id.nav_new) {
            if (ContextCompat.checkSelfPermission(ContainerActivity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(ContainerActivity.this,
                    android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(ContainerActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(ContainerActivity.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    android.Manifest.permission.CAMERA},
                            ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {

                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setShowCamera(true);
                startImagesPicking();
            }

        } else if (id == R.id.nav_settings) {
            if(mFragment != null) {

                destroyFragments();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void startImagesPicking() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);
    }

    protected void setGridsFragment() {
        CardsFragment fragment = new CardsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, TAG_FRAGMENT)
                .commit();

        mFragment = fragment;
        ActionBar toolbar = getSupportActionBar();
        if(toolbar != null){
            toolbar.setTitle("Collections");
        }

        ImageButton addCollection = (ImageButton) findViewById(R.id.add_collection);
        addCollection.setVisibility(View.VISIBLE);
        addCollection.setEnabled(true);
        addCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(ContainerActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(ContainerActivity.this,
                        android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ContainerActivity.this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(ContainerActivity.this,
                                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        android.Manifest.permission.CAMERA},
                                ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {

                    ImagePicker imagePicker = ImagePicker.getInstance();
                    imagePicker.setShowCamera(true);
                    startImagesPicking();
                }
            }
        });

        mNavigationID = R.id.nav_collections;
    }

    protected void destroyFragments(){
        for(Fragment fragment:getSupportFragmentManager().getFragments()){
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        mFragment = null;
    }

    protected void setDiscoverFragment(){

        ImageButton addCollection = (ImageButton) findViewById(R.id.add_collection);
        addCollection.setVisibility(View.GONE);
        addCollection.setEnabled(false);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ItemCards itemCards = ItemCards.getInstance(getApplicationContext());
                itemCards.addNewCardFromImages(images);
                Intent intent = new Intent(getApplicationContext(), EditTabsActivity.class);
                intent.putExtra("CARD_INDEX", 0);

                startActivity(intent);
                //finish();
                /*
                MyAdapter adapter = new MyAdapter(images);
                gridView.setAdapter(adapter);*/

            } else {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
