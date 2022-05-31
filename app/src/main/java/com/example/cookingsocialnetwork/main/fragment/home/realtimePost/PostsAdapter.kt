package com.example.cookingsocialnetwork.main.fragment.home.realtimePost

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.viewpost.ViewFullPost
import io.reactivex.disposables.CompositeDisposable
import com.example.cookingsocialnetwork.main.fragment.home.realtimePost.RealtimePost as RealtimePost1

class PostsAdapter : PagedListAdapter<RealtimePost1, PostsAdapter.PostViewHolder>(
    object : DiffUtil.ItemCallback<RealtimePost1>() {
        override fun areItemsTheSame(old: RealtimePost1, new: RealtimePost1): Boolean =
            old.id == new.id

        override fun areContentsTheSame(old: RealtimePost1, new: RealtimePost1): Boolean =
            old == new
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_form,
            parent,
            false
        )
        return PostViewHolder(view)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val record = getItem(position)
        holder.bind(record)

        holder.itemView.setOnClickListener{
            val idPostClicked = record?.id
            val context = holder.itemView.context
            if(idPostClicked != ""){
                val fullPost = Intent(context, ViewFullPost::class.java)
                fullPost.putExtra("id_post", idPostClicked)
                context.startActivity(fullPost)
            }
        }

    }

    override fun onViewRecycled(holder: PostViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            userName.text = ""
            rating.numStars = 0
            foodName.text = ""
            foodImage.setImageDrawable(Drawable.createFromPath("@drawable/food_picker"))
            userImage.setImageDrawable(Drawable.createFromPath("@color/black"))
            comment.text = "";
            heart.text = ""
            viewHolderDisposables.clear()
        }
    }

    inner class PostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val viewHolderDisposables = CompositeDisposable()

        val userImage : ImageView = itemView.findViewById(R.id.user_image)
        val userName : TextView = itemView.findViewById(R.id.user_name)
        val foodImage : ImageView = itemView.findViewById(R.id.food_image_rec)
        val foodName : TextView = itemView.findViewById(R.id.food_name)
        val rating : RatingBar = itemView.findViewById(R.id.food_rating)
        val comment : TextView = itemView.findViewById(R.id.comment)
        val heart : TextView = itemView.findViewById(R.id.heart)

        fun bind(realtimePost: RealtimePost1?) {
            realtimePost?.let {
                it.post
                    .subscribe { post ->
                        foodImage.load(post.images[0])
                        userImage.load(post.avatarOwner)
                        userName.text = post.owner
                        rating.numStars = post.level.toInt()
                        foodName.text = post.nameFood
                        comment.text = post.comments.size.toString()
                        heart.text = post.favourites.size.toString()
                    } to viewHolderDisposables
            }
        }

    }
    fun
    interface OnClickListener{
        fun onCommentClick(view: View, position : Int)
    }
}