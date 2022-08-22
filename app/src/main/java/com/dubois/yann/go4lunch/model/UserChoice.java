package com.dubois.yann.go4lunch.model;

public class UserChoice {
    private String userId;
    private String placeId;

    //Constructor
    public UserChoice(String userId, String placeId) {
        this.userId = userId;
        this.placeId = placeId;
    }

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
}
