package com.appspot.airpeepee.airpeepee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.airpeepee.airpeepee.model.Toilet;
import com.appspot.airpeepee.airpeepee.model.db;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mvc.imagepicker.ImagePicker;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

public class AddActivity extends AppCompatActivity {

    private Toilet toilet =new Toilet();
    private db mDatabase;

    //Photo upload stuff
    private ImageView imageView;
    private TextView textView;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;



    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(52.352552, 13.053786), new LatLng(52.702921, 13.769575));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mDatabase =  new db();

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

        @SuppressLint("WrongViewCast")
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

                Random rnd = new Random();
                int n = 10000000 + rnd.nextInt(90000000);
                toilet.setId(Integer.toString(n));


                toilet.setPhotoUrl(mDatabase.uploadImage(filePath, AddActivity.this));


               if(db.addToilet(toilet))
                   Toast.makeText(getApplicationContext(),"add success",Toast.LENGTH_SHORT).show();
                else
                   Toast.makeText(getApplicationContext(),"add not success",Toast.LENGTH_SHORT).show();

            }
        });

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setHint("adresse");
        autocompleteFragment.setBoundsBias(BOUNDS_MOUNTAIN_VIEW);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

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

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();
            }
        });

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
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
/*
    public void onPickImage(View view) {
        // Click on image button
        //ImagePicker.pickImage(this, "Select your image:");
        //startActivityForResult(chooseImageIntent, 234);
        ImagePicker.pickImage(this);
    }
*/



}
