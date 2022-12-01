package com.example.hiltretrofitproject.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hiltretrofitproject.model.Meal
import com.example.hiltretrofitproject.model.Meals


@Dao
interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("Select * FROM Meals_table")
    fun getDbMeals(): LiveData<List<Meal>>

}