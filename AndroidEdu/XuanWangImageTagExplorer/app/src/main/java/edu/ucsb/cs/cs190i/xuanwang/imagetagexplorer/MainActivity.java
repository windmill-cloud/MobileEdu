package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
  String mCurrentPhotoPath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ImageView iv = (ImageView) findViewById(R.id.test_image);

    Picasso.with(this).load("file:///storage/emulated/0/Android/data/edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer/files/ImageTagExplorer-b3227278-b733-4826-b069-e6d3e511a937.jpg").into(iv);

    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
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
  }


  @NeedsPermission({
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
  })
  protected void populateDatabase() {
    TaggedImageRetriever.getNumImages(new TaggedImageRetriever.ImageNumResultListener() {
      @Override
      public void onImageNum(int num) {
        //textView.setText(textView.getText() + "\n\n" + num);
        for (int i = 0; i < num; i++) {
          TaggedImageRetriever.getTaggedImageByIndex(i, new TaggedImageRetriever.TaggedImageResultListener() {
            @Override
            public void onTaggedImage(TaggedImageRetriever.TaggedImage image) {
              if (image != null) {

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

  private void clearDatabase() {
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

  // ============================= Take photo & Pick from gallery ==================================

  @OnClick(R.id.fab_camera)
  public void takePhoto(View view) {
    dispatchTakePictureIntent();
  }

  @OnClick(R.id.fab_gallery)
  public void selectPhoto(View view) {
    dispatchPickImageIntent();
  }

  static final int REQUEST_IMAGE_PICK = 2568;

  @NeedsPermission({
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
  })
  protected void dispatchPickImageIntent() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setAction(Intent.ACTION_GET_CONTENT);
    intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

    startActivityForResult(Intent.createChooser(intent,
        "Select Picture"), REQUEST_IMAGE_PICK);
  }

  static final int REQUEST_TAKE_PHOTO = 1234;

  private void dispatchTakePictureIntent() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    // Ensure that there's a camera activity to handle the intent
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
      // Create the File where the photo should go
      File photoFile = null;
      try {
        photoFile = createImageFile();
      } catch (IOException ex) {
        // Error occurred while creating the File
        ex.printStackTrace();
      }
      // Continue only if the File was successfully created
      if (photoFile != null) {
        Uri photoURI = FileProvider.getUriForFile(this,
            "edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer.fileprovider",
            photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
      }
    }
  }

  private File createImageFile() throws IOException {
    // Create an image file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";
    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    File image = File.createTempFile(
        imageFileName,  /* prefix */
        ".jpg",         /* suffix */
        storageDir      /* directory */
    );

    // Save a file: path for use with ACTION_VIEW intents
    mCurrentPhotoPath = image.getAbsolutePath();
    return image;
  }

  private void galleryAddPic() {
    // TODO: dialog fragment
    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    File f = new File(mCurrentPhotoPath);
    Uri contentUri = Uri.fromFile(f);
    mediaScanIntent.setData(contentUri);
    this.sendBroadcast(mediaScanIntent);
    showEditDialog(contentUri);
  }

  private void showEditDialog(Uri uri) {
    FragmentManager fm = getSupportFragmentManager();
    EditTagFragment editTagFragment = EditTagFragment.newInstance(uri);
    fm.beginTransaction().add(editTagFragment, "fragment_edit_name").commitAllowingStateLoss();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case REQUEST_TAKE_PHOTO:
          galleryAddPic();
          break;
        case REQUEST_IMAGE_PICK:
          Uri selectedImageUri = data.getData();
          String path = getRealPathFromURI(this, selectedImageUri);
          File f = new File(path);
          Uri uri = Uri.fromFile(f);
          showEditDialog(uri);
          break;
      }
    }
  }

  public static String getRealPathFromURI(Context context, Uri uri){
    String filePath = "";
    String wholeID = DocumentsContract.getDocumentId(uri);

    // Split at colon, use second item in the array
    String id = wholeID.split(":")[1];

    String[] column = { MediaStore.Images.Media.DATA };

    // where id is equal to
    String sel = MediaStore.Images.Media._ID + "=?";

    Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        column, sel, new String[]{ id }, null);

    int columnIndex = cursor.getColumnIndex(column[0]);

    if (cursor.moveToFirst()) {
      filePath = cursor.getString(columnIndex);
    }
    cursor.close();
    return filePath;
  }
}