package com.example.myfoodplannerapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FilterBy implements Parcelable {

    String strMeal;
    String strMealThumb;
    String idMeal;

    protected FilterBy(Parcel in) {
        strMeal = in.readString();
        strMealThumb = in.readString();
        idMeal = in.readString();
    }

    public static final Creator<FilterBy> CREATOR = new Creator<FilterBy>() {
        @Override
        public FilterBy createFromParcel(Parcel in) {
            return new FilterBy(in);
        }

        @Override
        public FilterBy[] newArray(int size) {
            return new FilterBy[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(strMeal);
        parcel.writeString(strMealThumb);
        parcel.writeString(idMeal);
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }
}
