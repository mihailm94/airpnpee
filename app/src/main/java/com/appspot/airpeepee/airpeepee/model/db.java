package com.appspot.airpeepee.airpeepee.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.FirebaseException;
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

    private static  DatabaseReference toiletRef ;
    public db(){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        toiletRef = database.getReference("/toilet");

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
                    boolean isprivate = Boolean.parseBoolean(toiletSnapshot.child("isPrivate").getValue().toString());
                    toiletList.add(new Toilet(id, fee, locationLat, locationLon, name, openingHours, plz, street, streetNo, wheelchair,isprivate));
                }

                DataHolder.getInstance().setData(toiletList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });

    }

    public static boolean addToilet(Toilet toilet)
    {
        try {
            toiletRef.child(toilet.id).push();
            toiletRef.child(toilet.id).child("name").setValue(toilet.getName());
            toiletRef.child(toilet.id).child("fee").setValue(toilet.isFee());
            toiletRef.child(toilet.id).child("description").setValue(toilet.getDescription());
            toiletRef.child(toilet.id).child("isPrivate").setValue(toilet.isPrivate());
            toiletRef.child(toilet.id).child("location").child("lat").setValue(toilet.getLocationLat());
            toiletRef.child(toilet.id).child("location").child("lon").setValue(toilet.getLocationLon());
            toiletRef.child(toilet.id).child("opening_hours").setValue(toilet.getOpeninghours());
            toiletRef.child(toilet.id).child("wheelchair").setValue(toilet.isWheelchair());
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }

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
