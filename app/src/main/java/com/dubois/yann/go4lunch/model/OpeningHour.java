package com.dubois.yann.go4lunch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpeningHour implements Parcelable {

    @SerializedName("open_now")
    private boolean openNow;
    @SerializedName("weekday_text")
    private List<String> weekdayText = null;

    //Empty constructor for serialization
    public OpeningHour() {}

    public OpeningHour(boolean openNow, List<String> weekdayText) {
        this.openNow = openNow;
        this.weekdayText = weekdayText;
    }

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

    //Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.openNow ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.weekdayText);
    }

    public void readFromParcel(Parcel source) {
        this.openNow = source.readByte() != 0;
        this.weekdayText = source.createStringArrayList();
    }

    protected OpeningHour(Parcel in) {
        this.openNow = in.readByte() != 0;
        this.weekdayText = in.createStringArrayList();
    }

    public static final Parcelable.Creator<OpeningHour> CREATOR = new Parcelable.Creator<OpeningHour>() {
        @Override
        public OpeningHour createFromParcel(Parcel source) {
            return new OpeningHour(source);
        }

        @Override
        public OpeningHour[] newArray(int size) {
            return new OpeningHour[size];
        }
    };
}
