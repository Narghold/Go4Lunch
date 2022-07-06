package com.dubois.yann.go4lunch.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String username;
    private String photoURL;
    private List<String> favoriteList;

    //Constructor
    public User(String id, String username, String photoURL, List<String> favoriteList) {
        this.id = id;
        this.username = username;
        this.photoURL = photoURL;
        this.favoriteList = favoriteList;
    }

    //For database
    public User(){
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

    public List<String> getFavoriteList() {
        return favoriteList;
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

    public void setFavoriteList(List<String> favoriteList) {
        this.favoriteList = favoriteList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }
}
