/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.storeinfirebase;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final int IMAGE_PICKING_REQUEST = 0;
  private static final List<Uri> mListUri = new ArrayList<>();
  private static final String TAG = "AnonymousAuth";

  private TextView mTextView;
  private FirebaseAuth mAuth;
  private FirebaseStorage storage;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mAuth = FirebaseAuth.getInstance();

    Button selectButton = (Button) findViewById(R.id.buttonSelect);
    selectButton.setOnClickListener(new View.OnClickListener(){

      @Override
      public void onClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), IMAGE_PICKING_REQUEST);
      }
    });

    mTextView = (TextView) findViewById(R.id.text);
    signInAnonymously();
  }

  private void signInAnonymously() {
    mAuth.signInAnonymously()
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information
              Log.d(TAG, "signInAnonymously:success");
              FirebaseUser user = mAuth.getCurrentUser();
              storage = FirebaseStorage.getInstance();
            } else {
              // If sign in fails, display a message to the user.
              Log.w(TAG, "signInAnonymously:failure", task.getException());
              Toast.makeText(MainActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
            }

          }
        });
  }

  private void signOut() {
    mAuth.signOut();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == IMAGE_PICKING_REQUEST && resultCode == RESULT_OK){
      mListUri.clear();
      if(data.getData() != null) {
        Uri uri = data.getData();
        mListUri.add(uri);
      } else {
        ClipData clipData = data.getClipData();
        if(clipData != null){
          for (int i = 0; i < clipData.getItemCount(); i++) {
            ClipData.Item item = clipData.getItemAt(i);
            Uri uri = item.getUri();
            mListUri.add(uri);
          }
        }
      }

      mTextView.setText(mListUri.toString());
      if(storage != null) {
        StorageReference storageRef = storage.getReference();

        for(Uri uri: mListUri) {
          StorageReference riversRef = storageRef.child("images/" + uri.getLastPathSegment());
          UploadTask uploadTask = riversRef.putFile(uri);

          // Register observers to listen for when the download is done or if it fails
          uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
              // Handle unsuccessful uploads
            }
          }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
              @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
          });
        }

      }
    }
  }
}
