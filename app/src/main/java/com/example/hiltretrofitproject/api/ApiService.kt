package com.example.hiltretrofitproject.api

import com.example.hiltretrofitproject.model.Meals
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v1/1/filter.php?a=Canadian")
    suspend fun getMeals(): Response<Meals>
}