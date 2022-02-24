package com.dubois.yann.go4lunch.model;

import android.graphics.Bitmap;

import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    private int id;
    @SerializedName("place_id")
    private String place_id;
    @SerializedName("name")
    private String name;
    @SerializedName("lat")
    private Double latitude;
    @SerializedName("lng")
    private Double longitude;
    @SerializedName("vicinity")
    private String address;
    @SerializedName("rating")
    private float rating;
    @SerializedName("photo_reference")
    private String photoReference;

    public Restaurant(int id, String place_id, String name, Double latitude, Double longitude, String address, float rating, String photoReference) {
        this.id = id;
        this.place_id = place_id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.rating = rating;
        this.photoReference = photoReference;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", place_id='" + place_id + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                ", photoReference='" + photoReference + '\'' +
                '}';
    }
}
