package com.example.hiltretrofitproject.api

import com.example.hiltretrofitproject.data.MealsDao
import com.example.hiltretrofitproject.model.Meal
import javax.inject.Inject


class MyRepository @Inject constructor(
    val api: ApiService,
    val dao: MealsDao,
) {

    suspend fun getMeals() = api.getMeals()

    suspend fun insertMeal(meal: Meal) = dao.insertMeal(meal)

    suspend fun deleteMeal(meal: Meal) = dao.deleteMeal(meal)

    fun getDbMeals() = dao.getDbMeals()


}