package com.example.myfoodplannerapplication.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.myfoodplannerapplication.model.InspirationMeal;

import java.util.List;

public class MealLocalDataSource {

    private MealDao mealDao;
    private LiveData<List<InspirationMeal>> inspirationMeals;
    private static MealLocalDataSource LocalDataSource = null;

    private MealLocalDataSource(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        mealDao = appDatabase.getMealDao();
        inspirationMeals = mealDao.getFavoriteMeals();
    }

    public static MealLocalDataSource getInstance(Context context) {

        if (LocalDataSource == null) {
            LocalDataSource = new MealLocalDataSource(context);
        }
        return LocalDataSource;
    }

    public LiveData<List<InspirationMeal>> getData() {
        return inspirationMeals;
    }

    public void insert(InspirationMeal inspirationMeal) {
        new Thread(() -> {
            mealDao.insertMeal(inspirationMeal);
        }).start();
    }

    public void delete(InspirationMeal product) {
        new Thread(() -> {
            mealDao.deleteMeal(product);
        }).start();
    }
}
