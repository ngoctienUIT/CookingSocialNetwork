package com.example.cookingsocialnetwork.main.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
            R.layout.item_view_post,
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
       /* holder.apply {
            txtRecordName.text = ""
            crdRecord.isEnabled = true
            crdRecord.setCardBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    android.R.color.white
                )
            )
            viewHolderDisposables.clear()
        }*/
    }

    inner class PostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val viewHolderDisposables = CompositeDisposable()

        private val nameFood : TextView  =  view.findViewById(R.id.nameFoodPost)
        private val author:  TextView =  view.findViewById(R.id.nameAuthor)
        private val timeCooking: TextView = view.findViewById(R.id.timeCooking)
        private val serves : TextView = view.findViewById(R.id.servesInPost)
        private val difficulty: TextView = view.findViewById(R.id.difficultyInPost)
        private val imageFood: ImageView = view.findViewById(R.id.imageFoodInPost)

        fun bind(realtimePost: RealtimePost?) {
            realtimePost?.let {
                it.post
                    .subscribe { post ->
                        nameFood.text = post.nameFood
                        author.text = post.owner
                        timeCooking.text = post.cookingTime
                        serves.text = post.servers
                        difficulty.text = post.level.toString()
                        imageFood.load(post.images[0])
                    }
            }
        }
    }
}