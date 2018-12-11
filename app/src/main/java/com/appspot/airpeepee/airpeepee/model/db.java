package com.appspot.airpeepee.airpeepee.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appspot.airpeepee.airpeepee.AddActivity;
import com.appspot.airpeepee.airpeepee.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.firebase.ui.storage.images.*;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class db {

    private static DatabaseReference toiletRef ;
    private static DatabaseReference userRef ;
    private static StorageReference storageRef;


    public db(){

        //db ref
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //db storage ref
        final FirebaseStorage databaseStorage = FirebaseStorage.getInstance();

        toiletRef = database.getReference("/toilet");
        userRef   = database.getReference("/user");
        storageRef = databaseStorage.getReference();

        // Attach a SINGLE READ listener to read the data at our posts reference
        toiletRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // declare the list inside onDataChange because the function fires asynchronously
                List<Toilet> toiletList = new ArrayList<>();
                for(DataSnapshot toiletSnapshot : dataSnapshot.getChildren()) {
                    String id =toiletSnapshot.getKey();
                    String fee = (String) toiletSnapshot.child("fee").getValue();
                    String name = (String) toiletSnapshot.child("name").getValue();
                    String openingHours = (String) toiletSnapshot.child("opening_hours").getValue();
                    String plz = (String) toiletSnapshot.child("plz").getValue();
                    String street = (String) toiletSnapshot.child("street").getValue();
                    String streetNo = (String) toiletSnapshot.child("street_number").getValue();
                    String wheelchair = (String) toiletSnapshot.child("wheelchair").getValue();
                    double locationLat = (double) toiletSnapshot.child("location").child("lat").getValue();
                    double locationLon = (double) toiletSnapshot.child("location").child("lon").getValue();
                    boolean isPrivate = Boolean.parseBoolean(toiletSnapshot.child("isPrivate").getValue().toString());
                    boolean isoutoforder=false;
                    if(toiletSnapshot.child("out_of_order").getValue() != null ) {
                        isoutoforder = Boolean.parseBoolean(toiletSnapshot.child("out_of_order").getValue().toString());
                    }
                    String photourl = (String) toiletSnapshot.child("photoUrl").getValue();
                    String description =(String) toiletSnapshot.child("description").getValue();
                    double totalRating = 0;//Double.parseDouble(toiletSnapshot.child("ratingTotal").getValue().toString());
                    Toilet temp =new Toilet(id, fee, locationLat, locationLon, name, openingHours, plz, street, streetNo, wheelchair,isPrivate);
                    for(DataSnapshot commentSnapshot : toiletSnapshot.child("comments").getChildren())
                    {
                        if(!commentSnapshot.child("commentText").getValue().toString().equals(" "))
                            temp.getComments().add(new Comment(commentSnapshot.child("commentUserID").getValue().toString(),commentSnapshot.child("commentText").getValue().toString()));
                    }
                    for(DataSnapshot ratingSnapshot : toiletSnapshot.child("rating").getChildren())
                    {
                        if(!ratingSnapshot.child("userRating").getValue().toString().equals(" ")) {
                            temp.getRatings().add(new Rating(ratingSnapshot.child("userID").getValue().toString(), Integer.parseInt(ratingSnapshot.child("userRating").getValue().toString())));
                            totalRating += Double.parseDouble(ratingSnapshot.child("userRating").getValue().toString());
                        }
                    }
                    if(toiletSnapshot.child("cost").getValue() != null) {
                        double cost = Double.parseDouble(toiletSnapshot.child("cost").getValue().toString());
                        temp.setCost(cost);
                    }
                    temp.setPhotoUrl(photourl);
                    temp.setDescription(description);
                    if( temp.getRatings().size() !=0)
                        totalRating=totalRating/temp.getRatings().size();
                    temp.setTotalRating(totalRating);
                    temp.setOutoforder(isoutoforder);
                    toiletList.add(temp);
                }

                DataHolder.getInstance().setData(toiletList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });

    }

    public static boolean addUser(String email,String password)
    {
        try {
            String n = UUID.randomUUID().toString();
            userRef.child(n).push();
            userRef.child(n).child("email").setValue(email);
            userRef.child(n).child("password").setValue(password);
            userRef.child(n).child("username").setValue("user_"+n);
            userRef.child(n).child("isAnbieter").setValue(false);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }




    public static boolean addToilet(Toilet toilet)
    {
        try {
            toiletRef.child(toilet.id).push();
            toiletRef.child(toilet.id).child("userId").setValue(DataHolder.getInstance().getUser().getId());
            toiletRef.child(toilet.id).child("name").setValue(toilet.getName());
            toiletRef.child(toilet.id).child("fee").setValue(toilet.isFee());
            toiletRef.child(toilet.id).child("description").setValue(toilet.getDescription());
            toiletRef.child(toilet.id).child("isPrivate").setValue(toilet.isPrivate());
            toiletRef.child(toilet.id).child("location").child("lat").setValue(toilet.getLocationLat());
            toiletRef.child(toilet.id).child("location").child("lon").setValue(toilet.getLocationLon());
            toiletRef.child(toilet.id).child("opening_hours").setValue(toilet.getOpeninghours());
            toiletRef.child(toilet.id).child("wheelchair").setValue(toilet.isWheelchair());
            toiletRef.child(toilet.id).child("ratingTotal").setValue(toilet.getTotalRating());
            toiletRef.child(toilet.id).child("street").setValue(toilet.getStreet());
            toiletRef.child(toilet.id).child("plz").setValue(toilet.getPlz());
            toiletRef.child(toilet.id).child("photoUrl").setValue(toilet.getPhotoUrl());
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }

    public static boolean updateData()
    {

        toiletRef.orderByChild("name");
        return true;
    }

    public static boolean editToilet(Toilet toilet)
    {
        try{
        toiletRef.child(toilet.id).child("name").setValue(toilet.getName());
        toiletRef.child(toilet.id).child("fee").setValue(toilet.isFee());
        toiletRef.child(toilet.id).child("description").setValue(toilet.getDescription());
        toiletRef.child(toilet.id).child("opening_hours").setValue(toilet.getOpeninghours());
        toiletRef.child(toilet.id).child("wheelchair").setValue(toilet.isWheelchair());
        toiletRef.child(toilet.id).child("photoUrl").setValue(toilet.getPhotoUrl());
        toiletRef.child(toilet.id).child("out_of_order").setValue(toilet.isOutoforder());
        toiletRef.child(toilet.id).child("cost").setValue(toilet.getCost());
        if(toilet.getComments().size() !=0) {
            toiletRef.child(toilet.id).child("comments").child(Integer.toString(toilet.getComments().size() - 1)).push();
            toiletRef.child(toilet.id).child("comments").child(Integer.toString(toilet.getComments().size() - 1)).child("commentUserID").setValue(toilet.getComments().get(toilet.getComments().size() - 1).getId());
            toiletRef.child(toilet.id).child("comments").child(Integer.toString(toilet.getComments().size() - 1)).child("commentText").setValue(toilet.getComments().get(toilet.getComments().size() - 1).getCommentText());
        }if(toilet.getRatings().size() != 0) {
                toiletRef.child(toilet.id).child("rating").child(Integer.toString(toilet.getRatings().size() - 1)).push();
                toiletRef.child(toilet.id).child("rating").child(Integer.toString(toilet.getRatings().size() - 1)).child("userID").setValue(toilet.getRatings().get(toilet.getRatings().size() - 1).getId());
                toiletRef.child(toilet.id).child("rating").child(Integer.toString(toilet.getRatings().size() - 1)).child("userRating").setValue(toilet.getRatings().get(toilet.getRatings().size() - 1).getUserRating());
            }
            return true;
    }
        catch (Exception e)
        {
            return false;
        }
    }

    public static boolean editUserData()
    {
        try
        {
            userRef.child(DataHolder.getInstance().getUser().getId()).child("firstname").setValue(DataHolder.getInstance().getUser().getFirstname());
            userRef.child(DataHolder.getInstance().getUser().getId()).child("lastname").setValue(DataHolder.getInstance().getUser().getLastname());
            userRef.child(DataHolder.getInstance().getUser().getId()).child("username").setValue(DataHolder.getInstance().getUser().getUsername());
            userRef.child(DataHolder.getInstance().getUser().getId()).child("phone").setValue(DataHolder.getInstance().getUser().getPhone());
            userRef.child(DataHolder.getInstance().getUser().getId()).child("birthday").setValue(DataHolder.getInstance().getUser().getBirthday());
            userRef.child(DataHolder.getInstance().getUser().getId()).child("address").setValue(DataHolder.getInstance().getUser().getAddress());
            userRef.child(DataHolder.getInstance().getUser().getId()).child("gender").setValue(DataHolder.getInstance().getUser().getGender());
            userRef.child(DataHolder.getInstance().getUser().getId()).child("isAnbieter").setValue(DataHolder.getInstance().getUser().isAnbieter());
            return true;
        }catch (Exception e )
        {
            return false;
        }

    }

    public static void findUserbyemail(String email)
    {
        Query query = FirebaseDatabase.getInstance().getReference("/user").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    DataHolder.getInstance().getUser().setFirstname((String) d.child("firstname").getValue());
                    DataHolder.getInstance().getUser().setLastname((String) d.child("lastname").getValue());
                    DataHolder.getInstance().getUser().setUsername((String)d.child("username").getValue());
                    DataHolder.getInstance().getUser().setPhone((String)d.child("phone").getValue());
                    DataHolder.getInstance().getUser().setBirthday((String)d.child("birthday").getValue());
                    DataHolder.getInstance().getUser().setGender((String)d.child("gender").getValue());
                    DataHolder.getInstance().getUser().setUID((String)d.child("password").getValue());
                    DataHolder.getInstance().getUser().setEmail((String)d.child("email").getValue());
                    DataHolder.getInstance().getUser().setAddress((String)d.child("address").getValue());
                    DataHolder.getInstance().getUser().setAnbieter((boolean)d.child("isAnbieter").getValue());
                    DataHolder.getInstance().getUser().setId((String)d.getKey());

                }
               }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static String uploadImage(Uri filePath, final Context context) {

        String bucketResult = "";

        if(filePath != null)
        {
            StorageReference ref = storageRef.child("images/"+ UUID.randomUUID().toString());

            //final ProgressDialog progressDialog = new ProgressDialog(context);
            //progressDialog.setTitle("Uploading...");
            //progressDialog.show();

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //progressDialog.dismiss();
                            Toast.makeText(context, "Successfully uploaded photo!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            Toast.makeText(context, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            //progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
            bucketResult = ref.getPath();


        }

        return bucketResult;
    }


}
