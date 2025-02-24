package com.example.myfoodplannerapplication.database;

import androidx.room.TypeConverter;

import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.google.gson.Gson;

public class Converters {

    @TypeConverter
    public static InspirationMeal fromString(String value) {
        return new Gson().fromJson(value, InspirationMeal.class);
    }

    @TypeConverter
    public static String fromInspirationMeal(InspirationMeal meal) {
        return new Gson().toJson(meal);
    }
}
