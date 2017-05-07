package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Samuel on 5/2/2017.
 */

public class ImageTagDatabaseHelper extends SQLiteOpenHelper {
  private static final String CreateImageTable =
      "CREATE TABLE Image (Id text PRIMARY KEY, Uri text NOT NULL UNIQUE);";
  private static final String CreateTagTable =
      "CREATE TABLE Tag (Id text PRIMARY KEY, Text text NOT NULL UNIQUE);";
  private static final String CreateLinkTable =
      "CREATE TABLE Link (ImageId text, TagId text, PRIMARY KEY (ImageId, TagId), " +
          "FOREIGN KEY (ImageId) REFERENCES Image (Id) ON DELETE CASCADE ON UPDATE NO ACTION, " +
          "FOREIGN KEY (TagId) REFERENCES Tag (Id) ON DELETE CASCADE ON UPDATE NO ACTION);";
  private static final String DatabaseName = "ImageTagDatabase.db";

  private static final String URI = "Uri";
  private static final String TAG = "Text";
  private static final String IMAGE_ID = "Id";
  private static final String TAG_ID = "Id";
  private static final String LINK_IMAGE_ID = "ImageId";
  private static final String LINK_TAG_ID = "TagId";

  private static final String TABLE_IMAGE = "Image";
  private static final String TABLE_LINK = "Link";
  private static final String TABLE_TAG = "Tag";

  private static ImageTagDatabaseHelper Instance;
  private List<OnDatabaseChangeListener> Listeners;

  private ImageTagDatabaseHelper(Context context) {
    super(context, DatabaseName, null, 1);
    Listeners = new ArrayList<>();
  }

  public static void initialize(Context context) {
    Instance = new ImageTagDatabaseHelper(context);
  }

  public static ImageTagDatabaseHelper getInstance() {
    return Instance;
  }

  public void subscribe(OnDatabaseChangeListener listener) {
    Listeners.add(listener);
  }

  private boolean tryUpdate(Cursor cursor) {
    try {
      cursor.moveToFirst();
    } catch (SQLiteConstraintException exception) {
      return false;
    } finally {
      cursor.close();
    }
    notifyListeners();
    return true;
  }

  private void notifyListeners() {
    for (OnDatabaseChangeListener listener : Listeners) {
      listener.onDatabaseChange();
    }
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CreateImageTable);
    db.execSQL(CreateTagTable);
    db.execSQL(CreateLinkTable);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }

  public interface OnDatabaseChangeListener {
    void onDatabaseChange();
  }

  // Adding new contact
  public void addTaggedImage(TaggedImageRetriever.TaggedImage taggedImage) {
    SQLiteDatabase db = getWritableDatabase();

    ContentValues imageContent = new ContentValues();
    imageContent.put(URI, taggedImage.uri.toString());
    String uuid = UUID.randomUUID().toString();
    imageContent.put(IMAGE_ID, uuid);

    db.insert(TABLE_IMAGE, null, imageContent);

    for(String tag: taggedImage.tags){

      //Cursor cursor = db.query(TABLE_TAG, new String[] { TAG_ID }, TAG + "=" + "'" + tag + "'", null, null, null, null);
      Cursor cursor = db.query(TABLE_TAG, new String[] { TAG_ID }, TAG + "=" + "'" + tag + "'", null, null, null, null);

      if (cursor.getCount() > 0) { // the tag exists
        cursor.moveToFirst();
        String tagId = cursor.getString(
            cursor.getColumnIndex(TAG_ID));

        ContentValues link = new ContentValues();
        link.put(LINK_IMAGE_ID, uuid);
        link.put(LINK_TAG_ID, tagId);
        db.insert(TABLE_LINK, null, link);

      } else { // the tag doesn't exist
        // insert into tag table
        String tagId = UUID.randomUUID().toString();
        ContentValues tagContent = new ContentValues();
        tagContent.put(TAG_ID, tagId);
        tagContent.put(TAG, tag);
        db.insert(TABLE_TAG, null, tagContent);

        // insert into link table
        ContentValues linkContent = new ContentValues();
        linkContent.put(LINK_IMAGE_ID, uuid);
        linkContent.put(LINK_TAG_ID, tagId);
        db.insert(TABLE_LINK, null, linkContent);
      }
      cursor.close();
    }
    // Inserting Row

    db.close(); // Closing database connection
  }

  public void clearDatabase(){
    SQLiteDatabase db = getWritableDatabase();

    db.execSQL("DELETE FROM " + TABLE_IMAGE);
    db.execSQL("DELETE FROM " + TABLE_LINK);
    db.execSQL("DELETE FROM " + TABLE_TAG);

    db.close(); // Closing database connection
  }
/*
  // Getting single contact
  public Contact getContact(int id) {}

  // Getting All Contacts
  public List<Contact> getAllContacts() {}

  // Getting contacts Count
  public int getContactsCount() {}
  // Updating single contact
  public int updateContact(Contact contact) {}

  // Deleting single contact
  public void deleteContact(Contact contact) {}*/
}