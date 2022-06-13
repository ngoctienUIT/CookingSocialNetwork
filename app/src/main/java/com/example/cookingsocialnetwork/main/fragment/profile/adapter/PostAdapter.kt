package com.example.cookingsocialnetwork.main.fragment.profile.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Post
import com.example.cookingsocialnetwork.newviewpost.ViewPost
import com.example.cookingsocialnetwork.viewpost.ViewFullPost

class PostAdapter(private var listPost: MutableList<Post>): RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.post = listPost[position]
        holder.viewPost?.setOnClickListener()
        {
            val fullPost = Intent(holder.itemView.context, ViewPost::class.java)
            fullPost.putExtra("id_post", listPost[position].id)
            holder.itemView.context.startActivity(fullPost)
        }
        holder.updateView()
    }

    override fun getItemCount(): Int = listPost.size
}