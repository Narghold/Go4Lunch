package com.dubois.yann.go4lunch.model;

import com.google.gson.annotations.SerializedName;

public class OpeningHour {

    @SerializedName("open_now")
    private Boolean openNow;

    //Empty constructor for serialization
    public OpeningHour() {}

    public OpeningHour(Boolean openNow) {
        this.openNow = openNow;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }
}
