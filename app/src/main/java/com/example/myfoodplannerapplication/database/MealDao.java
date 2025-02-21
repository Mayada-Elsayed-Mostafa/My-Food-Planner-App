package com.example.myfoodplannerapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myfoodplannerapplication.model.InspirationMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealDao {

    @Query("SELECT * FROM mealtable")
    Observable<List<InspirationMeal>> getFavoriteMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(InspirationMeal product);

    @Delete
    void deleteMeal(InspirationMeal product);
}
