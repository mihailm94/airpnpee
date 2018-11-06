package com.appspot.airpeepee.airpeepee.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class db {

    public String toilet;
    public String plz;

    public db(){};

    public db(String toilet, String plz) {

        // Get a reference to our posts
        final FirebaseDatabase firedb = FirebaseDatabase.getInstance();
        DatabaseReference ref = firedb.getReference("/toilet");

        /* Attach a listener to read the data at our posts reference */
        ref.addValueEventListener(new

              ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      db database = dataSnapshot.getValue(db.class);

                      System.out.println(dataSnapshot.getKey()); //das ist die Toilet collection (table toilet)
                      System.out.println(dataSnapshot.getValue());  // das ist das Value von das Table. In diesem Fall - das ganze JSON

                      Log.d("db:", database.toString());
                  }


                  @Override
                  public void onCancelled(DatabaseError databaseError) {
                      System.out.println("The read failed: " + databaseError.getCode());
                  }
              });

    }
}
