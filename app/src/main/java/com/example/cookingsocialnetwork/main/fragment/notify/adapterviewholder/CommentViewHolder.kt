package com.example.cookingsocialnetwork.main.fragment.notify.adapterviewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Notify
import com.example.cookingsocialnetwork.model.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class CommentViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
    private var view: WeakReference<View> = WeakReference(itemView)
    private var nameView: TextView? = null
    private var contentView: TextView? = null
    private var timeView: TextView? = null
    private var avatarView: ImageView? = null
    private var postView: ImageView? = null
    var comment: Notify? = null

    init {
        findView()
    }

    private fun findView() {
        nameView = view.get()?.findViewById(R.id.name)
        contentView = view.get()?.findViewById(R.id.content)
        timeView = view.get()?.findViewById(R.id.time)
        avatarView = view.get()?.findViewById(R.id.avatarUser)
        postView = view.get()?.findViewById(R.id.post)
    }

    fun updateView()
    {
        findView()
        timeView?.text = comment?.time?.dataTime
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(comment!!.name)
            .get()
            .addOnSuccessListener {
                val data = it.data
                if(data != null){
                    val info = data?.get("info") as Map<String, Any>
                    nameView?.text = info["name"].toString()
                    val avatar = info["avatar"].toString()
                    avatarView?.let { image ->
                        image.load(avatar)
//                    Picasso.get().load(avatar).into(image)
                    }
                }

            }

        FirebaseFirestore.getInstance()
            .collection("post")
            .document(comment!!.id)
            .get()
            .addOnSuccessListener {
                if(it.data != null){
                    val post = Post()
                    post.getData(it)
                    postView?.load(post.images[0])
//                Picasso.get().load(post.images[0]).into(postView)
                }

            }
        if (comment?.type?.compareTo("comment") == 0) contentView?.text = "??a?? bi??nh lu????n: " + comment?.content
        else contentView?.text = "??a?? thi??ch bi??nh lu????n: " + comment?.content
    }
}