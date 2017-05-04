/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.rxretrook;

import retrofit2.Retrofit;

/**
 * Created by xuanwang on 4/29/17.
 */

public class RetrofitNetHelper {
  public static RetrofitNetHelper mInstance;
  public Retrofit mRetrofit;
  public static final String BASE_URL = "http://es-ece-coe.herokuapp.com/";

  private RetrofitNetHelper(){
    mRetrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .build();
  }

  public static RetrofitNetHelper getInstance(){
    if(mInstance==null){
      synchronized (RetrofitNetHelper.class){
        if(mInstance==null)
          mInstance = new RetrofitNetHelper ();
      }
    }
    return mInstance ;
  }
}
