package com.dubois.yann.go4lunch.model;

import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("location")
    Location location;

    //Empty constructor for serialization
    public Geometry() {}

    //Constructor
    public Geometry(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
