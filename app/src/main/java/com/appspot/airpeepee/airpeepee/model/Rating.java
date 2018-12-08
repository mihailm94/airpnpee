package com.appspot.airpeepee.airpeepee.model;

public class Rating {

    public Rating(){}
    public Rating(String id,User user,float userRating){
        this.id=id;
        this.user=user;
        this.userRating=userRating;

    }
    public Rating(String id,float userRating){
        this.id=id;
        this.userRating=userRating;

    }

    private String id;
    private User user;
    private float userRating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }
}
