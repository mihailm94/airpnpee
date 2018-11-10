package com.appspot.airpeepee.airpeepee;

import android.location.Address;
import android.location.Location;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.Location;



import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;




import com.appspot.airpeepee.airpeepee.model.Toilet;

import com.appspot.airpeepee.airpeepee.model.MyLocationListener;
import com.appspot.airpeepee.airpeepee.model.db;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Marker marker;
    private List<Toilet> toiletList;

    private db databaseToilets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MyLocationListener myLocationListener =new MyLocationListener(this);
        if(myLocationListener.canGetLocation()) {

            double latitude = myLocationListener.getLatitude();
            double longitude = myLocationListener.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            myLocationListener.showSettingsAlert();
        }
        Location location= myLocationListener.getLastBestLocation();
       // System.out.println(location.getLatitude());

        //System.out.println(location.getLatitude());

        databaseToilets = new db();

        MarkerOptions markerPOI;



        // Toiltes from data to marker
        for(Toilet t : databaseToilets.fullToiletList)
        {
            markerPOI = new MarkerOptions();
            markerPOI.position(new LatLng(t.getLocationLat(),t.getLocationLon()))
                    .title(t.getName());

            mMap.addMarker(markerPOI );
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
