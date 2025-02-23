package com.example.myfoodplannerapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealsOfWeek;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealDao {

    @Query("SELECT * FROM mealtable")
    Observable<List<InspirationMeal>> getFavoriteMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(InspirationMeal meal);

    @Delete
    void deleteMeal(InspirationMeal meal);


    @Query("SELECT * FROM plantable")
    Observable<List<MealsOfWeek>> getMealsOfWeek();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealsOfWeek(MealsOfWeek meals);

    @Delete
    void deleteMealsOfWeek(MealsOfWeek meals);

    @Query("SELECT * FROM plantable WHERE day = :selectedDate")
    Observable<List<MealsOfWeek>> getMealsForDate(String selectedDate);
}

