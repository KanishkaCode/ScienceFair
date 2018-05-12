package com.dhs.kddevice.kddevice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dhs.kddevice.kddevice.util.GetNearbyPlacesData;

public class LocoNearby extends AppCompatActivity {


    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loco_nearby);
        latitude = getIntent().getDoubleExtra("LATTITUDE", 0.0);
        longitude = getIntent().getDoubleExtra("LONGITUDE", 0.0);

        Button btnGasStation = (Button) findViewById(R.id.GasStationbtn);
        btnGasStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (latitude > 0) {
                getLocation("gas_station");
            }
            }
        });

        Button btnRestaurant = (Button) findViewById(R.id.Restaurantsbtn);
        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latitude > 0) {
                    getLocation("restaurant");
                }
            }
        });

        Button btnHospital = (Button) findViewById(R.id.Hospitalbtn);
        btnHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latitude > 0) {
                    getLocation("hospital");
                }
            }
        });


    }

    private void getLocation (String locationType) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=");
        googlePlacesUrl.append(latitude);
        googlePlacesUrl.append(",");
        googlePlacesUrl.append(longitude);
        googlePlacesUrl.append("&radius=");
        googlePlacesUrl.append(5000);
        googlePlacesUrl.append("&types=");
        googlePlacesUrl.append(locationType);
        googlePlacesUrl.append("&key=");
        googlePlacesUrl.append("AIzaSyBAyyDukKPv2SMrZTptNw0vzxKU2C3RAEE");
        //-33.8670,151.1957&radius=500&types=food&name=cruise&key=YOUR_API_KEY

        new GetNearbyPlacesData().execute(googlePlacesUrl.toString());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException iEx ) {
            // Skip the exceptions
        }
        Intent intent = new Intent(LocoNearby.this, LocationListActivty.class);
        intent.putExtra("Title", locationType);

        startActivity(intent);
    }



}
