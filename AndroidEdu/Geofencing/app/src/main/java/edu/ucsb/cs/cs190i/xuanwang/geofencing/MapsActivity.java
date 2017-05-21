/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.geofencing;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ucsb.cs.cs190i.xuanwang.geofencing.models.NearbyPlaces;
import edu.ucsb.cs.cs190i.xuanwang.geofencing.models.PlaceDetail;
import edu.ucsb.cs.cs190i.xuanwang.geofencing.models.PoiResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MapsActivity extends FragmentActivity implements
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener,
    GoogleMap.OnInfoWindowClickListener,
    GoogleMap.OnMarkerClickListener,
    ResultCallback<Status> {

  private Marker curLocMarker;
  private SupportMapFragment mapFragment;
  private GoogleMap map;
  private GoogleApiClient mGoogleApiClient;
  private LocationRequest mLocationRequest;
  private long UPDATE_INTERVAL = 60000;  /* 60 secs */
  private long FASTEST_INTERVAL = 2000; /* 2 secs */
  private String markerId = "";

  private LatLng currLoc = new LatLng(34.412540, -119.848043);
  private LatLng lastReqLoc = new LatLng(34.412540, -119.848043);
  private List<Marker> markerList = new ArrayList<>();
  private List<Circle> circleList = new ArrayList<>();
  private ArrayList<Geofence> mGeofenceList = new ArrayList<>();
  private PendingIntent mGeofencePendingIntent;

  private OkHttpClient client = new OkHttpClient();

  private static final String NEARBY_ENDPOINT = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
  private static final String DETAIL_ENDPOINT = "https://maps.googleapis.com/maps/api/place/details/json";
  private static final String PLACES_KEY = "AIzaSyBqAnsYewa8uspl-mlskNygaufNgKz_IPA";

  /*
  * Define a request code to send to Google Play services This code is
  * returned in Activity.onActivityResult
  */
  private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);

    mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
    if (mapFragment != null) {
      mapFragment.getMapAsync(new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap map) {
          loadMap(map);
        }
      });
    } else {
      Toast.makeText(this, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
    }
  }

  protected void loadMap(GoogleMap googleMap) {
    map = googleMap;
    if (map != null) {
      Toast.makeText(this, "Map Fragment was loaded properly!", Toast.LENGTH_SHORT).show();
      map.setOnMarkerClickListener(this);
      map.setOnInfoWindowClickListener(this);
      MapsActivityPermissionsDispatcher.getMyLocationWithCheck(this);
    } else {
      Toast.makeText(this, "Error - Map was null!!", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    MapsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
  }

  @SuppressWarnings("all")
  @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
  void getMyLocation() {
    if (map != null) {
      // Now that map has loaded, let's get our location!
      map.setMyLocationEnabled(true);
      map.getUiSettings().setCompassEnabled(true);
      mGoogleApiClient = new GoogleApiClient.Builder(this)
          .addApi(LocationServices.API)
          .addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this).build();
      connectClient();
      getPois();
    }
  }

  protected void getPois(){
    // Let's get the points of interest
    // Callback Hell!!!!
    // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=34.4137,-119.841316&
    // radius=500&key=AIzaSyBqAnsYewa8uspl-mlskNygaufNgKz_IPA
    String url = NEARBY_ENDPOINT + "?location=" + currLoc.latitude + "," + currLoc.longitude + "&radius=500&key=" + PLACES_KEY;
    lastReqLoc = new LatLng(currLoc.latitude, currLoc.longitude);
    Request request = new Request.Builder()
        .url(url)
        .build();

    client.newCall(request).enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        e.printStackTrace();
      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String nearbyPlaces = response.body().string();
        Gson gson = new Gson();
        final NearbyPlaces pois = gson.fromJson(nearbyPlaces, NearbyPlaces.class);

        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            for(Marker mkr: markerList) {
              mkr.remove();
            }

            for(Circle crl: circleList) {
              crl.remove();
            }
            markerList.clear();
            mGeofenceList.clear();
            GeofencePlaceDict.getInstance().dict.clear();

            for(PoiResult place: pois.getResults()){
              // Set the color of the marker to red
              final BitmapDescriptor defaultMarker =
                  BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

              double lat = place.getGeometry().getLocation().getLat();
              double lng = place.getGeometry().getLocation().getLng();
              LatLng loc = new LatLng(lat, lng);

              Marker marker = map.addMarker(new MarkerOptions()
                  .position(loc)
                  .title(place.getName())
                  .icon(defaultMarker));
              marker.setTag(place.getPlaceId());

              markerList.add(marker);

              CircleOptions circleOptions = new CircleOptions()
                  .strokeWidth(4)
                  .center(loc)
                  .radius(25); // In meters

              // Get back the mutable Circle
              Circle circle = map.addCircle(circleOptions);
              circleList.add(circle);

              Geofence geofence = new Geofence.Builder()
                  // Set the request ID of the geofence. This is a string to identify this
                  // geofence.
                  .setRequestId(place.getId())
                  .setCircularRegion(
                      lat,
                      lng,
                      25
                  )
                  .setExpirationDuration(60 * 60 * 1000)
                  .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                      Geofence.GEOFENCE_TRANSITION_EXIT)
                  .build();
              mGeofenceList.add(geofence);
              GeofencePlaceDict.getInstance().dict.put(place.getId(), place.getName());

            }
            try {
              LocationServices.GeofencingApi.addGeofences(
                  mGoogleApiClient,
                  getGeofencingRequest(),
                  getGeofencePendingIntent()
              ).setResultCallback(MapsActivity.this);
            } catch (SecurityException e) {
              Log.e("", e.toString());
            }
          }
        });
      }
    });
  }

  private GeofencingRequest getGeofencingRequest() {
    GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
    builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
    builder.addGeofences(mGeofenceList);
    return builder.build();
  }

  private PendingIntent getGeofencePendingIntent() {
    // Reuse the PendingIntent if we already have it.
    if (mGeofencePendingIntent != null) {
      return mGeofencePendingIntent;
    }

    Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
    // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
    // calling addGeofences() and removeGeofences().

    mGeofencePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.
        FLAG_UPDATE_CURRENT);
    return mGeofencePendingIntent;
  }

  protected void connectClient() {
    // Connect the client.
    if (isGooglePlayServicesAvailable() && mGoogleApiClient != null) {
      mGoogleApiClient.connect();
    }
  }

  /*
   * Called when the Activity becomes visible.
  */
  @Override
  protected void onStart() {
    super.onStart();
    connectClient();
  }

  /*
  * Called when the Activity is no longer visible.
  */
  @Override
  protected void onStop() {
    // Disconnecting the client invalidates it.
    if (mGoogleApiClient != null) {
      mGoogleApiClient.disconnect();
    }
    super.onStop();
  }

  /*
   * Handle results returned to the FragmentActivity by Google Play services
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // Decide what to do based on the original request code
    switch (requestCode) {

      case CONNECTION_FAILURE_RESOLUTION_REQUEST:
			/*
			 * If the result code is Activity.RESULT_OK, try to connect again
			 */
        switch (resultCode) {
          case Activity.RESULT_OK:
            mGoogleApiClient.connect();
            break;
        }
        break;
    }
  }

  private boolean isGooglePlayServicesAvailable() {
    // Check that Google Play services is available
    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    // If Google Play services is available
    if (ConnectionResult.SUCCESS == resultCode) {
      // In debug mode, log the status
      Log.d("Location Updates", "Google Play services is available.");
      return true;
    } else {
      // Get the error dialog from Google Play services
      Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this,
          CONNECTION_FAILURE_RESOLUTION_REQUEST);

      // If Google Play services can provide an error dialog
      if (errorDialog != null) {
        // Create a new DialogFragment for the error dialog
        ErrorDialogFragment errorFragment = new ErrorDialogFragment();
        errorFragment.setDialog(errorDialog);
        errorFragment.show(getSupportFragmentManager(), "Location Updates");
      }

      return false;
    }
  }

  @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
  void showDeniedForCamera() {
    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
    finish();
  }
  /*
   * Called by Location Services when the request to connect the client
   * finishes successfully. At this point, you can request the current
   * location or start periodic updates
   */
  @SuppressWarnings("all")
  @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
  @Override
  public void onConnected(Bundle dataBundle) {
    // Display the connection status
    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    if (location != null) {
      Toast.makeText(this, "GPS location was found!", Toast.LENGTH_SHORT).show();
      currLoc = new LatLng(location.getLatitude(), location.getLongitude());
      CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currLoc, 17);
      map.animateCamera(cameraUpdate);
    } else {
      Toast.makeText(this, "Current location was null, enable GPS on emulator!", Toast.LENGTH_SHORT).show();
    }
    startLocationUpdates();
  }

  @SuppressWarnings("all")
  @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
  protected void startLocationUpdates() {
    mLocationRequest = new LocationRequest();
    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    mLocationRequest.setInterval(UPDATE_INTERVAL);
    mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
        mLocationRequest, this);
  }

  @Override
  public void onLocationChanged(Location location) {
    // Report to the UI that the location was updated
    String msg = "Updated Location: " +
        Double.toString(location.getLatitude()) + "," +
        Double.toString(location.getLongitude());
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    Location lastMarkerReqLoc = new Location("Position A");
    lastMarkerReqLoc.setLatitude(lastReqLoc.latitude);
    lastMarkerReqLoc.setLongitude(lastReqLoc.longitude);

    if(location.distanceTo(lastMarkerReqLoc) > 250.0){
      getPois();
    }

    LatLng lastLoc = new LatLng(currLoc.latitude, currLoc.longitude);

    currLoc = new LatLng(location.getLatitude(), location.getLongitude());

    Polyline line = map.addPolyline(new PolylineOptions()
        .add(lastLoc, currLoc)
        .width(5)
        .color(Color.BLUE));

    // Set the color of the marker to blue
    BitmapDescriptor defaultMarker =
        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
    // Create the marker on the fragment
    if(curLocMarker != null) {
      curLocMarker.remove();
    }
    curLocMarker = map.addMarker(new MarkerOptions()
        .position(currLoc)
        .title("Current Location")
        .snippet("" + currLoc.latitude + ", " + currLoc.longitude)
        .icon(defaultMarker));
  }

  /*
     * Called by Location Services if the connection to the location client
     * drops because of an error.
     */
  @Override
  public void onConnectionSuspended(int i) {
    if (i == CAUSE_SERVICE_DISCONNECTED) {
      Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
    } else if (i == CAUSE_NETWORK_LOST) {
      Toast.makeText(this, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
    }
  }

  /*
   * Called by Location Services if the attempt to Location Services fails.
   */
  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
    if (connectionResult.hasResolution()) {
      try {
        // Start an Activity that tries to resolve the error
        connectionResult.startResolutionForResult(this,
            CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
      } catch (IntentSender.SendIntentException e) {
        // Log the error
        e.printStackTrace();
      }
    } else {
      Toast.makeText(getApplicationContext(),
          "Sorry. Location services not available to you", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void onInfoWindowClick(Marker marker) {
    Toast.makeText(this, "Info window clicked",
        Toast.LENGTH_SHORT).show();
    getAndGotoUrl(markerId);
  }

  protected void getAndGotoUrl(String markerPlaceId){
    // Get location detail url and start a webview
    // Callback Hell!!!!
    // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=34.4137,-119.841316&
    // radius=500&key=AIzaSyBqAnsYewa8uspl-mlskNygaufNgKz_IPA
    String url = DETAIL_ENDPOINT + "?place_id=" + markerPlaceId + "&key=" + PLACES_KEY;
    Request request = new Request.Builder()
        .url(url)
        .build();

    client.newCall(request).enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        e.printStackTrace();
      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String place = response.body().string();
        Gson gson = new Gson();
        final PlaceDetail plcdtl = gson.fromJson(place, PlaceDetail.class);

        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Intent browserIntent = new Intent(MapsActivity.this, PlaceDetailActivity.class);
            browserIntent.putExtra("URL", plcdtl.getResult().getUrl());
            startActivity(browserIntent);
          }
        });
      }
    });
  }

  @Override
  public boolean onMarkerClick(Marker marker) {
    Toast.makeText(this, (String) marker.getTag(),
        Toast.LENGTH_SHORT).show();
    markerId = (String) marker.getTag();
    marker.showInfoWindow();
    return true;
  }

  @Override
  public void onResult(@NonNull Status status) {
    if (status.isSuccess()) {

    } else {
      // Get the status code for the error and log it using a user-friendly message.
    }
  }

  // Define a DialogFragment that displays the error dialog
  public static class ErrorDialogFragment extends DialogFragment {

    // Global field to contain the error dialog
    private Dialog mDialog;

    // Default constructor. Sets the dialog field to null
    public ErrorDialogFragment() {
      super();
      mDialog = null;
    }

    // Set the dialog to display
    public void setDialog(Dialog dialog) {
      mDialog = dialog;
    }

    // Return a Dialog to the DialogFragment.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      return mDialog;
    }
  }

}
