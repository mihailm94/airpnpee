package com.appspot.airpeepee.airpeepee;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.appspot.airpeepee.airpeepee.model.CommentAdapter;
import com.appspot.airpeepee.airpeepee.model.DB;
import com.appspot.airpeepee.airpeepee.model.GlideApp;
import com.appspot.airpeepee.airpeepee.model.PlaceArrayAdapter;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.appspot.airpeepee.airpeepee.model.DataHolder;
import com.appspot.airpeepee.airpeepee.model.Toilet;
import com.appspot.airpeepee.airpeepee.model.MyLocationListener;
import com.appspot.airpeepee.airpeepee.model.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
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
import com.github.zagum.expandicon.ExpandIconView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.math.BigDecimal;
import java.util.ArrayList;
import android.content.Intent;


import static com.appspot.airpeepee.airpeepee.R.*;
import static com.google.common.base.Strings.isNullOrEmpty;


public class MapsActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks ,
        GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, DirectionCallback ,
EditToiletActivity.NoticeDialogListener , AddReviewActivity.NoticeDialogListener
{

    Menu optionMenu;

    // error handler
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private  Thread thread;
    DB database ;
    String TAG;



    private boolean mendtrip=true;
    private GoogleMap mMap;
    // User Location
    private Location mlocation;
    //Toilette
    private Marker marker;
    private Marker m_marker;
    private View mBottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ExpandIconView mExpandIconView;

    private float mSlideOffset = 0;

    //Direction
    private String serverKey = "AIzaSyCwG-ebJNdh97djEIizZFmMw_FlowuaMGs";
    private LatLng origin;
    private LatLng destination;
    private FloatingActionButton btnRequestDirection;
    MyLocationListener myLocationListener;


    //Serach
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private AutoCompleteTextView mAutocompleteTextView;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private boolean isSearch=false;

    //Comment
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseAuth mAuth;



    public boolean isOnline() {
        try {
            ConnectivityManager cm =
                    (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            return isConnected;
        } catch (NetworkOnMainThreadException e) {
            return false; }
    }


    @SuppressLint("RestrictedApi")
    public void visibleAll()
    {

        FloatingActionButton mfab =(FloatingActionButton) findViewById(id.floatingActionButton);
        mfab.setVisibility(View.VISIBLE);
        findViewById(id.map).setVisibility(View.VISIBLE);
        findViewById(id.imageView5).setEnabled(true);
        findViewById(id.map).setEnabled(true);
        findViewById(id.toolbar).setVisibility(View.VISIBLE);
        mapReady(m_google_map);

    }


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_maps);


        //error handler
        if(isOnline()) {
            progressBar = (ProgressBar) findViewById(id.progressBar);
            thread = new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < 100) {
                        progressStatus += 30;
                        // Update the progress bar and display the
                        //current value in the text view
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressStatus);
                                database = new DB();
                                if (progressStatus > 100) {
                                    visibleAll();
                                    progressBar.setVisibility(View.GONE);
                                    thread.interrupt();
                                }
                            }
                        });
                        try {
                            // Sleep for 200 milliseconds.
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();

        }
        else
        {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                alertDialog.show();
            } catch (Exception e) {
                Log.d(this.TAG, "Show Dialog: " + e.getMessage());
            }
        }



        findViewById(id.Kilometers).setVisibility(View.GONE);




        //filter
        final FloatingActionButton mfaball =(FloatingActionButton) findViewById(id.filter_all);
        final FloatingActionButton mfabfee =(FloatingActionButton) findViewById(id.filter_fee);
        final FloatingActionButton mfabwheelchair =(FloatingActionButton) findViewById(id.filter_wheelchair);
        final FloatingActionButton mfab =(FloatingActionButton) findViewById(id.floatingActionButton);
        final LinearLayout linearLayout =(LinearLayout) findViewById(id.filter_layout);
        final Animation mShowButton =AnimationUtils.loadAnimation(MapsActivity.this,anim.show_button);
        final Animation mhideButton =AnimationUtils.loadAnimation(MapsActivity.this,anim.hide_button);
        final Animation mShowlayout =AnimationUtils.loadAnimation(MapsActivity.this,anim.show_layout);
        final Animation mhidelayout =AnimationUtils.loadAnimation(MapsActivity.this,anim.hide_layout);

        mfab.setVisibility(View.GONE);
        mfaball.setVisibility(View.GONE);
        mfabfee.setVisibility(View.GONE);
        mfabwheelchair.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);

        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayout.getVisibility() == View.VISIBLE)
                {   linearLayout.startAnimation(mhidelayout);
                    linearLayout.setVisibility(View.GONE);
                    mfabwheelchair.setVisibility(View.GONE);
                    mfaball.setVisibility(View.GONE);
                    mfabfee.setVisibility(View.GONE);

                    mfab.startAnimation(mhideButton);
                }
                else
                {  linearLayout.setVisibility(View.VISIBLE);
                    mfabwheelchair.setVisibility(View.VISIBLE);
                    mfaball.setVisibility(View.VISIBLE);
                    mfabfee.setVisibility(View.VISIBLE);
                    linearLayout.startAnimation(mShowlayout);
                    mfab.startAnimation(mShowButton);}
            }
        });

        mfaball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshMarker();
            }
        });
        mfabfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshMarker(true);
            }
        });
        mfabwheelchair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshMarker(false);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        toolbar.setTitle("");
        toolbar.setVisibility(View.GONE);
         setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, string.navigation_drawer_open, string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Search
        mGoogleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(id.search_view);
        mAutocompleteTextView.setThreshold(2);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);



        btnRequestDirection = findViewById(id.direction_btn);
        btnRequestDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mlocation = myLocationListener.getLastBestLocation();
                mendtrip =false;
                findViewById(id.imageView5).setVisibility(View.VISIBLE);
                findViewById(id.floatingActionButton).setVisibility(View.GONE);
                findViewById(id.filter_all).setVisibility(View.GONE);
                findViewById(id.filter_fee).setVisibility(View.GONE);
                findViewById(id.filter_wheelchair).setVisibility(View.GONE);
                findViewById(id.Kilometers).setVisibility(View.VISIBLE);
                findViewById(id.Kilometers).setPadding(0,0,0,110);
                origin = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
                destination = m_marker.getPosition();

                requestDirection();
            }
        });

        Button edit_toilet =(Button) findViewById(id.edit_toilet);
        edit_toilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoticeDialog();
            }
        });

        ImageView cancel= findViewById(id.imageView5);
        cancel.setEnabled(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mendtrip=true;
                findViewById(id.bottom_sheet).setVisibility(View.GONE);
                findViewById(id.imageView5).setVisibility(View.GONE);
                findViewById(id.Kilometers).setVisibility(View.GONE);
                findViewById(id.floatingActionButton).setVisibility(View.VISIBLE);
                refreshMarker();
            }
        });

        Button review_toilet = (Button) findViewById(id.review_toilet);
        review_toilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataHolder.getInstance().getUser() != null){
                    showNoticeDialogReview();

                } else {
                    startActivity(new Intent(MapsActivity.this, LoginActivity.class));

                }
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(id.map);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        myLocationListener = new MyLocationListener(this);
        mlocation = myLocationListener.getLastBestLocation();
        if (mlocation == null)
            mlocation = myLocationListener.currentBestLocation;
        findViews();
        setUpViews();
        findViewById(id.bottom_sheet).setVisibility(View.GONE);
        findViewById(id.direction_btn).setVisibility(View.GONE);
        findViewById(id.ic_euro).setVisibility(View.GONE);
        findViewById(id.ic_wheelchair).setVisibility(View.GONE);
        findViewById(id.ic_out_of_order).setVisibility(View.GONE);
        findViewById(id.imageView5).setVisibility(View.GONE);
        findViewById(id.map).setEnabled(false);
        findViewById(id.map).setVisibility(View.GONE);
    }




    private void findViews() {
        mBottomSheet = findViewById(id.bottom_sheet);
        mExpandIconView = (ExpandIconView) mBottomSheet.findViewById(id.expandIconView);
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
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        m_google_map =googleMap;
    }
    GoogleMap m_google_map;

    public void mapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        MarkerOptions markerPOI;


        if ( DataHolder.getInstance().getData()!=null) {
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
        }
        if(mlocation != null) {
            LatLng sydney = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());

            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    sydney, 15);

            mMap.animateCamera(location);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            this.recreate();
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if(!mendtrip && !isSearch)
                {
                    origin = new LatLng(location.getLatitude(), location.getLongitude());
                    if(destination !=null) {
                        Location locationOne = new Location("");
                        locationOne.setLatitude(destination.latitude);
                        locationOne.setLongitude(destination.longitude);
                        float distanceInMetersOne = location.distanceTo(locationOne);

                        BigDecimal result;
                        result = round(distanceInMetersOne/1000 ,2);
                        BigDecimal time = round((float) ((distanceInMetersOne/1000)*16.7),0);
                        String result1 = time+" min"+ " ("+result+" km)";
                        ((TextView)findViewById(id.Kilometers)).setText(result1);

                        if (distanceInMetersOne <30) {
                            //destination = null;
                            mendtrip=true;
                            findViewById(id.bottom_sheet).setVisibility(View.GONE);
                            findViewById(id.floatingActionButton).setVisibility(View.VISIBLE);
                            refreshMarker();
                            if (DataHolder.getInstance().getUser()!= null) {
                                DialogFragment dialog = new AddReviewActivity(m_marker.getPosition());
                                dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                            }
                            return;
                        }
                        requestDirection();
                    }
                }

            }
        });
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                m_marker=marker;
                findViewById(id.bottom_sheet).setVisibility(View.VISIBLE);
                findViewById(id.direction_btn).setVisibility(View.VISIBLE);
                putToiletInfo(marker);
                return false;
            }
        });



    }

    public void putToiletInfo(Marker marker) {
        TextView name = (TextView) findViewById(id.toiletName);
        TextView type = (TextView) findViewById(id.toilet_type);
        TextView totalrating = (TextView) findViewById(id.reviews);
        TextView cost=findViewById(id.view_cost);
        ImageView imageView = (ImageView) findViewById(id.imageView2);
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
        String total="";

        if(toilet.getCost()==0.0)
        {
            cost.setText("");
        }
        else
        cost.setText("("+toilet.getCost()+" €)");


        if((int)toilet.getTotalRating()==0)
        {
            total = "Reviews : " + "" + " ("+toilet.getRatings().size()+") ";
        }
        if((int)toilet.getTotalRating()==1){
            total = "Reviews : " + "\uD83D\uDCA6" + " ("+toilet.getRatings().size()+") ";
        }
        if((int)toilet.getTotalRating()==2){
            total = "Reviews : " + "\uD83D\uDCA6\uD83D\uDCA6" + " ("+toilet.getRatings().size()+") ";
        }
        if((int)toilet.getTotalRating()==3){
            total = "Reviews : " + "\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6" + " ("+toilet.getRatings().size()+") ";
        }
        if((int)toilet.getTotalRating()==4){
            total = "Reviews : " + "\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6" + " ("+toilet.getRatings().size()+") ";
        }
        if((int)toilet.getTotalRating()==5){
            total = "Reviews : " + "\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6" + " ("+toilet.getRatings().size()+") ";
        }
       totalrating.setText(total);

        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(toilet.getPhotoUrl());


        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(this /* context */)
                .load(storageReference)
                .into(imageView);

        TextView information=(TextView) findViewById(id.information);
        information.setText(toilet.getDescription());

        if(toilet.isOutoforder())
        {
            if( findViewById(id.ic_out_of_order).getVisibility()==View.GONE)
                findViewById(id.ic_out_of_order).setVisibility(View.VISIBLE);
        }
        else
        {
            if( findViewById(id.ic_out_of_order).getVisibility()==View.VISIBLE)
                findViewById(id.ic_out_of_order).setVisibility(View.GONE);
        }


        if(toilet.isFee()==null) {
            if (((ImageView) findViewById(id.ic_euro)).getVisibility() == View.VISIBLE)
                ((ImageView) findViewById(id.ic_euro)).setVisibility(View.GONE);
        }
        else{
        if(toilet.isFee().equals("yes")) {
            if(((ImageView) findViewById(id.ic_euro)).getVisibility() == View.GONE)
                 ((ImageView) findViewById(id.ic_euro)).setVisibility(View.VISIBLE);
        }
            if (toilet.isFee().equals("no")) {
                if (((ImageView) findViewById(id.ic_euro)).getVisibility() == View.VISIBLE)
                    ((ImageView) findViewById(id.ic_euro)).setVisibility(View.GONE);
            }


        }

        if(toilet.isWheelchair() == null){
            if(((ImageView) findViewById(id.ic_wheelchair)).getVisibility() == View.VISIBLE)
                ((ImageView) findViewById(id.ic_wheelchair)).setVisibility(View.GONE);

        }else{
        if(toilet.isWheelchair().equals("yes")) {
            if(((ImageView) findViewById(id.ic_wheelchair)).getVisibility() == View.GONE)
                ((ImageView) findViewById(id.ic_wheelchair)).setVisibility(View.VISIBLE);

        }
            if (toilet.isWheelchair().equals("no")) {
                if (((ImageView) findViewById(id.ic_wheelchair)).getVisibility() == View.VISIBLE)
                    ((ImageView) findViewById(id.ic_wheelchair)).setVisibility(View.GONE);
            }

        }

        mRecyclerView = (RecyclerView) findViewById(id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MapsActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CommentAdapter(toilet.getComments());
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        mlocation = myLocationListener.getLastBestLocation();
        isSearch =false;
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
        //Snackbar.make(btnRequestDirection, "Direction Requesting...", Snackbar.LENGTH_SHORT).show();
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .alternativeRoute(true)
                .transportMode(TransportMode.WALKING)
                .transitMode(TransportMode.WALKING)
                .execute(this);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        //Snackbar.make(btnRequestDirection, "Success with status: " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        if(direction.isOK()){
            mMap.clear();
          //  refreshMarker();
            Route route = direction.getRouteList().get(0);
            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
            mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, 0xFFF16232));
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        // lOGIN
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        optionMenu =navigationView.getMenu();
        if (currentUser != null) {
            DataHolder.getInstance().setUser(new User());
            DB.findUserbyemail(currentUser.getEmail());
            DataHolder.getInstance().getUser().setFirebaseUser(currentUser);

            // error fix me

            MenuItem item  =  optionMenu.findItem(id.nav_statistic);
            item.setTitle("Log out " +DataHolder.getInstance().getUser().getFirstname() );
        }
        else{
            MenuItem item  =  optionMenu.findItem(id.nav_statistic);
           item.setTitle("Log in");

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MapsActivity.this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            if(DataHolder.getInstance().getUser() != null) {
                startActivity(new Intent(MapsActivity.this, EditProfileActivity.class));
            }
            else
            {
                startActivity(new Intent(MapsActivity.this, LoginActivity.class));
            }

            // Handle the profile action
        } else if (id == R.id.nav_privToilet) {
            if(DataHolder.getInstance().getUser() != null) {
                if(DataHolder.getInstance().getUser().isAnbieter())
                    startActivity(new Intent(MapsActivity.this, AddActivity.class));
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Info");
                    alertDialog.setMessage("The Current User is not a provider you can change in Profile Edit");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

                    alertDialog.show();
                }
            }
            else
            {
                startActivity(new Intent(MapsActivity.this, LoginActivity.class));
            }

        } else if (id == R.id.nav_addCoupon) {
            if(DataHolder.getInstance().getUser() != null) {
                if(DataHolder.getInstance().getUser().isAnbieter())
                startActivity(new Intent(MapsActivity.this, EditProfileActivity.class));
            }
            else
            {
                startActivity(new Intent(MapsActivity.this, LoginActivity.class));
            }

        } else if (id == R.id.nav_statistic) {
            if(DataHolder.getInstance().getUser() != null) {
                startActivity(new Intent(MapsActivity.this, LoginActivity.class));
            }
            else
            {
                startActivity(new Intent(MapsActivity.this, LoginActivity.class));
            }

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(MapsActivity.this,SettingsActivity.class));

        } else if (id == R.id.nav_notification) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i("Location", "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i("Location", "Fetching details for ID: " + item.placeId);




        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e("Location", "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            LatLng sydney = place.getLatLng();
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    sydney, 15);
            mMap.animateCamera(location);
            mlocation.setLatitude(sydney.latitude);
            mlocation.setLongitude(sydney.longitude);
            isSearch=true;
            Log.i("name", place.getName().toString());
            Log.i("coordinates", place.getLatLng().toString());
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i("Location", "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("Location", "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e("Location", "Google Places API connection suspended.");
    }

    public void showNoticeDialog() {
    // Create an instance of the dialog fragment and show it
        if(DataHolder.getInstance().getUser() != null) {
            DialogFragment dialog = new EditToiletActivity(m_marker.getPosition());
            dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
        }
        else
        {
            startActivity(new Intent(MapsActivity.this, LoginActivity.class));
        }

}
    public void showNoticeDialogReview() {
        // Create an instance of the dialog fragment and show it
        if(DataHolder.getInstance().getUser() != null) {
            DialogFragment dialog = new AddReviewActivity(m_marker.getPosition());
            dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
        }
        else
        {
            startActivity(new Intent(MapsActivity.this, LoginActivity.class));
        }

    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button

    }
    public void refreshMarker() {
        mMap.clear();
        MarkerOptions markerPOI;
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
    }
    public void refreshMarker(boolean fee) {
        mMap.clear();
        MarkerOptions markerPOI;
        for (Toilet t : DataHolder.getInstance().getData()) {
            markerPOI = new MarkerOptions();
            markerPOI.position(new LatLng(t.getLocationLat(), t.getLocationLon()))
                    .title(t.getName());
            if(fee) {
                if(t.isFee()!=null){
                if(t.isFee().equals("yes")) {
                    if (t.isPrivate())
                        markerPOI.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    else
                        markerPOI.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }
                }
            }else
            {
                if(t.isWheelchair()!=null) {
                    if (t.isWheelchair().equals("yes")) {
                        if (t.isPrivate())
                            markerPOI.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        else
                            markerPOI.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                }
            }
            mMap.addMarker(markerPOI);
        }
    }
    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

}