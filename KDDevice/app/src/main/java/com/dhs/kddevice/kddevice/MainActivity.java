package com.dhs.kddevice.kddevice;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dhs.kddevice.kddevice.util.PreferenceHolder;
import com.dhs.kddevice.kddevice.util.PermissionUtil;
import com.dhs.kddevice.kddevice.util.SpeechUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    public FusedLocationProviderClient mFusedLocationClient;
    // FusedLocationProviderClient is a Google API
    private SpeechUtil speechUtil;
    protected Geocoder geocoder;
    String address;
    String coordinates;
    LinearLayout Main_Layout;
    protected static long lastLocBtnPressTime;

    boolean isGPSEnabled = false;
    LocationManager locationManager;
    private LocationListener gpsLocationListener;

    SharedPreferences pref;

    boolean doubleClick = false;
    double lattitude;
    double longitute;


    // all below is for the setting button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, AllContacts.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        //Main_Layout = (LinearLayout) findViewById(R.id.Main_Layout);
        //setSupportActionBar(toolbar);
        // Save Contact Preferences
        PreferenceHolder contactPref = PreferenceHolder.getInstance();
        contactPref.loadContacts(getApplicationContext());

        pref = PreferenceManager.getDefaultSharedPreferences(this); // 0 - for private mode

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        geocoder = new Geocoder(this, Locale.getDefault());
        speechUtil = new SpeechUtil(getApplicationContext());


        Button AddNumbtn = (Button) findViewById(R.id.AddNumbtn);
        AddNumbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllContacts.class);
                startActivity(intent);
            }});
        Button btnNearByLocation = (Button) findViewById(R.id.Nearbybtn);
        btnNearByLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lattitude > 0) {
                    Intent intent = new Intent(MainActivity.this, LocoNearby.class);
                    intent.putExtra("LATTITUDE", lattitude);
                    intent.putExtra("LONGITUDE", longitute);
                    startActivity(intent);
                }
            }
        });


        final Button btn = (Button) findViewById(R.id.buttonlocation);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                buttonClick();
                if (doubleClick) {
                    Intent intent = new Intent(MainActivity.this, SendSMSActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ADDRESS", getSMSMessage());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Tap again to send SMS...", Toast.LENGTH_SHORT).show();
                    doubleClick = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleClick = false;
                        }
                    }, 3000);
                }
            }
        });
    }

    public void getLocation(Context context) {
        String strLastLocation = null;

        if (PermissionUtil.verifyLocationPermissions(context)) {
            try {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                LocationRequest mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(2000);
                mLocationRequest.setFastestInterval(1000);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
                SettingsClient client = LocationServices.getSettingsClient(this);
                Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

                try {
                    mFusedLocationClient.getLastLocation()
                            .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                                @Override
                                public void onComplete(@NonNull Task<Location> task) {


                                    if (task.isSuccessful() && task.getResult() != null) {
                                        Location mLastLocation = task.getResult();

                                        List<Address> addresses = null;

                                        try {

                                            lattitude = mLastLocation.getLatitude();
                                            longitute = mLastLocation.getLongitude();
                                            coordinates = lattitude + "," + longitute;
                                            addresses = geocoder.getFromLocation(lattitude, longitute, 1);
                                            StringBuffer addressList = new StringBuffer();
                                            for (Address addressObj : addresses) {
                                                try {
                                                    addressList.append(addressObj.getAddressLine(0));
                                                } catch (Exception ex) {
                                                    // If address is not returned, log and skip
                                                    Log.w("KD Device", ex);
                                                    addressList = new StringBuffer(); // Clearing
                                                }
                                            }
                                            if (addressList.length() > 1) {
                                                address = addressList.toString();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();

                                        }
                                        //address = getAddress(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                                    } else {
                                        Log.w("KD Device", "getLastLocation:exception", task.getException());

                                    }

                                    if (address == null) {
                                        ((Button) findViewById(R.id.buttonlocation)).setText("You are at coordinates [" + coordinates + "]. Unable to get address nearby");
                                    } else {
                                        ((Button) findViewById(R.id.buttonlocation)).setText("You are near " + address);
                                    }

                                    speechUtil.speak(address);
                                }
                            });
                } catch (SecurityException seEx) {
                    speechUtil.speak("Exception occured. No permission");
                }
            } catch (SecurityException securityEx) {
                strLastLocation = "Sorry .. I need permission to access location.";
            }
        } else {
            strLastLocation = "Sorry .. I need permission to access location.";
        }

        if (strLastLocation != null) {
            ((Button) findViewById(R.id.buttonlocation)).setText(strLastLocation);
            speechUtil.speak(strLastLocation);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                locationManager = (LocationManager) this.getSystemService(
                        Context.LOCATION_SERVICE);
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (isGPSEnabled) {
                    //getLocation(getApplicationContext());
                } else {
                    Toast.makeText(getApplicationContext(), "Location services are NOT enabled. Enable location services in settings.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
                //locationSearchEditText.setText("");
            }
        }
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("stopPopup", true); // Storing long
        editor.commit();
        return;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!pref.getBoolean("stopPopup", false)) {
            checkPermission();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("stopPopup", false); // Storing long
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("stopPopup", true); // Storing long
        editor.commit();
    }

    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtil.requestLocationPermission(MainActivity.this);
        }
    }

    public void buttonClick() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtil.requestLocationPermission(MainActivity.this);
        } else {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGPSEnabled) {
                getLocation(getApplicationContext());
            } else {
                speechUtil.speak("Location services are disabled. Enable location services in settings.");
                //Toast.makeText(getApplicationContext(), "Location services are disabled. Enable location services in settings.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String getSMSMessage() {
        String msg = "";
        if (address == null) {
            msg = "I am at coordinates [" + coordinates + "].";
        } else {
            msg = "I am near " + address;
        }
        return msg;
    }

}
