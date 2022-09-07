package com.dubois.yann.go4lunch.model;

public class UserChoice {
    private String userId;
    private String placeId;
    private String placeName;

    //Constructor
    public UserChoice(String userId, String placeId, String placeName) {
        this.userId = userId;
        this.placeId = placeId;
        this.placeName = placeName;
    }

    //Empty constructor for serialization
    public UserChoice() {}

    //Getter & Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
