package com.dubois.yann.go4lunch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("html_attributions")
    public List<Object> htmlAttributions = null;
    @SerializedName("next_page_token")
    public String nextPageToken;
    @SerializedName("results")
    public List<Restaurant> restaurantList = null;
    @SerializedName("status")
    public String status;

    //Empty constructor for serialization
    public Result() {}

    //Constructor
    public Result(List<Object> htmlAttributions, String nextPageToken, List<Restaurant> restaurantList, String status) {
        this.htmlAttributions = htmlAttributions;
        this.nextPageToken = nextPageToken;
        this.restaurantList = restaurantList;
        this.status = status;
    }

    //Getters & setters
    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
