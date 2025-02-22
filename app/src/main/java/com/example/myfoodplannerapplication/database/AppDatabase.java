package com.example.myfoodplannerapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealsOfWeek;

@Database(entities = {InspirationMeal.class, MealsOfWeek.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;

    public abstract MealDao getMealDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mealdb")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
