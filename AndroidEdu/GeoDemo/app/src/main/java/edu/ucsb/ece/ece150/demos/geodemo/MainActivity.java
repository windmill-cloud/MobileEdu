package edu.ucsb.ece.ece150.demos.geodemo;

import android.Manifest;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

  private SupportMapFragment mapFragment;

  private GoogleApiClient mGoogleApiClient;
  private LocationRequest mLocationRequest;
  private GoogleMap mMap;

  private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
  private long FASTEST_INTERVAL = 2000; /* 2 sec */

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // Create the location client to start receiving updates
    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addApi(LocationServices.API)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this).build();

    mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap map) {
        mMap = map;
      }
    });
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    // NOTE: delegate the permission handling to generated method
    MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
  }

  protected void onStart() {
    super.onStart();
    // Connect the client.
    mGoogleApiClient.connect();
  }

  protected void onStop() {
    // Disconnecting the client invalidates it.
    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    // only stop if it's connected, otherwise we crash
    if (mGoogleApiClient != null) {
      mGoogleApiClient.disconnect();
    }
    super.onStop();
  }

  @Override
  @SuppressWarnings("all")
  @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
  public void onConnected(Bundle dataBundle) {
    // Get last known recent location.
    Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    // Note that this can be NULL if last location isn't already known.
    if (mCurrentLocation != null) {
      // Print current location if not null
      Log.d("DEBUG", "current location: " + mCurrentLocation.toString());
      LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
    }
    // Begin polling for new location updates.
    MainActivityPermissionsDispatcher.startLocationUpdatesWithCheck(MainActivity.this);
  }

  @Override
  public void onConnectionSuspended(int i) {
    if (i == CAUSE_SERVICE_DISCONNECTED) {
      Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
    } else if (i == CAUSE_NETWORK_LOST) {
      Toast.makeText(this, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
    }
  }

  // Trigger new location updates at interval
  @SuppressWarnings("all")
  @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
  protected void startLocationUpdates() {
    // Create the location request
    mLocationRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(UPDATE_INTERVAL)
        .setFastestInterval(FASTEST_INTERVAL);
    // Request location updates
    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
        mLocationRequest, this);
  }
  //and then register for location updates with onLocationChanged:
  @Override
  public void onLocationChanged(Location location) {
    // New location has now been determined
    String msg = "Updated Location: " +
        Double.toString(location.getLatitude()) + "," +
        Double.toString(location.getLongitude());
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    // You can now create a LatLng Object for use with maps
    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
    mMap.animateCamera(cameraUpdate);
  }

  // Annotate a method which is invoked if the user doesn't grant the permissions
  @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
  void showDeniedForPhoneCall() {
    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
  }


  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }
}
