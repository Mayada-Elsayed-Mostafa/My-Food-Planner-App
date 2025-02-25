package com.example.myfoodplannerapplication.model;

import android.util.Log;

import com.example.myfoodplannerapplication.database.MealLocalDataSource;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealRepository {

    private static MealRepository mealRepository = null;
    private MealRemoteDataSource mealRemoteDataSource;
    private MealLocalDataSource mealLocalDataSource;

    private MealRepository(MealLocalDataSource localDataSource, MealRemoteDataSource remoteDataSource) {
        this.mealLocalDataSource = localDataSource;
        this.mealRemoteDataSource = remoteDataSource;
    }

    public static MealRepository getInstance(MealLocalDataSource localDataSource, MealRemoteDataSource remoteDataSource) {
        if (mealRepository == null) {
            mealRepository = new MealRepository(localDataSource, remoteDataSource);
        }
        return mealRepository;
    }

    public Observable<List<InspirationMeal>> getAllData() {
        return mealLocalDataSource.getMeal();
    }

    public Observable<List<WeekMeals>> getMealsForDate(String selectedDate) {
        Log.d("TAG", "Fetching meals for date: " + selectedDate);
        return mealLocalDataSource.getMealsForDate(selectedDate);
    }


    public Single<InspirationMealResponse> getMealFromNetwork() {
        return mealRemoteDataSource.getMealOverNetwork()
                .onErrorResumeNext(throwable -> {
                    return Single.error(new Exception("Fail to get the data"));
                });
    }


    public void insert(InspirationMeal inspirationMeal) {
        mealLocalDataSource.insert(inspirationMeal);
    }

    public void delete(InspirationMeal inspirationMeal) {
        mealLocalDataSource.delete(inspirationMeal);
    }

    public void insertToWeeklyPlan(WeekMeals meals) {
        mealLocalDataSource.insertToWeeklyPlan(meals);
    }

    public void deleteFromWeeklyPlan(WeekMeals meals) {
        mealLocalDataSource.deleteFromWeeklyPlan(meals);
    }


    public Single<CategoriesResponse> getCategoryFromNetwork() {
        return mealRemoteDataSource.getCategoryOverNetwork();
    }

    public Single<CountryResponse> getCountryFromNetwork() {
        return mealRemoteDataSource.getCountryOverNetwork();
    }

    public Single<IngredientResponse> getIngredientFromNetwork() {
        return mealRemoteDataSource.getIngredientOverNetwork();
    }

    public Single<ByFilterResponse> getMealsByFilterOfCategoryOverNetwork(String filter) {
        return mealRemoteDataSource.getMealsByCategoriesOverNetwork(filter);
    }

    public Single<ByFilterResponse> getMealsByFilterOfCountryOverNetwork(String filter) {
        return mealRemoteDataSource.getMealsByCountryOverNetwork(filter);
    }

    public Single<ByFilterResponse> getMealsByFilterOfIngredientsOverNetwork(String filter) {
        return mealRemoteDataSource.getMealsByIngredientsOverNetwork(filter);
    }

    public Single<InspirationMealResponse> getMealsByFilterOfIdOverNetwork(String filter) {
        return mealRemoteDataSource.getMealsByIdOverNetwork(filter);
    }

}
