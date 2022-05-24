package com.example.cookingsocialnetwork.model.adapter.viewholdernotify

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Notify
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var view: WeakReference<View> = WeakReference(itemView)
    private var nameView: TextView? = null
    private var contentView: TextView? = null
    private var timeView: TextView? = null
    private var avatarView: ImageView? = null
    private var postView: ImageView? = null
    var favorite: Notify? = null
    var onClickItem : ((String)->Unit)? = null

    init {
        findView()
        setListener()
    }

    private fun findView() {
        nameView = view.get()?.findViewById(R.id.name)
        contentView = view.get()?.findViewById(R.id.content)
        timeView = view.get()?.findViewById(R.id.time)
        avatarView = view.get()?.findViewById(R.id.avatarUser)
        postView = view.get()?.findViewById(R.id.post)
    }

    private fun setListener()
    {
        view.get()?.setOnClickListener()
        {
            onClickItem?.let { favorite?.let { it1 -> it(it1.name) } }
        }
    }

    fun updateView()
    {
        findView()
        timeView?.text = favorite?.time
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(favorite!!.name)
            .get()
            .addOnSuccessListener {
                val data = it.data
                val info = data?.get("info") as Map<String, Any>
                nameView?.text = info["name"].toString()
                val avatar = info["avatar"].toString()
                avatarView?.let { image ->
                    Picasso.get().load(avatar).into(image)
                }
            }

        FirebaseFirestore.getInstance()
            .collection("post")
            .document(favorite!!.id)
            .get()
            .addOnSuccessListener {
                val post = Post()
                post.getData(it)
                Picasso.get().load(post.images[0]).into(postView)
            }
    }
}