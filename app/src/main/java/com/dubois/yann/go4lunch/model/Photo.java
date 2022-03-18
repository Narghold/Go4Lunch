package com.dubois.yann.go4lunch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {

    @SerializedName("photo_reference")
    public String photoReference;

    //Empty constructor for serialization
    public Photo() {
    }

    //Constructor
    public Photo(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

}
