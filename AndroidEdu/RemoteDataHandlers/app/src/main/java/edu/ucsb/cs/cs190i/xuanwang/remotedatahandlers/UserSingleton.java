/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.remotedatahandlers;

import android.net.Uri;

public class UserSingleton  {
  String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Uri getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(Uri photoUrl) {
    this.photoUrl = photoUrl;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  String email;
  Uri photoUrl;

  // The user's ID, unique to the Firebase project. Do NOT use this value to
  // authenticate with your backend server, if you have one. Use
  // FirebaseUser.getToken() instead.
  String uid;

  private static UserSingleton instance;

  private UserSingleton(){}

  public static synchronized UserSingleton getInstance(){
    if(instance == null){
      instance = new UserSingleton();
    }
    return instance;
  }
}
