package com.example.cookingsocialnetwork.viewpost.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.User
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class CommentAdapter(private var comments: MutableList<Map<String, Any>>): RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.comment = comments[position]
        holder.updateView()
    }

    override fun getItemCount(): Int = comments.size

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private var view: WeakReference<View> = WeakReference(itemView)
        private var name: TextView? = null
        private var content: TextView? = null
        private var time: TextView? = null
        private var favourite: TextView? = null
        private var userAvatar: ImageView? = null
        private var iconFavourite: ImageView? = null
        var comment: Map<String, Any>? = null

        init {
            findView()
        }

        private fun findView() {
            name = view.get()?.findViewById(R.id.name)
            content = view.get()?.findViewById(R.id.content)
            time = view.get()?.findViewById(R.id.time)
            favourite = view.get()?.findViewById(R.id.favourite)
            userAvatar = view.get()?.findViewById(R.id.userAvatar)
            iconFavourite = view.get()?.findViewById(R.id.ico_favourite)
        }

        fun updateView() {
            findView()
            content?.text = comment?.get("content").toString()
            FirebaseFirestore.getInstance()
                .collection("user")
                .document(comment?.get("userName").toString())
                .get()
                .addOnSuccessListener {
                    val user = User()
                    user.getData(it)
                    Picasso.get().load(user.avatar).into(userAvatar)
                    name?.text = user.name
                }
        }
    }
}