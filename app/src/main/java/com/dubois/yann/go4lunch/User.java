package com.dubois.yann.go4lunch;

public class User {

    private String uid;
    private String username;
    private String photoURL;

    public User(String uid, String username, String photoURL) {
        this.uid = uid;
        this.username = username;
        this.photoURL = photoURL;
    }

    //Getters
    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    //Setters
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
