package com.example.myfoodplannerapplication.network;

import android.util.Log;

import com.example.myfoodplannerapplication.model.InspirationMealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSource {

    public static String SINGLE_RANDOM_MEAL_URL = "https://www.themealdb.com/api/json/v1/1/";
    private InspirationMealService inspirationMealService;
    private static MealRemoteDataSource remoteDataSource = null;

    public MealRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SINGLE_RANDOM_MEAL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        inspirationMealService = retrofit.create(InspirationMealService.class);
    }

    public static MealRemoteDataSource getInstance() {
        if (remoteDataSource == null) {
            remoteDataSource = new MealRemoteDataSource();
        }

        return remoteDataSource;
    }

    public void getDataOverNetwork(NetworkCallback callback) {
        Call<InspirationMealResponse> call = inspirationMealService.getMeals();

        call.enqueue(new Callback<InspirationMealResponse>() {
            @Override
            public void onResponse(Call<InspirationMealResponse> call, Response<InspirationMealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("TAG", "onResponse: " + response.body().getMeals().size());
                    callback.onSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<InspirationMealResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: ");
                t.printStackTrace();
                callback.onFailure(t.getMessage());
            }
        });
    }

}
