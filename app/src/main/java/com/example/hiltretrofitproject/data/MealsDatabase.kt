package com.example.hiltretrofitproject.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hiltretrofitproject.model.Meal

@Database(
    entities = [Meal::class],
    version = 1
    )
abstract class MealsDatabase : RoomDatabase() {

    abstract fun getDao(): MealsDao
}