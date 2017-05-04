/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.vrdemo;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
  VrPanoramaView panoWidgetView;

  /** Configuration information for the panorama. **/
  private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view);

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor("admin", "admin")).build();
    Request request = new Request.Builder()
        .url("https://firebasestorage.googleapis.com/v0/b/monimenta-5041a.appspot.com/o/images%2FPANO_20170503_103912.jpg?alt=media&token=ffaa6767-601c-4318-b5d4-73842b08a87d")
        .build();

    client.newCall(request)
        .enqueue(new Callback() {
          @Override
          public void onFailure(final Call call, IOException e) {
            // Error

            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                // For the example, you can show an error dialog or a toast
                // on the main UI thread
              }
            });
          }

          @Override
          public void onResponse(Call call, final Response response) throws IOException {
            //String res = response.body().string();

            // Do something with the response
            InputStream inputStream = response.body().byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            panoWidgetView.loadImageFromBitmap(bitmap, panoOptions);
          }
        });
  }

  @Override
  protected void onPause() {
    panoWidgetView.pauseRendering();
    super.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    panoWidgetView.resumeRendering();
  }

  @Override
  protected void onDestroy() {
    // Destroy the widget and free memory.
    panoWidgetView.shutdown();
    super.onDestroy();
  }


  public class BasicAuthInterceptor implements Interceptor {

    private String credentials;

    public BasicAuthInterceptor(String user, String password) {
      this.credentials = Credentials.basic(user, password);
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
      Request request = chain.request();
      Request authenticatedRequest = request.newBuilder()
          .header("Authorization", credentials).build();
      return chain.proceed(authenticatedRequest);
    }

  }
}
