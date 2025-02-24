package com.example.myfoodplannerapplication.database;

import android.content.Context;

import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealsOfWeek;
import com.example.myfoodplannerapplication.model.WeekMeals;

import java.util.List;
import java.util.WeakHashMap;

import io.reactivex.rxjava3.core.Observable;

public class MealLocalDataSource {

    private MealDao mealDao;
    private Observable<List<InspirationMeal>> inspirationMeals;
    private Observable<List<WeekMeals>> mealsOfWeek;
    private static MealLocalDataSource LocalDataSource = null;

    private MealLocalDataSource(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        mealDao = appDatabase.getMealDao();
        inspirationMeals = mealDao.getFavoriteMeals();
        mealsOfWeek = mealDao.getMeals();
    }

    public static MealLocalDataSource getInstance(Context context) {

        if (LocalDataSource == null) {
            LocalDataSource = new MealLocalDataSource(context);
        }
        return LocalDataSource;
    }

    public Observable<List<InspirationMeal>> getMeal() {
        return inspirationMeals;
    }

    public Observable<List<WeekMeals>> getMeals() {
        return mealsOfWeek;
    }

    public void insert(InspirationMeal inspirationMeal) {
        new Thread(() -> {
            mealDao.insertMeal(inspirationMeal);
        }).start();
    }

    public void delete(InspirationMeal meal) {
        new Thread(() -> {
            mealDao.deleteMeal(meal);
        }).start();
    }

    public void insertToPlan(MealsOfWeek mealsOfWeek) {
        new Thread(() -> {
            mealDao.insertMealsOfWeek(mealsOfWeek);
        }).start();
    }

    public void deleteFromPlan(MealsOfWeek mealsOfWeek) {
        new Thread(() -> {
            mealDao.deleteMealsOfWeek(mealsOfWeek);
        }).start();
    }

    public void insertToWeeklyPlan(WeekMeals meals){
        new Thread(() -> {
            mealDao.insertMealToWeek(meals);
        }).start();
    }

    public void deleteFromWeeklyPlan(WeekMeals meals){
        new Thread(() -> {
            mealDao.deleteMealFromWeek(meals);
        }).start();
    }

}
