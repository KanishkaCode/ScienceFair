package com.dhs.kddevice.kddevice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dhs.kddevice.kddevice.util.PreferenceHolder;

public class LocationListActivty extends AppCompatActivity {

    RecyclerView rvLocationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        PreferenceHolder holder = PreferenceHolder.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list_activty);

        rvLocationList = (RecyclerView) findViewById(R.id.rvLocationListView);

        long startTs = System.currentTimeMillis();

        while (holder.getLocationsList() == null || holder.getLocationsList().isEmpty()) {
            if (System.currentTimeMillis() - startTs > 5000) {
                Toast.makeText(getApplicationContext(), "Try Again. Unable to read near By locations in 5 Seconds", Toast.LENGTH_SHORT).show();
            }
        }

        LocationListAdaptor locationListAdapter = new LocationListAdaptor(getApplicationContext());
        rvLocationList.setLayoutManager(new LinearLayoutManager(this));
        rvLocationList.setAdapter(locationListAdapter);


        Button btn = (Button) findViewById(R.id.btnOkLocation);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PreferenceHolder.getInstance().getLocationsList().clear();
                finish();
            }
        });

    }


}
