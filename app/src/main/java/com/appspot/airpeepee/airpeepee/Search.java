package com.appspot.airpeepee.airpeepee;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Search extends AppCompatActivity {
    protected GeoDataClient mGeoDataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // TODO: Start using the Places API.
        

    }



}
