package com.example.cookingsocialnetwork.main.fragment.home.loadMiniPost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.intro.IntroSlide

class TrendingAdapter(private val trendingSlides: List<TrendingSlide>) :
    RecyclerView.Adapter<TrendingAdapter.TrendingSlideViewHolder>() {
    inner class TrendingSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val foodImage = view.findViewById<ImageView>(R.id.trendingFoodPicture)
        private val foodName = view.findViewById<TextView>(R.id.trendingFoodName)

        fun bind(trendingSlide: TrendingSlide) {
            foodImage.setImageResource(trendingSlide.foodImage)
            foodName.text =  trendingSlide.foodName

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingSlideViewHolder {
        return TrendingSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.trending_slider,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrendingSlideViewHolder, position: Int) {
        holder.bind(trendingSlides[position])
    }

    override fun getItemCount(): Int {
        return trendingSlides.size
    }
}