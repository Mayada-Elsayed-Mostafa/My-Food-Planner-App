package com.example.myfoodplannerapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealsOfWeek;
import com.example.myfoodplannerapplication.model.WeekMeals;

@Database(entities = {InspirationMeal.class, MealsOfWeek.class, WeekMeals.class}, version = 3)
@TypeConverters(Converters.class)
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
