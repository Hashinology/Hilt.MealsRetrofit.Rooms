package com.example.hiltretrofitproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hiltretrofitproject.databinding.ActivityFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private val viewModel: MyViewModel by viewModels()
    @Inject
    lateinit var mealsAdapter: MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)


        setUpRecyclerView()

       viewModel.mealsDbLiveData.observe(this, Observer {
           mealsAdapter.differ.submitList(it)
       })

    }

    private fun setUpRecyclerView() {
        mealsAdapter = MealsAdapter()
        binding.rvFav.apply {
            adapter = mealsAdapter
            layoutManager = GridLayoutManager(this@FavoritesActivity,2,GridLayoutManager.VERTICAL,false)
        }

    }

}