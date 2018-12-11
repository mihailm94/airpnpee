package com.appspot.airpeepee.airpeepee.model;

import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Toilet {

    protected String id;
    protected String fee;
    protected List<Rating> Ratings;
    protected double totalRating;
    protected boolean isPrivate;
    protected List<Comment> comments;
    protected String description;
    protected double locationLat;
    protected double locationLon;
    protected String name;
    protected String openingHours;
    protected boolean outoforder;
    protected String plz;
    protected String street;
    protected String streetNumber;
    protected String wheelchair;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    protected double cost;

    public boolean isOutoforder() {
        return outoforder;
    }

    public void setOutoforder(boolean outoforder) {
        this.outoforder = outoforder;
    }




    protected String photoUrl;

    public Toilet(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        comments = new ArrayList<Comment>();
        Ratings = new ArrayList<Rating>();

    }

    public Toilet (String id, String fee, double locationLat, double locationLon, String name, String openingHours, String plz, String street, String streetNumber, String wheelchair,boolean isPrivate){
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
        this.isPrivate=isPrivate;
        comments = new ArrayList<Comment>();
        Ratings = new ArrayList<Rating>();

        // constracter code hier

    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(double locationLon) {
        this.locationLon = locationLon;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<Rating> getRatings() {
        return Ratings;
    }

    public void setRatings(List<Rating> ratings) {
        Ratings = ratings;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String isWheelchair() {
        return wheelchair;
    }

    public void setWheelchair(String wheelchair) {
        this.wheelchair = wheelchair;
    }
    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }



}
