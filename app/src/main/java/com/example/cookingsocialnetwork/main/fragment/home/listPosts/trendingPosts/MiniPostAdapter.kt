package com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R

class MiniPostAdapter(private val miniPostList: List<MiniPost>): RecyclerView.Adapter<MiniPostAdapter.MiniPostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniPostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_form_2, parent, false)
        return MiniPostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MiniPostViewHolder, position: Int) {
        val currentItem = miniPostList[position]
//       holder.userImage.setImageResource(currentItem.user_image)
        holder.userName.text = currentItem.user_name
//        holder.foodImage.setImageResource(currentItem.food_image)
        holder.foodName.text = currentItem.food_name
        holder.rating.rating = currentItem.food_rate.toFloat()
        holder.comment.text = currentItem.comment.toString()
        holder.heart.text = currentItem.comment.toString()
    }

    override fun getItemCount() = miniPostList.size

    class MiniPostViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView) {
        //val userImage : ImageView = itemView.findViewById(R.id.user_image_trending)
        val userName : TextView = itemView.findViewById(R.id.user_name_post_card)
        val foodImage : ImageView = itemView.findViewById(R.id.food_image_trending)
        val foodName : TextView = itemView.findViewById(R.id.food_name)
        val rating : RatingBar = itemView.findViewById(R.id.food_rating)
        val comment : TextView = itemView.findViewById(R.id.comment)
        val heart : TextView = itemView.findViewById(R.id.heart)
    }

}