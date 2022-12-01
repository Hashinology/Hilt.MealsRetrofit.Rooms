package com.example.hiltretrofitproject.ui

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hiltretrofitproject.databinding.MealListItemBinding
import com.example.hiltretrofitproject.model.Meal
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MealsAdapter @Inject constructor(
) : RecyclerView.Adapter<MealsAdapter.ViewHolder>() {


    private val callback = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var onItemClickListener: ((Meal) -> Unit)? = null

    inner class ViewHolder(val binding: MealListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal){
            binding.apply {
                meal.also {
                    tvMealId.text = "Id: ${it.idMeal}"
                    tvMealName.text = it.strMeal
                   Glide.with(ivMeal.rootView).load(it.strMealThumb).into(ivMeal)
                }

                binding.root.setOnClickListener {
                    onItemClickListener?.let { it(meal) }
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
           MealListItemBinding.inflate(
               LayoutInflater.from(parent.context),
               parent,
               false
           )
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val meal = differ.currentList[position]
        holder.bind(meal)



    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }

}