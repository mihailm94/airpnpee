package com.appspot.airpeepee.airpeepee.model;

public class Rating {

    public Rating(){}
    public Rating(String id,User user,double userRating){
        this.id=id;
        this.user=user;
        this.userRating=userRating;

    }

    private String id;
    private User user;
    private double userRating;

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

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
}
