package com.example.cookingsocialnetwork.main.fragment.home.listPosts.trendingPosts

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Post
import com.example.cookingsocialnetwork.viewpost.ViewPost
import io.reactivex.disposables.CompositeDisposable


class TrendingPagingAdapter : PagingDataAdapter<Post, TrendingPagingAdapter.TrendingSlideViewHolder>(
    Companion) {
    companion object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
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
        val post = getItem(position)
        post?.let { holder.bind(it) }
        
        holder.itemView.setOnClickListener{
            val idPostClicked = post?.id
            val context = holder.itemView.context
            if(idPostClicked != ""){
                val fullPost = Intent(context, ViewPost::class.java)
                fullPost.putExtra("id_post", idPostClicked)
                context.startActivity(fullPost)
            }
        }

    }
    override fun onViewRecycled(holder: TrendingPagingAdapter.TrendingSlideViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            foodName.text = ""
            foodImage.setImageDrawable(Drawable.createFromPath("@drawable/food_picker"))
            heartPost.text = ""
            viewHolderDisposables.clear()
        }
    }


    inner class TrendingSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val viewHolderDisposables = CompositeDisposable()
        val foodImage = view.findViewById<ImageView>(R.id.trendingFoodPicture)!!
        val foodName = view.findViewById<TextView>(R.id.trendingNameFood)!!
        val heartPost = view.findViewById<TextView>(R.id.heartTrending)!!

        fun bind(post : Post) {
            post.let {
                foodImage.load(post.images[0])
                foodName.text = post.nameFood
                heartPost.text = post.favourites.size.toString()
            } to viewHolderDisposables
        }
    }


}
