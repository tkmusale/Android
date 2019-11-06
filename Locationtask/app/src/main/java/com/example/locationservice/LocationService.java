package com.example.locationservice;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.example.locationservice.Constants.RECEIVER;

public class LocationService extends IntentService implements LocationListener {
    private static final String TAG = "LocationService";
    private LocationManager mLocationManager;
    private Geocoder mGeocoder;
    private Location location;
    private List<Address> mAddressList = new ArrayList<>();
    protected ResultReceiver receiver;
    private static final long MIN_DISTANCE_TO_UPDATE = 10L;
    private static final long MIN_TIME_TO_UPDATES = 1000L;
    private String errorMessage = "";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public LocationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            receiver = intent.getParcelableExtra(RECEIVER);
        }
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mGeocoder = new Geocoder(this, Locale.getDefault());
        getLocation();
        getAddress();
    }

    private void getAddress() {
        if (location != null) {
            try {
                mAddressList = mGeocoder.getFromLocation(
                        location.getLatitude(),
                        location.getLongitude(),
                        1);
            } catch (IOException ioException) {
                errorMessage = getString(R.string.service_not_available);
            } catch (IllegalArgumentException illegalArgumentException) {
                // Catch invalid latitude or longitude values.
                errorMessage = getString(R.string.invalid_lat_long_used);
            }
        } else {
            errorMessage = getString(R.string.service_not_available);
        }
        // Handle case where no address was found.
        if (mAddressList.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
        } else {
            Address address = mAddressList.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(TAG, getString(R.string.address_found));
            deliverResultToReceiver(Constants.SUCCESS_RESULT,
                    TextUtils.join(Objects.requireNonNull(System.getProperty("line.separator")),
                            addressFragments));
        }
    }


    public void getLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            location = null;
        } else {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // First get location from Network Provider
                String providerType;
                if (isNetworkEnabled) {
                    providerType = LocationManager.NETWORK_PROVIDER;
                } else {
                    providerType = LocationManager.GPS_PROVIDER;
                }
                mLocationManager.requestLocationUpdates(providerType, MIN_TIME_TO_UPDATES, MIN_DISTANCE_TO_UPDATE, this);
                location = mLocationManager.getLastKnownLocation(providerType);
            }
        }
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        receiver.send(resultCode, bundle);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: ");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged: ");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "onProviderDisabled: ");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled: ");
    }

}
