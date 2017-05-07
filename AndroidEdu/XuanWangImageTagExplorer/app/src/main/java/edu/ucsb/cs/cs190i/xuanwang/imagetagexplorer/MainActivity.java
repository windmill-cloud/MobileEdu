package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer.models.ImageItem;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
  String mCurrentPhotoPath;
  List<ImageItem> imageList = new ArrayList<>();

  ImageAdapter imageAdapter;
  TagAdapter tagAdapter;

  @BindView(R.id.main_rv)
  RecyclerView imageRecycler;

  @BindView(R.id.text_tag_search)
  AutoCompleteTextView searchView;

  @BindView(R.id.tag_rv)
  RecyclerView tagRecycler;

  Set<String> tagSet = new HashSet<>();
  Set<String> searchTagSet = new HashSet<>();
  List<String> searchTagList = new ArrayList<>();

  String[] tags;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ImageTagDatabaseHelper.initialize(this);

    imageAdapter = new ImageAdapter(this);
    imageRecycler.setLayoutManager(new GridLayoutManager(this, 2));
    imageRecycler.setAdapter(imageAdapter);
    imageRecycler.setHasFixedSize(true);

    List<ImageItem> list = ImageTagDatabaseHelper.getInstance().getImagesFromDb();
    imageList.addAll(list);
    imageAdapter.setContent(imageList);

    imageAdapter.setOnItemClickListener(new ImageAdapter.OnRecyclerViewItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
        // TODO: start dialog
        showEditDialog(imageList.get(position));
      }
    });

    tagAdapter = new TagAdapter(this);
    tagRecycler.setAdapter(tagAdapter);
    LinearLayoutManager layoutManager
        = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    tagRecycler.setLayoutManager(layoutManager);
    tagAdapter.setOnItemClickListener(new TagAdapter.OnRecyclerViewItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        String tag = searchTagList.get(position);
        searchTagSet.remove(tag);
        searchTagList.remove(position);
        tagAdapter.setData(searchTagList);
        // TODO: refresh search
      }
    });

    tags = ImageTagDatabaseHelper.getInstance().getAllTagsFromDb();
    for(String tag :tags){
      tagSet.add(tag);
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, tags);

    searchView.setAdapter(adapter);
    searchView.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(tagSet.contains(charSequence.toString())){
          Toast.makeText(MainActivity.this, charSequence, Toast.LENGTH_SHORT).show();
          searchTagSet.add(charSequence.toString());
          searchTagList = new ArrayList<>(searchTagSet);
          tagAdapter.setData(searchTagList);

          // TODO: perform search
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

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

  }

  @Override
  protected void onResume() {
    super.onResume();
    imageAdapter.notifyDataSetChanged();
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

                  ImageItem imgItem = new ImageItem(
                      UUID.randomUUID().toString(),
                      Uri.fromFile(file).toString());
                  //MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                  //new File(getExternalFilesDir(null), "ImageTagExplorer-ed1350b7-ebf7-44bd-8923-33bfe90d0ba5.jpg").exists()
                  ImageTagDatabaseHelper.getInstance().addTaggedImage(imgItem, image.tags);
                  imageList.add(imgItem);
                  imageAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                  e.printStackTrace();
                }
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
    imageAdapter.clearContent();
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

  private void showEditDialog(ImageItem imageItem) {
    FragmentManager fm = getSupportFragmentManager();
    EditTagFragment editTagFragment = EditTagFragment.newInstance(imageItem);
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