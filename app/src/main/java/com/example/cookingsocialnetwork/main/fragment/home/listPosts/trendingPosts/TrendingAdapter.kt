package com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R



class TrendingAdapter(private val trendingSlides: List<TrendingSlide>) :
    RecyclerView.Adapter<TrendingAdapter.TrendingSlideViewHolder>() {

    inner class TrendingSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val foodImage = view.findViewById<ImageView>(R.id.trendingFoodPicture)!!
        private val foodName = view.findViewById<TextView>(R.id.trendingNameFood)!!
        private val heartPost = view.findViewById<TextView>(R.id.heartTrending)!!

        fun bind(trendingSlide: TrendingSlide) {
            foodImage.load(trendingSlide.foodImage)
            foodName.text = trendingSlide.foodName
            heartPost.text = trendingSlide.heartPost
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingSlideViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.trending_slider,
                parent,
                false
            )
            return TrendingSlideViewHolder(view)

        }
    override fun onBindViewHolder(holder: TrendingSlideViewHolder, position: Int) {
        holder.bind(trendingSlides[position])
    }

    override fun getItemCount(): Int {
        return trendingSlides.size
    }




}






