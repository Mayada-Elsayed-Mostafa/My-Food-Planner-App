package com.example.myfoodplannerapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Parcelable {

    private String strArea;

    public Country(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strArea);
    }

    protected Country(Parcel in) {
        strArea = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
