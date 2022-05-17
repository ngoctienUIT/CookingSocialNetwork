package com.example.cookingsocialnetwork.model.adapter.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingsocialnetwork.R
import com.example.cookingsocialnetwork.model.data.Notify
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class FollowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var view: WeakReference<View> = WeakReference(itemView)
    private var nameView: TextView? = null
    private var contentView: TextView? = null
    private var timeView: TextView? = null
    private var avatarView: ImageView? = null
    private var followBtn: Button? = null
    var follow: Notify? = null
    var onClickItem: ((String) -> Unit)? = null

    init {
        findView()
        setListener()
    }

    private fun findView() {
        nameView = view.get()?.findViewById(R.id.name)
        contentView = view.get()?.findViewById(R.id.content)
        timeView = view.get()?.findViewById(R.id.time)
        avatarView = view.get()?.findViewById(R.id.avatarUser)
        followBtn = view.get()?.findViewById(R.id.follow)
    }

    private fun setListener() {
        view.get()?.setOnClickListener()
        {
            onClickItem?.let { follow?.let { it1 -> it(it1.name) } }
        }
    }

    fun updateView() {
        findView()
        timeView?.text = follow?.time
        FirebaseFirestore.getInstance()
            .collection("user")
            .document(follow!!.name)
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
    }
}