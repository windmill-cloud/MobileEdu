package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
          case R.id.action_populate:
            MainActivityPermissionsDispatcher.populateDatabaseWithCheck(MainActivity.this);
            break;
          case R.id.action_clear:
            clearDatabase();
            break;
        }
        return true;
      }
    });

    ImageTagDatabaseHelper.initialize(this);

    //final TextView textView = (TextView)findViewById(R.id.textView);
    //final ImageView imageView = (ImageView)findViewById(R.id.imageView);


  }

  @NeedsPermission({
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
  })
  protected void populateDatabase(){
    TaggedImageRetriever.getNumImages(new TaggedImageRetriever.ImageNumResultListener() {
      @Override
      public void onImageNum(int num) {
        //textView.setText(textView.getText() + "\n\n" + num);
        for(int i = 0; i < num; i++){
          TaggedImageRetriever.getTaggedImageByIndex(i, new TaggedImageRetriever.TaggedImageResultListener() {
            @Override
            public void onTaggedImage(TaggedImageRetriever.TaggedImage image) {
              if (image != null) {
                //String fileName = "Test" + String.valueOf(ii) + ".jpg";
                /*

                String root = Environment.getExternalStorageDirectory().getAbsolutePath();
                File myDir = new File(root + "/saved_images");
                myDir.mkdirs();
                String fileName = "ImageTagExplorer-" + UUID.randomUUID().toString() + ".jpg";
                File file = new File (myDir, fileName);
                if (file.exists ()) {
                  file.delete();
                }

*/

                File outDir = getExternalFilesDir(null); // root dir
                String fileName = "ImageTagExplorer-" + UUID.randomUUID().toString() + ".jpg";

                File file = new File(outDir, fileName);
                try {
                  file.createNewFile();

                  FileOutputStream out = new FileOutputStream(file);
                  image.image.compress(Bitmap.CompressFormat.JPEG, 100, out);
                  out.flush();
                  out.close();
                  image.image.recycle();
                  image.uri = Uri.fromFile(file);
                  //MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                  //new File(getExternalFilesDir(null), "ImageTagExplorer-ed1350b7-ebf7-44bd-8923-33bfe90d0ba5.jpg").exists()

                } catch (Exception e) {
                  e.printStackTrace();
                }

                ImageTagDatabaseHelper.getInstance().addTaggedImage(image);

/*
                StringBuilder tagList = new StringBuilder();
                for (String p : image.tags) {
                  tagList.append(p + "\n");
                }*/
                //textView.setText(textView.getText() + "\n\n" + tagList.toString());
              }
            }
          });
        }
      }
    });
  }

  private void clearDatabase(){
    ImageTagDatabaseHelper.getInstance().clearDatabase();
    File picsDir =
        Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES);
    for (File file : picsDir.listFiles()) {
      file.delete();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ImageTagDatabaseHelper.getInstance().close();
  }

  @Override
  protected void onPostResume() {
    super.onPostResume();
    // Show your dialog here (this is called right after onActivityResult)
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @OnPermissionDenied({
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
  })
  void showDeniedForLocationUpdates() {
    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
  }

}