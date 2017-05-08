package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by xuanwang on 5/6/17.
 */

public class ImageTagExplorerApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    //Stetho.initializeWithDefaults(this);

  }
}