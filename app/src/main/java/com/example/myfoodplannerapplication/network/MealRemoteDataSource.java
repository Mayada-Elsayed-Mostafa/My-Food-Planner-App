package com.example.myfoodplannerapplication.network;

import android.util.Log;

import com.example.myfoodplannerapplication.model.CategoriesResponse;
import com.example.myfoodplannerapplication.model.CountryResponse;
import com.example.myfoodplannerapplication.model.IngredientResponse;
import com.example.myfoodplannerapplication.model.InspirationMealResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealRemoteDataSource {

    public static String BASE_URL = "https://www.themealdb.com/";
    private InspirationMealService inspirationMealService;
    private static MealRemoteDataSource remoteDataSource = null;



    public MealRemoteDataSource() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        inspirationMealService = retrofit.create(InspirationMealService.class);
    }

    public static MealRemoteDataSource getInstance() {
        if (remoteDataSource == null) {
            remoteDataSource = new MealRemoteDataSource();
        }

        return remoteDataSource;
    }

    public Single<InspirationMealResponse> getMealOverNetwork() {
        return inspirationMealService.getMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<CategoriesResponse> getCategoryOverNetwork() {
        return inspirationMealService.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<CountryResponse> getCountryOverNetwork() {
        return inspirationMealService.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<IngredientResponse> getIngredientOverNetwork() {
        return inspirationMealService.getIngredients()
                .doOnSuccess(response -> {
                    Log.d("API_RESPONSE", "Ingredients: " + response.getIngredients().size());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
