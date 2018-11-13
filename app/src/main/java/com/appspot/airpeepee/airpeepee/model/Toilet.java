package com.appspot.airpeepee.airpeepee.model;

import android.location.Address;
import android.location.Location;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Toilet {

    protected String id;
    protected String fee;

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    protected double locationLat;

    public double getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(double locationLon) {
        this.locationLon = locationLon;
    }

    protected double locationLon;
    protected String name;
    protected String openingHours;
    protected String plz;
    protected String street;
    protected String streetNumber;
    protected String wheelchair;

    //protected Location location;
    //protected Address address;

    public Toilet(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }

    public Toilet (String id, String fee, double locationLat, double locationLon, String name, String openingHours, String plz, String street, String streetNumber, String wheelchair){
        this.id=id;
        this.fee=fee;
        this.locationLat=locationLat;
        this.locationLon=locationLon;
        this.name=name;
        this.openingHours=openingHours;
        this.plz = plz;
        this.street = street;
        this.streetNumber = streetNumber;
        this.wheelchair=wheelchair;
     // constracter code hier

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String isFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getOpeninghours() {
        return openingHours;
    }

    public void setOpeninghours(String opening_hours) {
        this.openingHours = opening_hours;
    }









    public String isWheelchair() {
        return wheelchair;
    }

    public void setWheelchair(String wheelchair) {
        this.wheelchair = wheelchair;
    }


}
