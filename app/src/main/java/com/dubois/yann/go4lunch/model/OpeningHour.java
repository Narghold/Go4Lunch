package com.dubois.yann.go4lunch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpeningHour{

    @SerializedName("open_now")
    private boolean openNow;
    @SerializedName("weekday_text")
    private List<String> weekdayText = null;

    //Empty constructor for serialization
    public OpeningHour() {}

    //Constructor
    public OpeningHour(boolean openNow, List<String> weekdayText) {
        this.openNow = openNow;
        this.weekdayText = weekdayText;
    }

    //Getters & Setters
    public boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public List<String> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<String> weekdayText) {
        this.weekdayText = weekdayText;
    }
}
