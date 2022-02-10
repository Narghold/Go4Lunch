package com.dubois.yann.go4lunch.model;

public class Restaurant {

    private String id;
    private String name;
    private String address;
    private int distance;
    private String nationality;
    private String photoURL;
    private Double rating;

    public Restaurant(String id, String name, String address, int distance, String nationality, String photoURL, Double rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.nationality = nationality;
        this.photoURL = photoURL;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", distance=" + distance +
                ", nationality='" + nationality + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", rating=" + rating +
                '}';
    }
}
