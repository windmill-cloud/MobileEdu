package edu.ucsb.cs.cs185.fbcomponents;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by xuanwang on 2/2/17.
 */

public class FBComponentsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
