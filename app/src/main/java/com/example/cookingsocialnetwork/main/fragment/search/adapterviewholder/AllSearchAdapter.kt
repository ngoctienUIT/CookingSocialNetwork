package com.example.cookingsocialnetwork.main.fragment.search.adapterviewholder

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Post
import com.example.cookingsocialnetwork.model.data.User
import com.example.cookingsocialnetwork.viewpost.ViewFullPost

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
                val index: Int = if (listUser.size < 6) listUser.size
                else 5
                holder.post = listPost[position - index]
                holder.itemView.setOnClickListener()
                {
                    val fullPost = Intent(holder.itemView.context, ViewFullPost::class.java)
                    fullPost.putExtra("id_post", listPost[position - index].id)
                    holder.itemView.context.startActivity(fullPost)
                }
                holder.updateView()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (listUser.size > 5) 5 + listPost.size
        else listUser.size + listPost.size
    }

    override fun getItemViewType(position: Int): Int {
        val index: Int = if (listUser.size < 6) listUser.size
        else 5
        return if (position < index) ViewHolderType.USER.ordinal
        else ViewHolderType.POST.ordinal
    }
}