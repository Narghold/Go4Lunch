package com.dubois.yann.go4lunch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {

    @SerializedName("height")
    public Integer height;
    @SerializedName("html_attributions")
    public List<String> htmlAttributions = null;
    @SerializedName("photo_reference")
    public String photoReference;
    @SerializedName("width")
    public Integer width;

    //Empty constructor for serialization
    public Photo() {
    }

    //Constructor
    public Photo(Integer height, List<String> htmlAttributions, String photoReference, Integer width) {
        this.height = height;
        this.htmlAttributions = htmlAttributions;
        this.photoReference = photoReference;
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
