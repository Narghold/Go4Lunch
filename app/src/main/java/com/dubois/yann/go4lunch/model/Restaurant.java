package com.dubois.yann.go4lunch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Parcelable {

    private int id;
    @SerializedName("place_id")
    private String place_id;
    @SerializedName("name")
    private String name;
    @SerializedName("geometry")
    private Geometry geometry;
    @SerializedName("vicinity")
    private String address;
    @SerializedName("rating")
    private Double rating;
    @SerializedName("photos")
    public List<Photo> photos = null;
    @SerializedName("opening_hours")
    private OpeningHour openingHour;
    @SerializedName("website")
    private String website;


    //Empty constructor for serialization
    public Restaurant() {}

    //Constructor
    public Restaurant(int id, String place_id, String name, Geometry geometry, String address, Double rating, List<Photo> photos, OpeningHour openingHour) {
        this.id = id;
        this.place_id = place_id;
        this.name = name;
        this.geometry = geometry;
        this.address = address;
        this.rating = rating;
        this.photos = photos;
        this.openingHour = openingHour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public OpeningHour getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHours(OpeningHour openingHour) {
        this.openingHour = openingHour;
    }

    //PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.place_id);
        dest.writeString(this.name);
        dest.writeParcelable(this.geometry, flags);
        dest.writeString(this.address);
        dest.writeValue(this.rating);
        dest.writeList(this.photos);
        dest.writeParcelable(this.openingHour, flags);
        dest.writeString(this.website);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.place_id = source.readString();
        this.name = source.readString();
        this.geometry = source.readParcelable(Geometry.class.getClassLoader());
        this.address = source.readString();
        this.rating = (Double) source.readValue(Double.class.getClassLoader());
        this.photos = new ArrayList<Photo>();
        source.readList(this.photos, Photo.class.getClassLoader());
        this.openingHour = source.readParcelable(OpeningHour.class.getClassLoader());
        this.website = source.readString();
    }

    protected Restaurant(Parcel in) {
        this.id = in.readInt();
        this.place_id = in.readString();
        this.name = in.readString();
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
        this.address = in.readString();
        this.rating = (Double) in.readValue(Double.class.getClassLoader());
        this.photos = new ArrayList<Photo>();
        in.readList(this.photos, Photo.class.getClassLoader());
        this.openingHour = in.readParcelable(OpeningHour.class.getClassLoader());
        this.website = in.readString();
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
