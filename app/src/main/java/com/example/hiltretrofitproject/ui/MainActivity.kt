package com.example.hiltretrofitproject.ui

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hiltretrofitproject.databinding.ActivityMainBinding
import com.example.hiltretrofitproject.model.Meal
import com.example.hiltretrofitproject.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MyViewModel by viewModels()
    @Inject
    lateinit var mealsAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setUpRecyclerView()

        viewModel.getMeals()

        lifecycleScope.launch {
            viewModel.mealsLiveData.observe(this@MainActivity, Observer { resource->
                when (resource) {
                    is Resource.Loading -> {
                        showProgress()
                        hideError()
                    }
                    is Resource.Success -> {
                        hideProgress()
                        hideError()
                        resource.data?.let {
                            mealsAdapter.differ.submitList(it.meals)
                        }
                    }
                    is Resource.Error -> {
                        hideProgress()
                        resource.message?.let {
                            showError(it)
                        }
                    }
                }
            })

        }


        mealsAdapter.setOnItemClickListener { meal ->
            viewModel.addMeal(meal)
            Toast.makeText(this,"Meal added successfully",Toast.LENGTH_SHORT).show()

        }


        binding.fbToFavorites.setOnClickListener {
            startActivity(
                Intent(this,FavoritesActivity::class.java)
            )
        }

    }

    private fun hideError() {
        binding.tvError.visibility = View.INVISIBLE
    }

    private fun showError(message: String) {
       binding.tvError.apply {
           visibility = View.VISIBLE
           text = message
       }
    }

    private fun hideProgress() {
        binding.pbLoading.visibility = View.INVISIBLE
    }

    private fun showProgress() {
        binding.pbLoading.visibility = View.VISIBLE
    }


    private fun setUpRecyclerView() {
        mealsAdapter = MealsAdapter()
        binding.rvPosts.apply {
            adapter = mealsAdapter
            layoutManager = GridLayoutManager(this@MainActivity,2,GridLayoutManager.VERTICAL,false)
        }
    }
}