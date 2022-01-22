package com.dubois.yann.go4lunch.model;

public class User {

    private String id;
    private String username;
    private String photoURL;

    //Constructor
    public User(String id, String username, String photoURL) {
        this.id = id;
        this.username = username;
        this.photoURL = photoURL;
    }

    //Getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    //Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
