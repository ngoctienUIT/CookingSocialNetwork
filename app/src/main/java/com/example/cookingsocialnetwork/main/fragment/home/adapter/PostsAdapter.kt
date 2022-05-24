package com.example.cookingsocialnetwork.main.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.main.fragment.home.realtimePost.RealtimePost
import com.example.cookingsocialnetwork.model.data.Post
import com.google.android.material.card.MaterialCardView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables

class PostsAdapter : PagedListAdapter<RealtimePost, PostsAdapter.PostViewHolder>(
    object : DiffUtil.ItemCallback<RealtimePost>() {
        override fun areItemsTheSame(old: RealtimePost, new: RealtimePost): Boolean =
            old.id == new.id

        override fun areContentsTheSame(old: RealtimePost, new: RealtimePost): Boolean =
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

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val record = getItem(position)
        holder.bind(record)
    }

    override fun onViewRecycled(holder: PostViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            foodName.text = ""
            author.text = ""
            rating.numStars = 0;
            crdRecord.setCardBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    android.R.color.white
                )
            )
            viewHolderDisposables.clear()
        }
    }

    inner class PostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val viewHolderDisposables = CompositeDisposable()

        val userImage : ImageView = itemView.findViewById(R.id.user_image)
        val author : TextView = itemView.findViewById(R.id.user_name)
        val foodImage : ImageView = itemView.findViewById(R.id.food_image_rec)
        val foodName : TextView = itemView.findViewById(R.id.food_name)
        val rating : RatingBar = itemView.findViewById(R.id.food_rating)
        val comment : TextView = itemView.findViewById(R.id.comment)
        val heart : TextView = itemView.findViewById(R.id.heart)

        fun bind(realtimePost: RealtimePost?) {
            realtimePost?.let {
                it.post
                    .subscribe { post ->
                        foodName.text = post.nameFood
                        author.text = post.owner
                        // timeCooking.text = post.cookingTime
                        rating.numStars =  post.level.toInt()
                        foodImage.load(post.images[0])
                    } to viewHolderDisposables
               // viewHolderDisposables.add();

            }
        }
    }
}