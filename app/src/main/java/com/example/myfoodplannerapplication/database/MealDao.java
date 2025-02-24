package com.example.myfoodplannerapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.WeekMeals;

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


    @Query("SELECT * FROM weekplantable")
    Observable<List<WeekMeals>> getMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealToWeek(WeekMeals meals);

    @Delete
    void deleteMealFromWeek(WeekMeals meals);

    @Query("SELECT * FROM weekplantable WHERE day = :selectedDate")
    Observable<List<WeekMeals>> getMealsForDate(String selectedDate);


}

