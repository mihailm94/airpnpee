package com.appspot.airpeepee.airpeepee.model;

public class Comment {

    public Comment(){}

    public Comment(String id,User user,String commentText){
        this.id=id;
        this.user=user;
        this.commentText=commentText;

    }
    public Comment(String id,String commentText){
        this.id=id;
        this.commentText=commentText;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    private String commentText;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;



}
