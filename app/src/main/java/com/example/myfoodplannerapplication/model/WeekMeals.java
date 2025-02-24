package com.example.myfoodplannerapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "weekplantable")
public class WeekMeals implements Parcelable {

    @PrimaryKey
    @NonNull
    private String id;
    private String day;
    private String type;
    private InspirationMeal meal;

    public WeekMeals() {
    }

    public WeekMeals(@NonNull String id, String day, String type, InspirationMeal meal) {
        this.id = id;
        this.day = day;
        this.type = type;
        this.meal = meal;
    }

    protected WeekMeals(Parcel in) {
        id = in.readString();
        day = in.readString();
        type = in.readString();
        meal = in.readParcelable(InspirationMeal.class.getClassLoader());
    }

    public static final Creator<WeekMeals> CREATOR = new Creator<WeekMeals>() {
        @Override
        public WeekMeals createFromParcel(Parcel in) {
            return new WeekMeals(in);
        }

        @Override
        public WeekMeals[] newArray(int size) {
            return new WeekMeals[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(day);
        parcel.writeString(type);
        parcel.writeParcelable(meal, i);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InspirationMeal getMeal() {
        return meal;
    }

    public void setMeal(InspirationMeal meal) {
        this.meal = meal;
    }
}
