/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.geofencing;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;
import java.util.Random;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class GeofenceTransitionsIntentService extends IntentService {
  private static final String TAG = "Geo service";

  public GeofenceTransitionsIntentService() {
    // Use the TAG to name the worker thread.
    super(TAG);
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
    if (geofencingEvent.hasError()) {
      return;
    }

    // Get the transition type.
    int geofenceTransition = geofencingEvent.getGeofenceTransition();

    // Test that the reported transition was of interest.
    if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

      // Get the geofences that were triggered. A single event can trigger
      // multiple geofences.
      List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

      for(Geofence gf: triggeringGeofences){
        if (GeofencePlaceDict.getInstance().dict.containsKey(gf.getRequestId())) {
          String geofenceDetails = GeofencePlaceDict.getInstance().dict.get(gf.getRequestId());
          sendNotification(geofenceDetails);
        }
      }
    } else {
      // Log the error.
      //Log.e(TAG, getString(R.string.geofence_transition_invalid_type,
       //   geofenceTransition));
    }
  }


  /**
   * Posts a notification in the notification bar when a transition is detected.
   * If the user clicks the notification, control goes to the MainActivity.
   */
  private void sendNotification(String notificationDetails) {
    // Create an explicit content Intent that starts the main Activity.
    Intent notificationIntent = new Intent(getApplicationContext(), MapsActivity.class);

    // Construct a task stack.
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

    // Add the main Activity to the task stack as the parent.
    stackBuilder.addParentStack(MapsActivity.class);

    // Push the content Intent onto the stack.
    stackBuilder.addNextIntent(notificationIntent);

    // Get a PendingIntent containing the entire back stack.
    PendingIntent notificationPendingIntent =
        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

    // Get a notification builder that's compatible with platform versions >= 4
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

    // Define the notification settings.
    builder.setSmallIcon(R.mipmap.ic_launcher)
        // In a real app, you may want to use a library like Volley
        // to decode the Bitmap.
        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
            R.mipmap.ic_launcher))
        .setColor(Color.RED)
        .setContentTitle("Entered Point of Interest")
        .setDefaults(Notification.DEFAULT_ALL)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentText(notificationDetails)
        .setContentIntent(notificationPendingIntent);

    // Dismiss notification once the user touches it.
    builder.setAutoCancel(true);

    // Get an instance of the Notification manager
    NotificationManager mNotificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    // Issue the notification
    Random rand = new Random();

    int randomNum = rand.nextInt();
    mNotificationManager.notify(randomNum, builder.build());
  }
}
