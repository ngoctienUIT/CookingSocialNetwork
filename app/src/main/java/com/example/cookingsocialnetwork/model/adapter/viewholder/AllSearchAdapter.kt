package com.example.cookingsocialnetwork.model.adapter.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Post
import com.example.cookingsocialnetwork.model.data.User

class AllSearchAdapter(private var listUser: MutableList<User>, private var listPost:MutableList<Post>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        enum class ViewHolderType {
        USER, POST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewHolderType.USER.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_user, parent, false)
                UserViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.grid_item_post, parent, false)
                PostViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
                holder.user = listUser[position]
                holder.updateView()
            }

            is PostViewHolder -> {
                holder.post = listPost[position - 5]
                holder.updateView()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (listUser.size > 5) 5 + listPost.size
        else listUser.size + listPost.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < 5) ViewHolderType.USER.ordinal
        else ViewHolderType.POST.ordinal
    }
}