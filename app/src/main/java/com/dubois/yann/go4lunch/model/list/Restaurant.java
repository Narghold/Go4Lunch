package com.dubois.yann.go4lunch.model.list;

import com.dubois.yann.go4lunch.model.Geometry;
import com.dubois.yann.go4lunch.model.OpeningHour;
import com.dubois.yann.go4lunch.model.Photo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Restaurant {

    private int id;
    @SerializedName("place_id")
    private String place_id;
    @SerializedName("name")
    private String name;
    @SerializedName("geometry")
    private Geometry geometry;
    @SerializedName("vicinity")
    private String address;
    @SerializedName("rating")
    private Double rating;
    @SerializedName("photos")
    public List<Photo> photos = null;
    @SerializedName("opening_hours")
    private OpeningHour openingHour;


    //Empty constructor for serialization
    public Restaurant() {}

    //Constructor
    public Restaurant(int id, String place_id, String name, Geometry geometry, String address, Double rating, List<Photo> photos, OpeningHour openingHour) {
        this.id = id;
        this.place_id = place_id;
        this.name = name;
        this.geometry = geometry;
        this.address = address;
        this.rating = rating;
        this.photos = photos;
        this.openingHour = openingHour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public OpeningHour getOpeningHour() {
        return openingHour;
    }


    public void setOpeningHour(OpeningHour openingHour) {
        this.openingHour = openingHour;
    }

}
