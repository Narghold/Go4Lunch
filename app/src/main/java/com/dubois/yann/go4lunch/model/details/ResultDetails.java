package com.dubois.yann.go4lunch.model.details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultDetails {

    @SerializedName("html_attributions")
    private List<Object> htmlAttributions = null;
    @SerializedName("result")
    private RestaurantDetails restaurantDetails;
    @SerializedName("status")
    private String status;

    //Empty constructor for serialization
    public ResultDetails() {}

    //Constructor
    public ResultDetails(List<Object> htmlAttributions, RestaurantDetails restaurantDetails, String status) {
        this.htmlAttributions = htmlAttributions;
        this.restaurantDetails = restaurantDetails;
        this.status = status;
    }

    //Getters & setters
    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
