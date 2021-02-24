package mobiledev.unb.ca.locationdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "TAG";
    private static final int LOCATION_REQUEST = 101;

    private Button mButton;
    private TextView mTextView;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textview);
        mButton = findViewById(R.id.button);
        mButton.setEnabled(false);

        // Create an instance of Google API Client
        initGoogleApiClient();
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (null != mGoogleApiClient) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (null != mGoogleApiClient) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult: Granted");
                    getCurrentLocation();

                } else {
                    Toast.makeText(this, "onRequestPermissionsResult: Denied", Toast.LENGTH_SHORT);
                    Log.i(TAG, "onRequestPermissionsResult: Denied");
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Connected to Google Play services
        mButton.setEnabled(true);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
        mButton.setEnabled(false);
        Toast.makeText(this, "Connection was suspended", Toast.LENGTH_SHORT);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the 'Handle Connection Failures' section.
        mButton.setEnabled(false);
        Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT);
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    private void getCurrentLocation() {
        // Check to see if the location permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            }, LOCATION_REQUEST);

            Toast.makeText(this, "Location permission check failed", Toast.LENGTH_SHORT);
            Log.i(TAG, "Get location without permissions...");
            return;
        }

        // Fetch location using the FusedLocationProviderAPI
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if (lastLocation != null) {
            String latitude = String.valueOf(lastLocation.getLatitude());
            String longitude = String.valueOf(lastLocation.getLongitude());
            String accuracy = String.valueOf(lastLocation.getAccuracy());

            String text = getString(R.string.location_details, latitude, longitude, accuracy);
            mTextView.setText(text);
        } else {
            mTextView.setText("Unable to fetch the location");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                getCurrentLocation();
                break;
        }
    }
}
