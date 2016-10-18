package draco.ece1778_assignment3.Classes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
/**
 * Created by Draco on 2016-10-17.
 */

public class GPSHandler {
    private static GPSHandler gpsHandler = null;

    // Debugging Tag
    private String TAG = "Location Handler";

    // PRIVATE CLASS VARIABLES
    private GoogleApiClient mGoogleApiClient = null;
    private Location mCurrentLocation = null;
    private boolean mRequestingLocationUpdates = false; // if location api is on this is true
    private LocationRequest mLocationRequest = null;
    private Context mContext = null;

    private GoogleApiClient.ConnectionCallbacks mLocationCallback =
            new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    gpsHandler.onConnected();
                }

                @Override
                public void onConnectionSuspended(int i) {
                    gpsHandler.onSuspended();
                }
            };
    private GoogleApiClient.OnConnectionFailedListener mLocationFailedListener =
            new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    gpsHandler.onConnectionFail();
                }
            };
    private LocationListener mLocationListener =
            new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    gpsHandler.onLocationChanged(location);
                }
            };

    // private constructor for singleton class
    private GPSHandler() {
    }

    // Public Interface Functions
    // Initialize onCreate
    public static void initHandler(Context context) {
        if (gpsHandler == null) {
            gpsHandler = new GPSHandler();
            gpsHandler.initInitialModels(context);
            // 5sec fastest, 10sec normal, high accuracy
            gpsHandler.setLocationRequest(10000, 5000, LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
    }

    // get handler function
    public static GPSHandler getHandler() {
        return gpsHandler;
    }

    // Called on Resume
    public static void onResume() {
        if (gpsHandler.mRequestingLocationUpdates == false) {
            gpsHandler.startLocationUpdates();
            gpsHandler.mRequestingLocationUpdates = true;
        }
    }

    // Called on Pause
    public static void onPause() {
        if (gpsHandler.mRequestingLocationUpdates == true) {
            gpsHandler.stopLocationUpdates();
            gpsHandler.mRequestingLocationUpdates = false;
        }
    }

    public static void onStart() {
        gpsHandler.mGoogleApiClient.connect();
    }
    public static void onStop() {
        gpsHandler.mGoogleApiClient.disconnect();
    }
    // Get Location
    public Location getLocation() {
        return mCurrentLocation;
    }

    public String getLocationString () {
        if (mCurrentLocation == null) {
            return "";
        }
        return  "(" + mCurrentLocation.getLatitude() +
                " , " + mCurrentLocation.getLongitude() + ")";
    }

    // Private Worker functions
    protected synchronized void buildGoogleApiClient() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
        Log.d(TAG, "Google Play Services Status: " + Integer.toString(status));
        if (status != ConnectionResult.SUCCESS) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) mContext, 1);
            dialog.show();
        } else {
            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                        .addConnectionCallbacks(mLocationCallback)
                        .addOnConnectionFailedListener(mLocationFailedListener)
                        .addApi(LocationServices.API)
                        .build();
            }
        }
    }

    protected void initInitialModels(Context context) {
        if (mContext == null) {
            mContext = context;
        }
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
        }
        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
        }
        mRequestingLocationUpdates = false;
    }

    protected void setLocationRequest(int interval, int fastestInterval, int priority) {
        mLocationRequest.setInterval(interval);
        mLocationRequest.setFastestInterval(fastestInterval);
        mLocationRequest.setPriority(priority);
    }

    protected void startLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, mLocationListener);
        }
    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, mLocationListener);
        }
    }

    // Connection Functions.  Handles the listeners and callbacks
    protected void onConnected() {
        Log.d(TAG, "onConnected Connection Callback");
        startLocationUpdates();

        GPSHandler.onResume();
    }

    protected void onSuspended() {
        Log.d(TAG, "onConnectedSuspended Connection Callback");
        GPSHandler.onPause();
    }

    protected void onConnectionFail() {
        Log.d(TAG, "onConnectionFail Listener");
    }

    protected void onLocationChanged(Location location) {
        Log.d(TAG, "onConnectionChanged Listener");
        mCurrentLocation = location;
    }

}
