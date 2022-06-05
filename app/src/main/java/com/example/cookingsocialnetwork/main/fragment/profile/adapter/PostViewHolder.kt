package com.example.cookingsocialnetwork.main.fragment.profile.adapter

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Post
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var view: WeakReference<View> = WeakReference(itemView)
    private var favourite: TextView? = null
    private var imagePost: ImageView? = null
    var viewPost: RelativeLayout? = null
    var post: Post? = null

    init {
        findView()
    }

    private fun findView() {
        favourite = view.get()?.findViewById(R.id.favorite)
        imagePost = view.get()?.findViewById(R.id.image_post)
        viewPost = view.get()?.findViewById(R.id.view_post)
    }

    fun updateView() {
        findView()
        Picasso.get().load(post!!.images[0]).into(imagePost)
        favourite?.text = post?.favourites?.size.toString()
    }
}