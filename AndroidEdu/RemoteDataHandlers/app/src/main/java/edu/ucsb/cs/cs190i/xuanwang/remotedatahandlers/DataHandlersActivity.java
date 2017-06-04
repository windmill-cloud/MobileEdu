/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.remotedatahandlers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataHandlersActivity extends AppCompatActivity {

  String id = "57e7c595-bb3f-4c3d-b679-677b6e1d3ae11495402652269";
  String TAG = "TOWN";

  Town town = null;
  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  FirebaseDatabase database;
  DatabaseReference townsDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_data_handlers);
    ButterKnife.bind(this);

    database = FirebaseDatabase.getInstance();
    townsDatabase = FirebaseDatabase.getInstance().getReference().child("towns");

    Query q = townsDatabase.child(id);
    q.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        printTown(dataSnapshot);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @OnClick(R.id.button_get_likes)
  public void getLikes() {
    DatabaseReference likesRef = townsDatabase.child(id).child("numOfLikes");
    likesRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d("NumOfLikes", dataSnapshot.getValue().toString());
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @OnClick(R.id.button_increase_likes)
  public void increaseLikes() {
    town.increaseLikes();
    DatabaseReference likesRef = townsDatabase.child(id).child("numOfLikes");
    likesRef.setValue(town.getNumOfLikes(),
        new DatabaseReference.CompletionListener() {
          public void onComplete(DatabaseError err, DatabaseReference ref){
            if (err == null) {
              Log.d("INC_LIKE", "Setting num of likes succeeded");
            }
          }
        }
    );
  }

  @OnClick(R.id.button_get_date)
  public void getDate() {
    DatabaseReference dateRef = townsDatabase.child(id).child("date");
    dateRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d("Date", dataSnapshot.getValue().toString());
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @OnClick(R.id.button_set_date)
  public void setDate() {
    DatabaseReference dateRef = townsDatabase.child(id).child("date");

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat mdformat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
    String strDate = mdformat.format(cal.getTime());
    town.setDate(strDate);
    dateRef.setValue(town.getDate(),
        new DatabaseReference.CompletionListener() {
          public void onComplete(DatabaseError err, DatabaseReference ref){
            if (err == null) {
              Log.d("SET_DATE", "Setting date succeeded");
            }
          }
        }
    );
  }

  @OnClick(R.id.button_get_all)
  public void getAllTowns() {
    townsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds: dataSnapshot.getChildren()){
          Town t = ds.getValue(Town.class);
          printTown(t);
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @OnClick(R.id.button_get_description_array)
  public void getDescriptionArray() {
    DatabaseReference descriptionRef = townsDatabase.child(id).child("description");
    descriptionRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d("Description", dataSnapshot.getValue().toString());
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @OnClick(R.id.button_set_description_array)
  public void setDescriptionArray() {
    DatabaseReference descriptionRef = townsDatabase.child(id).child("description");
    town.getDescription().add("nonono");
    descriptionRef.setValue(town.getDescription(),
        new DatabaseReference.CompletionListener() {
          public void onComplete(DatabaseError err, DatabaseReference ref){
            if (err == null) {
              Log.d("SET_DES", "Setting description succeeded");
            }
          }
    });
  }

  @OnClick(R.id.button_search)
  public void search() {
    // I don't know how to search
  }

  @OnClick(R.id.button_get_sketch_url)
  public void getSketchUrl() {
    DatabaseReference descriptionRef = townsDatabase.child(id).child("sketch");
    descriptionRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d("Sketch", dataSnapshot.getValue().toString());
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @OnClick(R.id.button_set_sketch_url)
  public void setSketchUrl() {
    DatabaseReference descriptionRef = townsDatabase.child(id).child("sketch");
    town.setSketch("https://firebasestorage.googleapis.com/v0/b/newproject-720b7.appspot.com/o/images%2Fimage%253A682a773d7f1-f4f1-47ed-8c84-ac60f019e0d31495402655882.jpg?alt=media&token=fdca9b93-9bb2-4425-abde-ea4a514b1ba2");
    descriptionRef.setValue(town.getSketch(),
        new DatabaseReference.CompletionListener() {
          public void onComplete(DatabaseError err, DatabaseReference ref){
            if (err == null) {
              Log.d("SET_SKETCH", "Setting sketch succeeded");
            }
          }
        });
  }

  @OnClick(R.id.button_get_image_array)
  public void getImageArray() {
    DatabaseReference descriptionRef = townsDatabase.child(id).child("imageUrls");
    descriptionRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d("IMAGES", dataSnapshot.getValue().toString());
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @OnClick(R.id.button_set_image_array)
  public void setImageArray() {
    DatabaseReference descriptionRef = townsDatabase.child(id).child("imageUrls");
    town.getImageUrls().add("https://upload.wikimedia.org/wikipedia/commons/b/b5/Kotlin-logo.png");
    descriptionRef.setValue(town.getImageUrls(),
        new DatabaseReference.CompletionListener() {
          public void onComplete(DatabaseError err, DatabaseReference ref){
            if (err == null) {
              Log.d("IMAGES", "Setting images succeeded");
            }
          }
        });
  }

  @OnClick(R.id.button_get_user_posts_number)
  public void getUsersPosts() {
    Query q = townsDatabase.orderByChild("userId").equalTo(UserSingleton.getInstance().getUid());
    q.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds: dataSnapshot.getChildren()){
          Town t = ds.getValue(Town.class);
          printTown(t);
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  void printTown(DataSnapshot dataSnapshot) {
    town = dataSnapshot.getValue(Town.class);
    String result = gson.toJson(town);
    Log.d(TAG, result);
  }

  void printTown(Town town) {
    String result = gson.toJson(town);
    Log.d(TAG, result);
  }
}
