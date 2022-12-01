package com.example.hiltretrofitproject.di

import android.content.Context
import androidx.room.Room
import com.example.hiltretrofitproject.api.ApiService
import com.example.hiltretrofitproject.api.MyRepository
import com.example.hiltretrofitproject.data.MealsDao
import com.example.hiltretrofitproject.data.MealsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    private const val BASE_URL = "https://www.themealdb.com/api/json/"


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService, dao: MealsDao) = MyRepository(apiService,dao)


    // DataBase
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        MealsDatabase::class.java,
        "Meals_DataBase"
    ).build()


    // Dao
    @Singleton
    @Provides
    fun provideDao(db: MealsDatabase) = db.getDao()


}