package com.appspot.airpeepee.airpeepee.model;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import java.util.Date;

public class User {

    private FirebaseUser firebaseUser;
    private GoogleSignInAccount googleUser;
    private AccessToken facebookAccessToken;
    private String address;
    private String birthday;
    private String email;
    private String firstname;
    private String lastname;
    private String gender;
    private boolean isAnbieter;
    private String UID;
    private String phone;
    private String username;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isAnbieter() {
        return isAnbieter;
    }

    public void setAnbieter(boolean anbieter) {
        isAnbieter = anbieter;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public GoogleSignInAccount getGoogleUser() {
        return googleUser;
    }

    public void setGoogleUser(GoogleSignInAccount googleUser) {
        this.googleUser = googleUser;
    }

    public AccessToken getFacebookAccessToken() {
        return facebookAccessToken;
    }

    public void setFacebookAccessToken(AccessToken facebookAccessToken) {
        this.facebookAccessToken = facebookAccessToken;
    }

    public User(){}
}
