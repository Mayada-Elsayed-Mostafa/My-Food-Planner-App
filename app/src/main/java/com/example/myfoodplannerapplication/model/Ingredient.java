package com.example.myfoodplannerapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    String idIngredient;
    String strIngredient;
    String strDescription;

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idIngredient);
        dest.writeString(strIngredient);
        dest.writeString(strDescription);
    }

    protected Ingredient(Parcel in) {
        idIngredient = in.readString();
        strIngredient = in.readString();
        strDescription = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
