package com.example.myfoodplannerapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myfoodplannerapplication.model.InspirationMeal;

@Database(entities = {InspirationMeal.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE mealtable ADD COLUMN day TEXT DEFAULT null");
        }
    };

    public abstract MealDao getMealDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mealdb")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }
}
