package com.appspot.airpeepee.airpeepee;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.Location;


import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
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
import com.google.android.gms.maps.model.LatLngBounds;
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


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, DirectionCallback{

    private GoogleMap mMap;
    // User Location
    private Location mlocation;
    //Toilette
    private Marker marker;
    private View mBottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ExpandIconView mExpandIconView;

    private float mSlideOffset = 0;

    //Direction
    private String serverKey = "AIzaSyCwG-ebJNdh97djEIizZFmMw_FlowuaMGs";
    private LatLng origin;
    private LatLng destination;
    private FloatingActionButton btnRequestDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btnRequestDirection = findViewById(R.id.direction_btn);
        btnRequestDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                origin = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
                requestDirection();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        MyLocationListener myLocationListener = new MyLocationListener(this);
        mlocation = myLocationListener.getLastBestLocation();
        if (mlocation == null)
            mlocation = myLocationListener.currentBestLocation;
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

            if (t.isPrivate())
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
                destination = marker.getPosition();
                findViewById(R.id.bottom_sheet).setVisibility(View.VISIBLE);
                findViewById(R.id.direction_btn).setVisibility(View.VISIBLE);
                putToiletInfo(marker);
                return false;
            }
        });

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void putToiletInfo(Marker marker) {
        TextView name = (TextView) findViewById(R.id.toiletName);
        TextView type = (TextView) findViewById(R.id.toilet_type);
        TextView totalrating = (TextView) findViewById(R.id.reviews);
        // toilet name zeigen
        if (isNullOrEmpty(marker.getTitle()))
            name.setText("öffentlicher toilette");
        else
            name.setText(marker.getTitle());
        Toilet toilet = DataHolder.getInstance().findToiletbyLatLng(marker.getPosition());
        if (toilet.isPrivate())
            type.setText("Private Toilet");
        else
            type.setText("Public Toilet");

        totalrating.setText("Reviews : " + toilet.getTotalRating());

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

    //--------------------------------------------
    //Direction
    //--------------------------------------------
  /*  @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.direction_btn){
            requestDirection();
        }
    }
*/
    public void requestDirection() {
        Snackbar.make(btnRequestDirection, "Direction Requesting...", Snackbar.LENGTH_SHORT).show();
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transitMode(TransportMode.WALKING)
                .execute(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        Snackbar.make(btnRequestDirection, "Success with status: " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        if(direction.isOK()){
            Route route = direction.getRouteList().get(0);
            mMap.addMarker(new MarkerOptions().position(origin));
            mMap.addMarker(new MarkerOptions().position(destination));

            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
            mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.RED));
            setCameraWithCoordinationBounds(route);

            btnRequestDirection.setVisibility(View.GONE);
        } else {
            Snackbar.make(btnRequestDirection, direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Snackbar.make(btnRequestDirection, t.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    public void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }


}