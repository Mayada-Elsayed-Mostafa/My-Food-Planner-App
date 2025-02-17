package com.example.myfoodplannerapplication.model;

import androidx.lifecycle.LiveData;

import com.example.myfoodplannerapplication.database.MealLocalDataSource;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;
import com.example.myfoodplannerapplication.network.NetworkCallback;

import java.util.List;

public class MealRepository {

    private static MealRepository mealRepository = null;

    private MealRemoteDataSource mealRemoteDataSource;
    private MealLocalDataSource mealLocalDataSource;

    private MealRepository(MealLocalDataSource localDataSource, MealRemoteDataSource remoteDataSource) {
        this.mealLocalDataSource = localDataSource;
        this.mealRemoteDataSource = remoteDataSource;
    }

    public static MealRepository getInstance(MealLocalDataSource localDataSource, MealRemoteDataSource remoteDataSource){
        if (mealRepository == null) {
            mealRepository = new MealRepository(localDataSource, remoteDataSource);
        }
        return mealRepository;
    }

    public LiveData<List<InspirationMeal>> getAllData() {
        return mealLocalDataSource.getData();
    }

    public void insert(InspirationMeal inspirationMeal) {
        mealLocalDataSource.insert(inspirationMeal);
    }

    public void delete(InspirationMeal inspirationMeal) {
        mealLocalDataSource.delete(inspirationMeal);
    }

    public void getAllData(NetworkCallback networkCallBack) {
        mealRemoteDataSource.getDataOverNetwork(networkCallBack);
    }

}
