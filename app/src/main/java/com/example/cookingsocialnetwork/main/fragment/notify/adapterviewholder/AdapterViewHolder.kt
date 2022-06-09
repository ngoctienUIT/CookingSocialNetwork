package com.example.cookingsocialnetwork.main.fragment.notify.adapterviewholder

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Notify
import com.example.cookingsocialnetwork.viewpost.ViewFullPost

class AdapterViewHolder(private var listNotify: MutableList<Notify>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class ViewHolderType {
        COMMENT, FOLLOW, FAVORITE
    }

    var onLoadMore: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         when (viewType) {
            ViewHolderType.COMMENT.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.comment_notify_item, parent, false)
                return CommentViewHolder(view)
            }
            ViewHolderType.FOLLOW.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.follow_notify_item, parent, false)
                return FollowViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.favorite_notify_item, parent, false)
                return FavoriteViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommentViewHolder -> {
                holder.comment = listNotify[position]
                holder.itemView.setOnClickListener()
                {
                    val fullPost = Intent(holder.itemView.context, ViewFullPost::class.java)
                    fullPost.putExtra("id_post", listNotify[position].id)
                    holder.itemView.context.startActivity(fullPost)
                }
                holder.updateView()
            }

            is FollowViewHolder -> {
                holder.follow = listNotify[position]
                holder.updateView()
            }

            is FavoriteViewHolder -> {
                holder.favorite = listNotify[position]
                holder.itemView.setOnClickListener()
                {
                    val fullPost = Intent(holder.itemView.context, ViewFullPost::class.java)
                    fullPost.putExtra("id_post", listNotify[position].id)
                    holder.itemView.context.startActivity(fullPost)
                }
                holder.updateView()
            }
        }

        if (position == listNotify.size) {
            onLoadMore?.let { it() }
        }
    }

    override fun getItemCount(): Int = listNotify.size

    override fun getItemViewType(position: Int): Int {
        return when (listNotify[position].type) {
            "comment" -> ViewHolderType.COMMENT.ordinal
            "like_comment" -> ViewHolderType.COMMENT.ordinal
            "follow" -> ViewHolderType.FOLLOW.ordinal
            else -> ViewHolderType.FAVORITE.ordinal
        }
    }
}