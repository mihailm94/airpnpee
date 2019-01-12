package com.appspot.airpeepee.airpeepee;

import com.appspot.airpeepee.airpeepee.model.DB;
import com.appspot.airpeepee.airpeepee.model.PlaceArrayAdapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.airpeepee.airpeepee.model.Toilet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.mvc.imagepicker.ImagePicker;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class AddActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private Toilet toilet =new Toilet();
    private DB mDatabase;

    //Search without google logo
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private AutoCompleteTextView mAutocompleteTextView;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(52.352552, 13.053786), new LatLng(52.702921, 13.769575));



    //Photo upload stuff
    private ImageView imageView;
    private TextView textView;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
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
            toilet.setStreet(place.getName().toString());
            Geocoder geocoder = new Geocoder(AddActivity.this);
            try {

                List<Address> addresses =  geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude,1);
                if (addresses.size()>0)
                {
                    toilet.setStreet(addresses.get(0).getAddressLine(0));
                    //get postal code
                    toilet.setPlz(addresses.get(0).getPostalCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            toilet.setLocationLat(place.getLatLng().latitude);
            toilet.setLocationLon(place.getLatLng().longitude);
            toilet.setTotalRating(place.getRating());
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mDatabase =  new DB();

        ImageView backbutton =(ImageView) findViewById(R.id.shape);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //search without google logo

        mGoogleApiClient = new GoogleApiClient.Builder(AddActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView.setThreshold(2);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);



        //image picking for tha toilet
        imageView = (ImageView) findViewById(R.id.imageView3);
        textView = (TextView) findViewById(R.id.textViewPhoto);
        ImagePicker.setMinQuality(600, 600);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(ImagePicker.getPickImageIntent(this, "Please select your photo"), PICK_IMAGE_REQUEST);
                //ImagePicker.pickImage(AddActivity.this, PICK_IMAGE_REQUEST);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        //@SuppressLint("WrongViewCast")
        View save=(View) findViewById(R.id.button);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView name = (TextView) findViewById(R.id.name);
                TextView des = (TextView) findViewById(R.id.description);
                TextView opening = (TextView) findViewById(R.id.opening_hours);
                Switch fee=(Switch) findViewById(R.id.switch1);
                Switch isprivate=(Switch) findViewById(R.id.switch2);
                Switch wheelchair=(Switch) findViewById(R.id.switch3);

                toilet.setName(name.getText().toString());
                toilet.setDescription(des.getText().toString());
                toilet.setOpeninghours(opening.getText().toString());

                if(fee.isChecked())
                    toilet.setFee("yes");
                else
                    toilet.setFee("no");
                toilet.setPrivate(isprivate.isChecked());

                if(wheelchair.isChecked())
                    toilet.setWheelchair("yes");
                else
                    toilet.setWheelchair("no");

                String n =UUID.randomUUID().toString();
                toilet.setId(n);

                if (filePath ==null)
                {
                    toilet.setPhotoUrl("/images/default.jpg");
                }else {
                    toilet.setPhotoUrl(mDatabase.uploadImage(filePath, getApplicationContext()));
                }

                if(DB.addToilet(toilet)) {
                    Toast.makeText(getApplicationContext(),"add success",Toast.LENGTH_SHORT).show();
                    DB d=new DB();
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),"add not success",Toast.LENGTH_SHORT).show();



            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }



}
