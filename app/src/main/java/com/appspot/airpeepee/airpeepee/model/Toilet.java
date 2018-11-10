package com.appspot.airpeepee.airpeepee.model;

import android.location.Address;
import android.location.Location;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Toilet {

    protected int id;
    protected String fee;
    protected String name;
    protected String opening_hours;
    protected Location location;
    protected Address address;
    protected String wheelchair;


    public Toilet(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }

    public Toilet (int id,String fee,String name,String opening_hours,Location location,Address address,String wheelchair){
        this.id=id;
        this.fee=fee;
        this.name=name;
        this.opening_hours=opening_hours;
        this.location=location;
        this.address=address;
        this.wheelchair=wheelchair;
     // constracter code hier

    }


    /*

    public int getId() {
        return id;
    }

    public void setId(int id) {
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



    public String getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(String opening_hours) {
        this.opening_hours = opening_hours;
    }



    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }



    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }



    public String isWheelchair() {
        return wheelchair;
    }

    public void setWheelchair(String wheelchair) {
        this.wheelchair = wheelchair;
    }

    */

}
