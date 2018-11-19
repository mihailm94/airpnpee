package com.appspot.airpeepee.airpeepee;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Location;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.Location;


import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.appspot.airpeepee.airpeepee.model.DataHolder;
import com.appspot.airpeepee.airpeepee.model.Toilet;

import com.appspot.airpeepee.airpeepee.model.MyLocationListener;
import com.appspot.airpeepee.airpeepee.model.db;
import com.google.android.gms.internal.maps.zzw;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.github.zagum.expandicon.ExpandIconView;


import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.google.common.base.Strings.isNullOrEmpty;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private Location mlocation;
    private Marker marker;
    private View mBottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ExpandIconView mExpandIconView;

    private float mSlideOffset = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        MyLocationListener myLocationListener = new MyLocationListener(this);
        mlocation = myLocationListener.getLastBestLocation();

        findViews();
        setUpViews();
        findViewById(R.id.bottom_sheet).setVisibility(View.GONE);
        findViewById(R.id.direction_btn).setVisibility(View.GONE);

    }



    private void findViews() {
        mBottomSheet = findViewById(R.id.bottom_sheet);
        mExpandIconView = (ExpandIconView) mBottomSheet.findViewById(R.id.expandIconView);
    }

    private void setUpViews() {
        mExpandIconView.setState(ExpandIconView.LESS, true);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mExpandIconView.setState(ExpandIconView.LESS, true);
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    mExpandIconView.setState(ExpandIconView.MORE, true);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, final float slideOffset) {
                mSlideOffset = slideOffset;
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        float dis = (mSlideOffset - slideOffset) * 10;
                        if (dis > 1) {
                            dis = 1;
                        } else if (dis < -1) {
                            dis = -1;
                        }
                        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_DRAGGING) {
                            mExpandIconView.setFraction(.5f + dis * .5f, false);
                        }
                    }
                }, 150);
            }
        });
        mBottomSheetBehavior.setPeekHeight((int) convertDpToPixel(140, this));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public DisplayMetrics getDisplayMetrics(Context context) {
        Resources resources = context.getResources();
        return resources.getDisplayMetrics();
    }

    public float convertDpToPixel(float dp, Context context) {
        return dp * (getDisplayMetrics(context).densityDpi / 160f);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        MarkerOptions markerPOI;



        // Toiltes from data to marker
        for (Toilet t : DataHolder.getInstance().getData()) {
            markerPOI = new MarkerOptions();
            markerPOI.position(new LatLng(t.getLocationLat(), t.getLocationLon()))
                    .title(t.getName());

            if(t.isPrivate())
                markerPOI.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            else
                markerPOI.icon(BitmapDescriptorFactory.defaultMarker());

            mMap.addMarker(markerPOI);
        }

        LatLng sydney = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                sydney, 15);
        mMap.animateCamera(location);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Toast.makeText(getContext(),"YOU CLICKED ON "+marker.getTitle(),Toast.LENGTH_LONG).show();
                findViewById(R.id.bottom_sheet).setVisibility(View.VISIBLE);
                findViewById(R.id.direction_btn).setVisibility(View.VISIBLE);
                TextView name =(TextView) findViewById(R.id.toiletName);

                // toilet name zeigen
                if(isNullOrEmpty(marker.getTitle()))
                    name.setText("öffentlicher toilette");
                else
                    name.setText(marker.getTitle());

                return false;
            }
        });
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

    }
}
