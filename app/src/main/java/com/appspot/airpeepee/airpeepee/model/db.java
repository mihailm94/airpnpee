package com.appspot.airpeepee.airpeepee.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class db {



    public db(){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference toiletRef = database.getReference("/toilet");

        // Attach a SINGLE READ listener to read the data at our posts reference
        toiletRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // declare the list inside onDataChange because the function fires asynchronously
                List<Toilet> toiletList = new ArrayList<>();

                for(DataSnapshot toiletSnapshot : dataSnapshot.getChildren()) {
                    String id = (String) toiletSnapshot.getKey();
                    String fee = (String) toiletSnapshot.child("fee").getValue();
                    String name = (String) toiletSnapshot.child("name").getValue();
                    String openingHours = (String) toiletSnapshot.child("opening_hours").getValue();
                    String plz = (String) toiletSnapshot.child("plz").getValue();
                    String street = (String) toiletSnapshot.child("street").getValue();
                    String streetNo = (String) toiletSnapshot.child("street_number").getValue();
                    String wheelchair = (String) toiletSnapshot.child("wheelchair").getValue();
                    double locationLat = (double) toiletSnapshot.child("location").child("lat").getValue();
                    double locationLon = (double) toiletSnapshot.child("location").child("lon").getValue();

                    //new Toilet(id, fee, locationLat, locationLon, name, openingHours, plz, street, streetNo, wheelchair);

                    toiletList.add(new Toilet(id, fee, locationLat, locationLon, name, openingHours, plz, street, streetNo, wheelchair));

                }

                System.out.print(toiletList.size());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });



        // First Query all Toilette with name "City Toilette"
        //Query mQueryRef = ref.orderByChild("name").equalTo("City Toilette");

        /*
        mQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getChildrenCount()); // just 136 toilet with name "City Toilette"

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */

        };



    public db(String toilet, String plz) {

        // Get a reference to our toilets
        final FirebaseDatabase firedb = FirebaseDatabase.getInstance();
        DatabaseReference toiletRef = firedb.getReference("/toilet");

        /* Attach a listener to read the data at our posts reference */
        toiletRef.addValueEventListener(new

              ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      Toilet toilet = dataSnapshot.getValue(Toilet.class);

                      //Log.d("toilet:", String.valueOf(toilet));
                      System.out.println(toilet);
                      //System.out.println(dataSnapshot.getKey()); //das ist die Toilet collection (table toilet)
                      //System.out.println(dataSnapshot.getValue());  // das ist das Value von das Table. In diesem Fall - das ganze JSON

                      //Log.d("db:", toile.toString());
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {
                      System.out.println("The read failed: " + databaseError.getCode());
                  }
              });

    }

}
