package com.example.locationservice;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.example.locationservice.Constants.RECEIVER;
import static com.example.locationservice.Constants.REQUEST_CODE_PERMISSION;

public class MainActivity extends AppCompatActivity {
    private static final String[] mPermission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private AddressResultReceiver mResultReceiver;
    private TextView addressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressTextView = findViewById(R.id.address_text_view);
        mResultReceiver = new AddressResultReceiver(new Handler());
        requestLocationPermission();
    }

    private void requestLocationPermission() {
        if (!checkIfPermissionAllowed()) {
            ActivityCompat.requestPermissions(this, mPermission, REQUEST_CODE_PERMISSION);
        }
    }

    private boolean checkIfPermissionAllowed() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED);
    }

    public void getLocation(View view) {
        Intent intent = new Intent(this, LocationService.class);
        intent.putExtra(RECEIVER, mResultReceiver);
        startService(intent);
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultData == null) {
                addressTextView.setText(getText(R.string.no_address_found));
                return;
            }

            // Display the address string
            // or an error message sent from the intent service.
            String addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            if (addressOutput == null) {
                addressTextView.setText(getText(R.string.no_address_found));
            } else {
                addressTextView.setText(addressOutput);
            }

        }
    }
}
