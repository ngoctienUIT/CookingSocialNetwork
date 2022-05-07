package com.example.cookingsocialnetwork.model.adapter.viewholder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R

class AdapterViewHolder:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class ViewHolderType {
        COMMENT, FOLLOW, FAVORITE
    }

    private var list: MutableList<String> = mutableListOf()
    var onLoadMore: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
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
                holder.content = list[position]
                holder.updateView()
            }

            is FollowViewHolder -> {
                holder.content = list[position]
                holder.updateView()
            }

            is FavoriteViewHolder -> {
                holder.content = list[position]
                holder.updateView()
            }
        }

        if (position == list.size - 1) {
            onLoadMore?.let { it() }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            "comment" -> ViewHolderType.COMMENT.ordinal
            "follow" -> ViewHolderType.FOLLOW.ordinal
            else -> ViewHolderType.FAVORITE.ordinal
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(list: MutableList<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: MutableList<String>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }
}