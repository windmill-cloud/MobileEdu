/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.rxretrook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
  Retrofit mRetrofit;
  private CompositeDisposable mCompositeDisposable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);



    File cacheFile = new File(getApplicationContext().getCacheDir(), "HttpCache");
    Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

    mCompositeDisposable = new CompositeDisposable();

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        //.addInterceptor(new BasicAuthInterceptor("admin", "admin"))
        .cache(cache)
        .build();

    /*

    mRetrofit = new Retrofit.Builder()
        .baseUrl("https://es-ece-coe.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(okHttpClient)
        .build();
*/
    AlbumService albumService = new Retrofit.Builder()
        .baseUrl(AlbumService.SERVICE_ENDPOINT)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build().create(AlbumService.class);

    albumService.getAlbums()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(this::handleResponse, this::handleError);

  }

  private void handleResponse(List<Album> albumList) {
    int i = 0;

  }

  private void handleError(Throwable error) {

    Toast.makeText(this, "Error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
  }

  public interface AlbumService {
    String SERVICE_ENDPOINT = "https://es-ece-coe.herokuapp.com";

    @GET("/")
    Observable<List<Album>> getAlbums();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mCompositeDisposable.clear();
  }
}
