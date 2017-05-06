/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.lithoplay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;

import com.facebook.litho.Size;
import com.facebook.litho.SizeSpec;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnMeasure;
import com.facebook.litho.annotations.OnMount;
import com.facebook.litho.annotations.Prop;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.google.vr.cardboard.ThreadUtils.runOnUiThread;

/**
 * Created by xuanwang on 5/4/17.
 */

@MountSpec
public class VrViewSpec {

  @OnMeasure
  static void onMeasure(ComponentContext c, ComponentLayout layout, int widthSpec, int heightSpec, Size size) {
    // If width is undefined, set default size.
    if (SizeSpec.getMode(widthSpec) == SizeSpec.UNSPECIFIED) {
      size.width = 400;
    } else {
      size.width = SizeSpec.getSize(widthSpec);
    }

    // If height is undefined, use 1.5 aspect ratio.
    if (SizeSpec.getMode(heightSpec) == SizeSpec.UNSPECIFIED) {
      size.height = (int)(size.width * 0.5);
    } else {
      size.height = SizeSpec.getSize(heightSpec);
    }

  }

  @OnCreateMountContent
  static VrPanoramaView onCreateMountContent(ComponentContext c) {
    return new VrPanoramaView(c.getBaseContext());
  }

  @OnMount
  static void onMount(
    ComponentContext context,
    final VrPanoramaView vrPanoramaView,
    @Prop String uri) {

    final VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();

    String u = !uri.equals("") ? uri: "https://firebasestorage.googleapis.com/v0/b/monimenta-5041a.appspot.com/o/images%2FPANO_20170503_103912.jpg?alt=media&token=ffaa6767-601c-4318-b5d4-73842b08a87d";

    OkHttpClient client = new OkHttpClient.Builder().build();
    Request request = new Request.Builder()
        .url(u)
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
            // Do something with the response
            InputStream inputStream = response.body().byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            vrPanoramaView.loadImageFromBitmap(bitmap, panoOptions);
          }
        });
  }

}