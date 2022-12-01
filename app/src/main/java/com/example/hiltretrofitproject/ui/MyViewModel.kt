package com.example.hiltretrofitproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltretrofitproject.api.MyRepository
import com.example.hiltretrofitproject.model.Meal
import com.example.hiltretrofitproject.model.Meals
import com.example.hiltretrofitproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MyViewModel @Inject constructor(val repo : MyRepository): ViewModel() {

    private val _mealsLiveData = MutableLiveData<Resource<Meals>>(Resource.Loading())
    val mealsLiveData: LiveData<Resource<Meals>> = _mealsLiveData


    val mealsDbLiveData: LiveData<List<Meal>>


    init {
        mealsDbLiveData = repo.getDbMeals()
    }


    fun getMeals() = viewModelScope.launch(Dispatchers.IO) {
        _mealsLiveData.postValue(Resource.Loading())
        val response = repo.getMeals()
        try {
            if (response.isSuccessful){
                _mealsLiveData.postValue(Resource.Success(response.body()!!))
            }else{
                _mealsLiveData.postValue(Resource.Error(response.message()))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> _mealsLiveData.postValue(Resource.Error("No Internet Connection"))
                else -> _mealsLiveData.postValue(Resource.Error(t.message.toString()))

            }
        }

    }

    fun addMeal(meal: Meal) = viewModelScope.launch(Dispatchers.IO) {
        repo.insertMeal(meal)
    }

    fun removeMeal(meal: Meal) = viewModelScope.launch(Dispatchers.IO){
        repo.deleteMeal(meal)
    }






}