package com.example.myfoodplannerapplication.database;

import android.content.Context;

import com.example.myfoodplannerapplication.model.InspirationMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class MealLocalDataSource {

    private MealDao mealDao;
    private Observable<List<InspirationMeal>> inspirationMeals;
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

    public Observable<List<InspirationMeal>> getData() {
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
