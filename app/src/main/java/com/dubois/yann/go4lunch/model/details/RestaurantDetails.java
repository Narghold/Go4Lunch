package com.dubois.yann.go4lunch.model.details;

import com.dubois.yann.go4lunch.model.OpeningHour;
import com.dubois.yann.go4lunch.model.Photo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantDetails {

    @SerializedName("formatted_phone_number")
    private String formattedPhoneNumber;
    @SerializedName("name")
    private String name;
    @SerializedName("photos")
    private List<Photo> photos = null;
    @SerializedName("rating")
    private Double rating;
    @SerializedName("vicinity")
    private String vicinity;
    @SerializedName("website")
    private String website;
    @SerializedName("opening_hours")
    OpeningHour openingHour;

    //Empty constructor for serialization
    public RestaurantDetails() {}

    public RestaurantDetails(String formattedPhoneNumber, String name, List<Photo> photos, Double rating, String vicinity, String website, OpeningHour openingHour) {
        this.formattedPhoneNumber = formattedPhoneNumber;
        this.name = name;
        this.photos = photos;
        this.rating = rating;
        this.vicinity = vicinity;
        this.website = website;
        this.openingHour = openingHour;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public OpeningHour getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(OpeningHour openingHour) {
        this.openingHour = openingHour;
    }
}
