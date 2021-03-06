package com.example.cookingsocialnetwork.main.fragment.search.adapterviewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.ref.WeakReference

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var view: WeakReference<View> = WeakReference(itemView)
    private var nameView: TextView? = null
    private var nameFoodView: TextView? = null
    private var favoriteView: TextView? = null
    private var avatarView: ImageView? = null
    private var imagePostView: ImageView? = null
    var post: Post? = null

    init {
        findView()
    }

    private fun findView() {
        nameView = view.get()?.findViewById(R.id.name)
        nameFoodView = view.get()?.findViewById(R.id.name_post)
        avatarView = view.get()?.findViewById(R.id.avatar)
        imagePostView = view.get()?.findViewById(R.id.image_post)
        favoriteView = view.get()?.findViewById(R.id.favorite)
    }

    fun updateView() {
        findView()
        nameFoodView?.text = post?.nameFood
        favoriteView?.text = post?.favourites?.size.toString()
        imagePostView?.load(post!!.images[0])
//        Picasso.get().load(post!!.images[0]).into(imagePostView)
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(post!!.owner)
            .get()
            .addOnSuccessListener {
                val data = it.data
                val info = data?.get("info") as Map<String, Any>
                nameView?.text = info["name"].toString()
                val avatar = info["avatar"].toString()
                avatarView?.let { image ->
                    image.load(avatar)
//                    Picasso.get().load(avatar).into(image)
                }
            }
    }
}