package com.example.hiltretrofitproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "Meals_table")
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
):Serializable{

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}